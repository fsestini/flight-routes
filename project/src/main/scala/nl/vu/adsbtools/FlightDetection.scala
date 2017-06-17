package nl.vu.adsbtools

import java.nio.file.Paths

import nl.vu.adsbtools.Airports.Airport
import nl.vu.adsbtools.Positions.Row
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.mutable.ListBuffer

object FlightDetection {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("boh")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val airports = Airports.getAirports(sqlContext, args(0)).collect()
    urls.foreach(url => dumpFlights(sqlContext, airports, url))
  }

  case class Sector(index: Int, fst: Int, snd: Int)

  def points2route(points: List[(Double, Double)]): List[Sector] = {
    val asd = points.map(z => (z._1.*(10).toInt, z._2.*(10).toInt)).distinct
    var i = 0
    val buffer = ListBuffer.empty[Sector]
    asd.foreach(p => {
      buffer += Sector(i, p._1, p._2)
      i = i + 1
    })
    buffer.toList
  }

  type Cluster = List[Row]
  type SectoredCluster = List[Sector]

  /*
  def dumpFlightsInfo(sqlContext: SQLContext,
                      url: String) = {
    val positionRows = Positions.positionRows(url, sqlContext)
    val asd = positionRows.map(w => {
      val icao = w._1
      val rows = w._2
      val clusters: List[Cluster] = FlightDetection.cluster(rows)
      val goodClusters = clusters.filter(isGoodCluster)
      (clusters.count(x => true), goodClusters.count(x => true))
    }).reduce((x1, x2) => (x1._1 + x2._1, x1._2 + x2._2))
  }
  */

  def dumpFlights(sqlContext: SQLContext,
                  airports: Array[Airport],
                  url: String) = {
    val positionRows = Positions.positionRows(url, sqlContext)
    positionRows.flatMap(w => {
      val icao = w._1
      val rows = w._2
      val clusters: List[Cluster] = FlightDetection.cluster(rows)
      val goodClusters = clusters.filter(isGoodCluster)

      val normalizedClusters = goodClusters
        .map(cl => normalizeClusterAndAirports(airports, cl))
        .filter(x => x._1.code != x._2.code)

      var i = 0
      normalizedClusters.flatMap(x => {
        val dep = x._1
        val arr = x._2
        val cl = x._3
        val sectors = points2route(cl.map(row => (row.latitude, row.longitude)))
        val asd = sectors.map(s => Paths.get(url).getFileName + icao + i + "," +
          dep.code + "," + arr.code + "," +
          s.index + "," + s.fst + "," + s.snd)
        i = i + 1
        asd
      })
    }).saveAsTextFile("flights-" + Paths.get(url).getFileName + "-rerun")
  }

  def normalizeClusterAndAirports(airports: Array[Airport], cl: Cluster):
  (Airport, Airport, Cluster) = {
    val dep = flightAirports(airports, cl)._1
    val arr = flightAirports(airports, cl)._2
    val firstTimestamp = cl.head.timestamp
    val lastTimestamp = cl.last.timestamp

    (dep, arr, Row(firstTimestamp - 1, dep.latitude, dep.longitude, 0) ::
      (cl :+ Row(lastTimestamp + 1, arr.latitude, arr.longitude, 0)))
  }

  def canCluster(lastRow: Row, currentRow: Row) = {
    val cond1 = distFrom(lastRow.latitude, lastRow.longitude,
      currentRow.latitude, currentRow.longitude) < 20000  // 20 km
    val cond2 = Math.abs(lastRow.timestamp - currentRow.timestamp) < 1200 // 20 min
    val cond3 = Math.abs(lastRow.altitude - currentRow.altitude) < 200 // 200 m
    cond1 && cond2 && cond3
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

  def airportNearPoint(airports: Seq[Airport], lat: Double, lon: Double) = {
    airports.min(Ordering.by((a: Airport) =>
      distFrom(a.latitude, a.longitude, lat, lon)))
  }

  def altitudes(rows: List[Row]): List[Double] = rows.map(r => r.altitude)

  def isAscending(altitudes: List[Double]): Boolean =
    altitudes match {
      case first :: xs => first <= 3000 && isAscendingLoop(first, first, false, xs)
      case _ => false
    }

  def isAscendingLoop(first: Double,
                      last: Double,
                      toAmend: Boolean,
                      altitudes: List[Double]): Boolean = {
    altitudes match {
      case current :: xs => {
        if (current >= last) {
          if ((current - first) >= 2000)
            true
          else
            isAscendingLoop(first, current, false, xs)
        } else {
          if (toAmend)
            false
          else
            isAscendingLoop(first, current, false, xs)
        }
      }
      case _ => false
    }
  }

  def cluster(rows: List[Row]): List[List[Row]] = {
    val clusters = ListBuffer.empty[List[Row]]
    var currentCluster = ListBuffer.empty[Row]
    rows.sortBy(r => r.timestamp).foreach(currentRow => {
      val len = currentCluster.length
      if (len > 0) {
        val lastRow = currentCluster(len-1)
        if (canCluster(lastRow, currentRow))
          currentCluster += currentRow
        else {
          clusters += currentCluster.toList
          currentCluster = ListBuffer.empty[Row]
        }
      } else
        currentCluster += currentRow
    })
    clusters.toList
  }

  def isGoodCluster(cluster: List[Row]) = {
    val alts = altitudes(cluster)
    isAscending(alts) && isAscending(alts.reverse)
  }

  def flightAirports(airports: Seq[Airport],
                     rows: List[Row]): (Airport, Airport) = {
    val departure = airportNearPoint(airports, rows.head.latitude, rows.head.longitude)
    val arrival = airportNearPoint(airports, rows.last.latitude, rows.last.longitude)
    (departure, arrival)
  }

  val urls = Array(
    "positions-20160918",
    "positions-20160919",
    "positions-20160920",
    "positions-20160921",
    "positions-20160922",
    "positions-20160923",
    "positions-20160924"
  )
}
