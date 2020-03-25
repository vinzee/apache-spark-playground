package rdd_basics

import org.apache.spark.SparkContext

object CustomerExpenses {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]", "RatingsCounter")

    val lines = sc.textFile("/Users/vinzee/code/SparkScala/resources/customer-orders.csv")

    val ordersMap = lines.map(x => {
      val columns = x.split(",")
      (columns(0), columns(1).toInt)
    })

    val ordersByCustomer = ordersMap.reduceByKey((x, y) => x + y)

    ordersByCustomer.foreach(println)
  }
}
