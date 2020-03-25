package dataframes_and_datasets

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

final case class Movie(id: Int, title: String, releaseDate: String, videoReleaseDate: String, IMDbURL: String,
                       genres: List[String])

final case class User(id: Int, age: Int, gender: String, occupation: String, zip_code: String)

final case class Rating(userId: Int, movieId: Int, rating: Int, timestamp: String)

object MoviesDataSetPlayGround {
  private val genresList: List[(String, Int)] = List("unknown", "Action", "Adventure", "Animation", "Childrens", "Comedy", "Crime", "Documentary", "Drama", "Fantasy", "Film_Noir", "Horror", "Musical", "Mystery", "Romance", "Sci_Fi", "Thriller", "War", "Western").zipWithIndex

  def main(args: Array[String]): Unit = {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Use new SparkSession interface in Spark 2.0
    val spark = SparkSession
      .builder
      .appName("MoviesPlayGround")
      .master("local[*]")
      .getOrCreate()

    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    val movies = spark.sparkContext.textFile("resources/ml-100k/u.item").map(movieMapper).toDS().cache()
    val users = spark.sparkContext.textFile("resources/ml-100k/u.user").map(userMapper).toDS().cache()
    val ratings = spark.sparkContext.textFile("resources/ml-100k/u.data").map(ratingMapper).toDS().cache()

    //    movies.printSchema()
    //    movies.count()
    //    movies.select("genres").show()
    //    ratings.groupBy("movieId").mean("rating").show()

    val filteredUsers = users.filter(col("age") > 18 && col("age") < 45 && col("gender") === "M").select("id")
    //    filteredUsers.show()

    val filteredRatings = filteredUsers.join(ratings, ratings("userId") === filteredUsers("id"))

    //    val aggRatings = ratings.groupBy("movieId").agg(
    val aggRatings = filteredRatings.groupBy("movieId").agg(
      mean("rating").alias("avgRating"),
      count("rating").alias("numRatings")
    ).filter(col("numRatings") > 10)

    aggRatings.join(movies, aggRatings("movieId") === movies("id"))
      .select("title", "avgRating", "numRatings", "genres")
      .sort(desc("avgRating"), desc("numRatings"))
      .show()

    // Stop the session
    spark.stop()
  }

  def movieMapper(line: String): Movie = {
    val fields = line.split('|')

    val genres: List[String] = genresList.flatMap((genre: (String, Int)) => {
      //      println(genre._1 + " : " + genre._2 + " : " + fields(genre._2+5))
      if (fields(genre._2 + 5).toInt == 1) {
        Some(genre._1)
      } else {
        None
      }
    })

    Movie(fields(0).toInt, fields(1), fields(2), fields(3), fields(4), genres)
  }

  def ratingMapper(line: String): Rating = {
    val fields = line.split('\t')
    Rating(fields(0).toInt, fields(1).toInt, fields(2).toInt, fields(3))
  }

  def userMapper(line: String): User = {
    val fields = line.split('|')
    User(fields(0).toInt, fields(1).toInt, fields(2), fields(3), fields(4))
  }
}
