package rdd_basics

import java.nio.charset.CodingErrorAction

import org.apache.spark.SparkContext

import scala.io.{Codec, Source}

object MovieRating {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]", "RatingsCounter")
    //    user id | item id | rating | timestamp.
    val lines = sc.textFile("/Users/vinzee/code/SparkScala/resources/ml-100k/u.data")
    val ratings = lines.map(x => x.toString.split("\t")(1).toInt)
    val ratingCounts = ratings.map(x => (x, 1)).reduceByKey((x, y) => x + y).sortBy(x => x._2)

    val movieNames = sc.broadcast(loadMovieNames())

    val movieRatingCounts = ratingCounts.map(x => (movieNames.value(x._1), x._2))

    movieRatingCounts.collect().foreach(println)
  }

  /** Load up a Map of movie IDs to movie names. */
  def loadMovieNames(): Map[Int, String] = {

    // Handle character encoding issues:
    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    // Create a Map of Ints to Strings, and populate it from u.item.
    var movieNames: Map[Int, String] = Map()

    val lines = Source.fromFile("resources/ml-100k/u.item").getLines()
    for (line <- lines) {
      var fields = line.split('|')
      if (fields.length > 1) {
        movieNames += (fields(0).toInt -> fields(1))
      }
    }

    return movieNames
  }
}
