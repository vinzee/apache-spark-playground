name := "SparkScalaSBT"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.5"

// sbt syntax -
// Scala library - "group" %% "artifact" % "version"
// Java library - "group" % "artifact" % "version"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-graphx" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-twitter" % "1.6.3",
  "org.twitter4j" % "twitter4j-core" % "4.0.7",
  "org.twitter4j" % "twitter4j-stream" % "4.0.7",
  "com.twitter" % "hbc-core" % "2.2.0"
  //  "org.spark-project" % "dstream-twitter" % "0.1.0"
)