package org.apache.spark.examples.graphx

// $example on$
// $example off$

import org.apache.spark.graphx.GraphLoader
import org.apache.spark.sql.SparkSession

/**
 * A PageRank example on social network dataset
 * Run with
 * {{{
 * bin/run-example graphx.PageRankExample
 * }}}
 */
object PageRankExample {
  def main(args: Array[String]): Unit = {
    // Creates a SparkSession.
    val spark = SparkSession
      .builder
      .appName("PageRankExample")
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext

    //    $example on$
    //      Load the edges as a graph
    val graph = GraphLoader.edgeListFile(sc, "./resources/followers.txt")
    // Run PageRank
    val ranks = graph.pageRank(0.0001).vertices
    // Join the ranks with the usernames
    val users = sc.textFile("./resources/users.txt").map { line =>
      val fields = line.split(",")
      (fields(0).toLong, fields(1))
    }
    val ranksByUsername = users.join(ranks).map {
      case (id, (username, rank)) => (username, rank)
    }
    // Print the result
    println(ranksByUsername.collect().mkString("\n"))
    // $example off$
    spark.stop()
  }
}

// scalastyle:on println
