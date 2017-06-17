package nl.vu.adsbtools

import java.nio.file.Paths

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types._
import org.opensky.libadsb.exceptions.BadFormatException
import org.opensky.libadsb.{tools, Decoder, PositionDecoder, Position}
import org.opensky.libadsb.msgs.{SurfacePositionMsg, AirbornePositionMsg, ModeSReply}

import scala.collection.mutable.ListBuffer
import scala.util.control.Exception._

object Positions {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("boh")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    urls.foreach(url => {
      val positionMsgs = Positions.positionMessages(url, sqlContext)
      positionMsgs.groupBy(p => stringIcao24(p._2)).flatMap(w => {
        val icao = w._1
        val list = w._2.toList.sortBy(pair => pair._1)
        val positions = Positions.computePositions(list)
        val rows = Positions.toRows(positions)
        rows.map(r => icao + "," +
          r.timestamp + "," +
          r.latitude + "," +
          r.longitude + "," +
          r.altitude)
      }).saveAsTextFile("positions-" + Paths.get(url).getFileName)
    })
  }

  def stringIcao24(msg: ModeSReply) = tools.toHexString(msg.getIcao24)

  def isAirbornPosition(m: ModeSReply): Boolean =
    m.getType == org.opensky.libadsb.msgs.ModeSReply.subtype.ADSB_AIRBORN_POSITION

  def isSurfacePosition(m: ModeSReply): Boolean =
    m.getType == org.opensky.libadsb.msgs.ModeSReply.subtype.ADSB_SURFACE_POSITION

  def computePositions(list: Iterable[(Double, ModeSReply)]): List[(Double, Position)] = {
    val dec = new PositionDecoder
    val positions = ListBuffer.empty[(Double, Position)]

    list.foreach(w => {
      val timestamp = w._1

      if (isAirbornPosition(w._2)) {
        val msg = w._2.asInstanceOf[AirbornePositionMsg]
        msg.setNICSupplementA(dec.getNICSupplementA)
        val pos = dec.decodePosition(timestamp, msg)
        if (pos != null && pos.isReasonable)
          positions += ((timestamp, pos))
      } else if (isSurfacePosition(w._2)) {
        val msg = w._2.asInstanceOf[SurfacePositionMsg]
        val pos = dec.decodePosition(timestamp, msg)
        if (pos != null && pos.isReasonable)
          positions += ((timestamp, pos))
      }
    })

    positions.toList
  }

  case class Row(timestamp: Double, latitude: Double, longitude: Double, altitude: Double)

  def toRows(positions: List[(Double, Position)]): List[Row] = {
    positions.flatMap(w => {
      for {
        lat <- Option(w._2.getLatitude)
        lon <- Option(w._2.getLongitude)
        alt <- Option(w._2.getAltitude)
        time <- Option(w._1)
      } yield Row(time, lat, lon, alt)
    })
  }

  def positionRows(url: String, sqlContext: SQLContext): RDD[(String, List[Row])] = {
    val customSchema = StructType(Array(
      StructField("icao", StringType, true),
      StructField("timestamp", DoubleType, true),
      StructField("latitude", DoubleType, true),
      StructField("longitude", DoubleType, true),
      StructField("altitude", DoubleType, true)))
    val df = sqlContext.read.format("com.databricks.spark.csv")
      .schema(customSchema).load(url)
      .map(r => {
        val icao = r.get(0).asInstanceOf[String]
        val timestamp = r.get(1).asInstanceOf[Double]
        val latitude = r.get(2).asInstanceOf[Double]
        val longitude = r.get(3).asInstanceOf[Double]
        val altitude = r.get(4).asInstanceOf[Double]
        (icao, timestamp, latitude, longitude, altitude)
      }).groupBy(tuple => tuple._1)
      .map(w => {
        val listb = ListBuffer.empty[Row]
        w._2.foreach(t => listb += Row(t._2, t._3, t._4, t._5))
        (w._1, listb.toList)
      })
    df
  }

  def isPositionMessage(m: ModeSReply): Boolean =
    m.getType == org.opensky.libadsb.msgs.ModeSReply.subtype.ADSB_AIRBORN_POSITION ||
      m.getType == org.opensky.libadsb.msgs.ModeSReply.subtype.ADSB_SURFACE_POSITION

  def genericDecode(rawMsg: String): Option[ModeSReply] =
    catching(classOf[BadFormatException], classOf[ArrayIndexOutOfBoundsException])
      .opt(Decoder.genericDecoder(rawMsg))

  def positionMessages(url: String, sqlContext: SQLContext): RDD[(Double, ModeSReply)] = {
    val df = sqlContext.read.format("com.databricks.spark.avro").load(url)
    val allMessages: RDD[Option[(Double, ModeSReply)]] =
      df.sort("timeAtServer")
        .select(df("timeAtServer"), df("rawMessage"))
        .map(r => genericDecode(r.get(1).toString) match {
          case Some(m) => Some((r.get(0).asInstanceOf[Double], m))
          case None => None
        })
    val goodMessages = allMessages.flatMap(x => x)
    goodMessages.filter(p => isPositionMessage(p._2))
  }

  val urls = Array(
    "/user/hannesm/lsde/opensky2/20160919",
    "/user/hannesm/lsde/opensky2/20160920",
    "/user/hannesm/lsde/opensky2/20160921",
    "/user/hannesm/lsde/opensky2/20160922",
    "/user/hannesm/lsde/opensky2/20160923",
    "/user/hannesm/lsde/opensky2/20160924")

}
