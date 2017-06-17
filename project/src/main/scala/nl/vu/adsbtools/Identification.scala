package nl.vu.adsbtools

import java.nio.file.Paths

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.opensky.libadsb.{tools, Decoder}
import org.opensky.libadsb.exceptions.BadFormatException
import org.opensky.libadsb.msgs.{IdentificationMsg, ModeSReply}

import scala.util.control.Exception._

object Identification {

  val urls = Array(
    "/user/hannesm/lsde/opensky2/20160918",
    "/user/hannesm/lsde/opensky2/20160919",
    "/user/hannesm/lsde/opensky2/20160920",
    "/user/hannesm/lsde/opensky2/20160921",
    "/user/hannesm/lsde/opensky2/20160922",
    "/user/hannesm/lsde/opensky2/20160923",
    "/user/hannesm/lsde/opensky2/20160924"
  )

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("boh")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    urls.foreach(url => {
      val msgs = rawIdentificationMsgs(sqlContext, url)
      msgs.saveAsTextFile("identification-msgs-" + Paths.get(url).getFileName)
    })

    val rawIdMsgs = sc.textFile("identification-msgs-201609*")
    val idMsgs = rawIdMsgs.flatMap(raw => genericDecode(raw)).map(_.asInstanceOf[IdentificationMsg])

    doStuff(idMsgs)
  }

  def doStuff(idMsgs: RDD[IdentificationMsg]) = {
    idMsgs
      .map(m => (stringIcao24(m), callsignString(m)))
      .filter(p => p._2.trim.length >= 3)
      .map(x => (x._1, x._2.substring(0,3)))
      .filter(x => x._2.trim.length == 3)
      .groupByKey()
      .flatMap(p => {
        unique(p._2) match {
          case Some(x) => Some((p._1, x))
          case None => None
        }
      })
      .map(p => p._1 + "," + p._2)
      .saveAsTextFile("icao-callsign-new")
  }

  def identificationMsgs(sqlContext: SQLContext, url: String) = {
    val df = sqlContext.read.format("com.databricks.spark.avro").load(url)
    df.select(df("rawMessage"))
      .flatMap(r => genericDecode(r.get(0).toString))
      .filter(m => m.getType == org.opensky.libadsb.msgs.ModeSReply.subtype.ADSB_IDENTIFICATION)
      .map(m => m.asInstanceOf[IdentificationMsg])
  }

  def unique(it: Iterable[String]): Option[String] = {
    val list = it.toList
    if (list.nonEmpty) {
      val first = list.head
      if (list.tail.nonEmpty)
        list.tail.map(el => el == first).reduce(_ && _) match {
          case true => Some(first)
          case false => None
        }
      else Some(first)
    } else None

  }

  def callsignString(m: IdentificationMsg) =
    new String(m.getIdentity)

  def rawIdentificationMsgs(sqlContext: SQLContext, url: String) = {
    val df = sqlContext.read.format("com.databricks.spark.avro").load(url)
    df.select(df("rawMessage"))
      .flatMap(r => {
        val raw = r.get(0).toString
        genericDecode(raw) match {
          case Some(m) => Some(raw, m)
          case None => None
        }
      })
      .filter(x => x._2.getType == org.opensky.libadsb.msgs.ModeSReply.subtype.ADSB_IDENTIFICATION)
      .map(_._1)
  }

  def stringIcao24(msg: ModeSReply) = tools.toHexString(msg.getIcao24)

  def genericDecode(rawMsg: String): Option[ModeSReply] = {
    val m = catching(classOf[BadFormatException], classOf[ArrayIndexOutOfBoundsException])
      .opt(Decoder.genericDecoder(rawMsg))
    m match {
      case Some(m) => Option(m)
      case None => None
    }
  }
}
