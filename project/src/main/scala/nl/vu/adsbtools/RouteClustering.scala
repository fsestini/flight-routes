package nl.vu.adsbtools

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types._
import org.apache.spark.{SparkContext, SparkConf}
import com.google.gson.Gson
import umontreal.ssj.functionfit.SmoothingCubicSpline

import scala.collection.mutable.{ListBuffer, ArrayBuffer}

object RouteClustering {
  private val threshold = 1000
  private val insdel_weight = 20
  private val editing_weight = 1

  type Cluster = List[Route]
  type MutableCluster = ListBuffer[Route]
  type ArrayRoute = Array[Array[Double]]

  case class Route(icao: String, sectors: List[Sector])
  case class Airports(departureCode: String, arrivalCode: String)
  case class Sector(lat: Int, lon: Int)
  case class StandardRouteInfo(departureCode: String, arrivalCode: String, route: ArrayRoute)
  case class DivergingRouteInfo(departureCode: String, arrivalCode: String, routes: Array[ArrayRoute])

  def sectorDistance(sector1: Sector, sector2: Sector): Double = {
    val lat1 = sector1.lat.toDouble / 10
    val lon1 = sector1.lon.toDouble / 10
    val lat2 = sector2.lat.toDouble / 10
    val lon2 = sector2.lon.toDouble / 10
    distFrom(lat1, lon1, lat2, lon2)
  }

  def routeLength(route: Route) = {
    val sectors = route.sectors
    sectors.tail.zip(sectors).map(x => sectorDistance(x._1, x._2)).reduce(_+_)
  }

  def smoothen(points: Array[(Double, Double)]): Array[(Double, Double)] = {
    val lats = points.map(_._1)
    val latsW = lats.map(x => 1)
    latsW.update(0, 1000)
    latsW.update(latsW.length - 1, 1000)
    val lons = points.map(_._2)
    val lonsW = lons.map(x => 1)
    lonsW.update(0, 1000)
    lonsW.update(lonsW.length - 1, 1000)

    val latSpline = new SmoothingCubicSpline(
      (1 to lats.length).map(_.toDouble).toArray,
      lats,
      latsW.map(_.toDouble),
      0.1)

    val lonSpline = new SmoothingCubicSpline(
      (1 to lons.length).map(_.toDouble).toArray,
      lons,
      lonsW.map(_.toDouble),
      0.1)

    val latValues = (1.0 to lats.length by 0.2).map(x => latSpline.evaluate(x))
    val lonValues = (1.0 to lats.length by 0.2).map(x => lonSpline.evaluate(x))

    (latValues zip lonValues) toArray
  }

  def toSmoothArrayRoute(route: Route): ArrayRoute = {
    val doubledRoute = route.sectors.map(s => (s.lat.toDouble / 10, s.lon.toDouble / 10)).toArray
    val smoothRoute = smoothen(doubledRoute)
    val arrayedRoute: ArrayRoute = smoothRoute.map(x => Array(x._1, x._2))
    arrayedRoute
  }

  def stdRoutesToJson(array: Array[(Airports, Route)]): String = {
    val data: Array[StandardRouteInfo] =
      array.map(x => StandardRouteInfo(
        x._1.departureCode,
        x._1.arrivalCode,
        toSmoothArrayRoute(x._2)))
    (new Gson).toJson(data)
  }

  def divergingRoutesToJson(array: Array[(Airports, List[Route])]): String = {
    val data: Array[DivergingRouteInfo] =
      array.map(x => DivergingRouteInfo(
        x._1.departureCode,
        x._1.arrivalCode,
        x._2.toArray.map(toSmoothArrayRoute)))
    (new Gson).toJson(data)
  }

  def distFrom(lat1: Double, lng1: Double, lat2: Double, lng2: Double) = {
    val earthRadius: Double = 6371000; //meters
    val dLat: Double = Math.toRadians(lat2-lat1)
    val dLng: Double = Math.toRadians(lng2-lng1)
    val a: Double = Math.sin(dLat/2) * Math.sin(dLat/2) +
      Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLng/2) * Math.sin(dLng/2)
    val c: Double = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
    val dist: Double = earthRadius * c
    dist
  }

  case class CarbonDioxideInfo(depCode: String,
                               arrCode: String,
                               routeProduction: Double,
                               straightLineProduction: Double)

  def dumpCO2Info(sc: SparkContext, standardRoutes: Array[(Airports, Route)]) = {
    val co2 = standardRoutes.map(x => {
      val airports = x._1
      val route = x._2

      val lengthMeters = routeLength(route)
      val lengthMiles = lengthMeters * 0.000621371
      val co2ProductionPounds = 53 * lengthMiles

      val airportsDistanceMeters = sectorDistance(route.sectors.head, route.sectors.last)
      val airportsDistanceMiles = airportsDistanceMeters * 0.000621371
      val co2ProductionPoundsStraightLine = 53 * airportsDistanceMiles

      CarbonDioxideInfo(
        airports.departureCode,
        airports.arrivalCode,
        co2ProductionPounds,
        co2ProductionPoundsStraightLine)
    })

    sc.parallelize(Array((new Gson).toJson(co2))).saveAsTextFile("co2.json")
  }

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("boh")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val stdNonstd = stdAndNonstdRoutes(sc, sqlContext, "flights-positions-201609*")

    val standardAndNonstandardRoutes = stdNonstd._2
    val standardRoutes: Array[(Airports, Route)] =
      standardAndNonstandardRoutes.map(x => (x._1, x._2))

    val nonstandardRoutes: Array[(Airports, List[Route])] =
      standardAndNonstandardRoutes
        .filter(x => x._3.nonEmpty)
        .map(x => (x._1, x._3))

    sc.parallelize(Array(stdRoutesToJson(standardRoutes))).saveAsTextFile("new-std-1000.json")
    sc.parallelize(Array(divergingRoutesToJson(nonstandardRoutes))).saveAsTextFile("new-diverging-1000.json")

    val stdNonstdClusters = stdNonstd._1
    AirlineStats.dumpAirlineInfo2(sc, sqlContext, stdNonstdClusters)
  }

  def stdAndNonstdRoutes(sc: SparkContext, sqlContext: SQLContext, url: String) = {
    val customSchema = StructType(Array(
      StructField("flight-id", StringType, true),
      StructField("icao", StringType, true),
      StructField("dep-code", StringType, true),
      StructField("arr-code", StringType, true),
      StructField("sector-index", IntegerType, true),
      StructField("sector-fst", IntegerType, true),
      StructField("sector-snd", IntegerType, true)
    ))

    val data = sqlContext.read.format("com.databricks.spark.csv")
      .schema(customSchema).load(url)
      .map(r => {
        val flightId = r.get(0).asInstanceOf[String]
        val icao = r.get(1).asInstanceOf[String]
        val depCode = r.get(2).asInstanceOf[String]
        val arrCode = r.get(3).asInstanceOf[String]
        val index = r.get(4).asInstanceOf[Int]
        val fst = r.get(5).asInstanceOf[Int]
        val snd = r.get(6).asInstanceOf[Int]
        (flightId, icao, depCode, arrCode, index, fst, snd)
      }).groupBy(w => (w._3, w._4))
        .map(w => (w._1, w._2.map(x => (x._1, x._2, x._5, x._6, x._7))))

    val airportsAndRoutes: RDD[(Airports, List[Route])] =
      data.map(p => {
        val airports: (String, String) = p._1
        val stuff: Iterable[(String, String, Int, Int, Int)] = p._2
        val mapped = stuff.groupBy(x => x._1).map(p => (p._1, p._2.map(w => (w._2, w._3, w._4, w._5))))

        val routes = mapped.map(x => {
          val iterable = x._2
          val icao = iterable.toList.head._1
          val rawSectors = iterable.map(x => (x._2, x._3, x._4))
          val orderedRawSectors = rawSectors.toList.sortBy(_._1)
          val sectors = orderedRawSectors.map(x => (x._2, x._3)).map(x => Sector(x._1, x._2))
          Route(icao, sectors)
        }).toList
          .filter(r => r.sectors.length > 1)

        (Airports(airports._1, airports._2), routes)
      })

    val airportsAndClusters: RDD[(Airports, List[Cluster])] = airportsAndRoutes
      .map(x => {
        val airports = x._1
        val routes = x._2
        val clusters = clusterizeRoutes(ArrayBuffer.empty[MutableCluster], routes)
        (airports, clusters)
      })

    val standardAndNonstandardClusters: RDD[(Airports, Cluster, List[Cluster])] =
      airportsAndClusters
        .filter(x => x._2.nonEmpty)
        .map(x => {
          val airports = x._1
          val clusters = x._2
          val clustersAndSize = clusters.map(cl => (cl, cl.length))
          val max = clustersAndSize.maxBy(x => x._2)._1
          val others = clusters.filter(cl => cl != max)
          (airports, max, others)
        })

    val standardAndNonstandardRoutes: Array[(Airports, Route, List[Route])] =
      standardAndNonstandardClusters
        .map(x => (x._1, standardRouteInCluster(x._2), x._3.map(standardRouteInCluster)))
        .collect()

    (standardAndNonstandardClusters, standardAndNonstandardRoutes)
  }

  // Cluster contains at least one route
  def standardRouteInCluster(cluster: Cluster): Route = {
    if (cluster.length == 1)
      cluster.head
    else {
      // val asd = cluster.map(r => (r, cluster.filter(x => x != r).map(q => distance(r,q)).max))
      val asd = cluster.map(r => (r, cluster.filter(x => x eq r).map(q => distance(r,q)).max))
      asd.minBy(x => x._2)._1
    }
  }

  def clusterizeRoutes(clusters: ArrayBuffer[MutableCluster], routes: List[Route]): List[Cluster] = {
    routes match {
      case Nil => clusters.map(_.toList).toList
      case route :: rest => {
        clusters.find(cluster => canBeInCluster(cluster, route)) match {
          case Some(cl) => {
            val index = clusters.indexOf(cl)
            clusters.update(index, cl += route)
            clusterizeRoutes(clusters, rest)
          }
          case None => clusterizeRoutes(clusters += (ListBuffer.empty[Route] += route), rest)
        }
      }
    }
  }

  def canBeInCluster(cluster: MutableCluster, route: Route): Boolean =
    cluster.forall(clRoute => distance(clRoute, route) <= threshold)

  def distance(route1: Route, route2: Route): Double = {
    val dist = Array.tabulate(route1.sectors.length + 1, route2.sectors.length + 1) {
      (j,i) => if (j==0) i else if (i==0) j else 0
    }

    for(j <- 1 to route1.sectors.length; i <- 1 to route2.sectors.length) {
      lazy val diff = (route1.sectors(j-1).lat - route2.sectors(i-1).lat, route1.sectors(j-1).lon - route2.sectors(i-1).lon)
      dist(j)(i) =
        if (route1.sectors(j-1) == route2.sectors(i-1))
          dist(j-1)(i-1)
        else
          Array(
            dist(j-1)(i) + insdel_weight,
            dist(j)(i-1) + insdel_weight,
            dist(j-1)(i-1) + (editing_weight * (diff._1 * diff._1 + diff._2 * diff._2))
          ).min
    }

    dist(route1.sectors.length)(route2.sectors.length)
  }

}
