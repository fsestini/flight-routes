package nl.vu.adsbtools

import com.google.gson.Gson
import nl.vu.adsbtools.RouteClustering.Airports
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object AirlineStats {
  case class Airline(icao: String, name: String)

  def callsigns(sqlContext: SQLContext, url: String): RDD[(String, String)] = {
    val schema = StructType(Array(
      StructField("icao", StringType, true),
      StructField("callsign", StringType, true)
    ))
    sqlContext.read.format("com.databricks.spark.csv")
      .schema(schema).load(url)
      .map(r => (r.get(0).toString, r.get(1).toString))
  }

  case class AirlineDivergingInfo(name: String, nonDiverging: Long, diverging: Long)

  def dumpAirlineInfo2(sc: SparkContext,
                      sqlContext: SQLContext,
                      data: RDD[(Airports, RouteClustering.Cluster, List[RouteClustering.Cluster])]) = {
    val divergingIcaos = data.flatMap(_._3).flatMap(x => x).map(r => r.icao)
    val nonDivergingIcaos = data.flatMap(_._2).map(x => x.icao)

    val calls = callsigns(sqlContext, "icao-callsign-new")

    // println("LOG: " + divergingIcaos.count() + " diverging icaos")

    val tempDiverging = calls
      .join(divergingIcaos.map(x => (x,0)))
      .map(x => (x._2._1, x._1))

    // println("LOG: " + temp.count() + " joined with callsigns")

    val airls = airlines(sqlContext, "airlines.dat")
    val airlsTuple = airls.map(a => (a.icao, a.name))

    val statsDiv = airlsTuple.join(tempDiverging).groupByKey()
      .map(x => (x._1, (x._2.toList.head._1, x._2.count(x => true))))

    val tempNonDiverging = calls
      .join(nonDivergingIcaos.map(x => (x,0)))
      .map(x => (x._2._1, x._1))

    val statsNonDiverging = airlsTuple.join(tempNonDiverging).groupByKey()
      .map(x => (x._1, (x._2.toList.head._1, x._2.count(x => true))))

    val stats = statsNonDiverging.join(statsDiv)
      .map(x => (x._2._1._1, x._2._1._2, x._2._2._2))
      .map(x => AirlineDivergingInfo(x._1, x._2, x._3))

    // println("LOG: " + stats.count() + " joined with airlines")

    val gson = new Gson
    val str = gson.toJson(stats.collect())
    sc.parallelize(Array(str)).saveAsTextFile("diverging-airlines-1000.json")
  }

  /*
  def dumpAirlineInfo(sc: SparkContext,
                      sqlContext: SQLContext,
                      data: RDD[(Airports, RouteClustering.Cluster, List[RouteClustering.Cluster])]) = {
    val nonDivergingIcaos = data.flatMap(_._2).map(r => r.icao)
    val divergingIcaos = data.flatMap(_._3).flatMap(x => x).map(r => r.icao)

    val airls = airlines(sqlContext, "airlines.dat")
    val airlsColl = sc.broadcast(airls.collect())
    val calls = sc.broadcast(callsigns(sqlContext, "icao-callsign-new").collect())

    val stats = airls.map(airline => {
      val div = airlineInIcaos(calls.value, airlsColl.value, airline, divergingIcaos)
      (airline.name, div)
    }).filter(x => x._2 > 0)
      .map(x => AirlineDivergingInfo(x._1, x._2))

    // val asd = airls.union()

    val gson = new Gson
    val str = gson.toJson(stats.collect())
    sc.parallelize(Array(str)).saveAsTextFile("diverging-airlines-fast.json")
  }
  */

  def airlineInIcaos(calls: Array[(String, String)], airls: Array[Airline], airline: Airline, icaos: RDD[String]) = {
    val matching = icaos.filter(icao => {
      val asd = for {
        (ii, cc) <- calls.find(x => x._1 == icao)
        a <- airlineFromCallsign(airls, cc)
      } yield airline.icao == a.icao
      asd match {
        case Some(b) => b
        case None => false
      }
    })
    matching.count()
  }

  def airlineFromCallsign(airlines: Array[Airline], callsign: String): Option[Airline] = {
    if (callsign.length >= 3) {
      val three = callsign.substring(0,3)
      airlines.find(a => a.icao == three)
    } else None
  }


  def airlines(sqlContext: SQLContext, url: String): RDD[Airline] = {
    val schema = StructType(Array(
      StructField("airline-id", StringType, true),
      StructField("airline-name", StringType, true),
      StructField("airline-alias", StringType, true),
      StructField("airline-iata", StringType, true),
      StructField("airline-icao", StringType, true),
      StructField("airline-callsign", StringType, true),
      StructField("airline-country", StringType, true),
      StructField("airline-active", StringType, true)
    ))

    val df = sqlContext.read.format("com.databricks.spark.csv")
      .schema(schema).load(url)
    df.select(df("airline-name"), df("airline-icao"))
      .map(r => Airline(r.get(1).toString, r.get(0).toString))
  }

}
