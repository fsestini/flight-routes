name := "ADSBTools"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.avro" % "avro" % "1.8.0",
  "org.apache.spark" %% "spark-core" % "1.6.1",
  "org.apache.spark" %% "spark-sql" % "1.6.1",
  "com.databricks" %% "spark-avro" % "2.0.1",
  "org.opensky-network" % "libadsb" % "2.0",
  "com.google.code.gson" % "gson" % "2.7",
  "ca.umontreal.iro.simul" % "ssj" % "3.2.0"
)
