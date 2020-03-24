name := "SparkScalaSBT"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.5",
  "org.apache.spark" %% "spark-sql" % "2.4.5"
)

//libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.4.1"

//libraryDependencies ++= Seq(
//  "org.twitter4j" % "twitter4j-core" % "3.0.3",
//  "org.twitter4j" % "twitter4j-stream" % "3.0.3"
//)

//libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "0.9.0-incubating"