package nl.vu.adsbtools

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkContext, SparkConf}

object Airports {

  case class Airport(code: String, latitude: Double, longitude: Double)

  def getAirports(sqlContext: SQLContext, fileName: String): RDD[Airport] =
    sqlContext.read.format("com.databricks.spark.csv").load(fileName)
      .map(r => Airport(r.get(4).asInstanceOf[String],
                        r.get(6).asInstanceOf[String].toDouble,
                        r.get(7).asInstanceOf[String].toDouble))
}
