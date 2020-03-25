package rdd_basics

import org.apache.spark.SparkContext

object MinimumTemp {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]", "MinimumTemp")
    val lines = sc.textFile("resources/1800.csv")
    val temps = lines.map(line => {
      val fields = line.toString.split(",")
      val stationID = fields(0)
      val entryType = fields(2)
      val temperature = fields(3).toFloat * 0.1f * (9.0f / 5.0f) + 32.0f
      (stationID, entryType, temperature)
    })

    //    temps.foreach(println)

    val minTemps = temps.filter(x => x._2 == "TMIN")

    val stationTemps = minTemps.map(x => (x._1, x._3))

    val minTempsByStation = stationTemps.reduceByKey((x, y) => Math.min(x, y))

    val results = minTempsByStation.collect()

    for (result <- results.sorted) {
      val station = result._1
      val temp = result._2
      val formattedTemp = f"$temp%.2f F"
      println(s"$station minimum temperature: $formattedTemp")
    }
  }
}
