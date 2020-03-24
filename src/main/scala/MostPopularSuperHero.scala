import org.apache.spark.SparkContext

object MostPopularSuperHero {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]", "RatingsCounter")

    val nameLines = sc.textFile("resources/Marvel-names.txt")
    val superHeroNames = nameLines.flatMap(parseNames)
//    val superHeroNamesB = sc.broadcast(superHeroNames.collect())

    //    user id | item id | rating | timestamp.
    val friendLines = sc.textFile("resources/Marvel-graph.txt")
    val friendCounts = friendLines.map(x => {
      val split = x.split(" ")
      (split(0).toInt, split.length - 1)
    }).reduceByKey((x,y) => x + y)
//      .sortBy(x => x._2)

//    ???
    // this gives a error ->>>
  //    val results = friendCounts.map(x => (superHeroNames.lookup(x._1.toInt), x._2))
//      friendCounts.foreach(println)

    val mostPopularSuperHero = friendCounts.map(x => (x._2, x._1)).max()
    println(mostPopularSuperHero)
    val mostPopularName = superHeroNames.lookup(mostPopularSuperHero._2)

    println(mostPopularName)
    println(mostPopularSuperHero._2)
  }

  def parseNames (line: String) : Option[(Int, String)] = {
      val fields = line.split(" ", 2)
      if (fields.length > 1) {
        return Some(fields(0).trim().toInt, fields(1))
      } else {
        return None // flatmap will just discard None results, and extract data from Some results.
      }
    }
}
