import org.apache.spark.SparkContext

object WordCounter {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]", "RatingsCounter")

    val lines = sc.textFile("resources/book.txt")
    val words = lines.flatMap(x => x.split("\\W+"))
    val lowerCaseWords = words.map(x => x.toLowerCase())

    // returns scala map
//    val results = lowerCaseWords.countByValue()

//     val sortedResults = results.toSeq.sortBy(_._2).reverse
//     val top10 = sortedResults.take(10)

//    sortedResults.foreach(println)
//    top10.foreach(println)

    val wordCounts = lowerCaseWords.map(x => (x, 1)).reduceByKey((x,y) => x + y)

    // If the data is partitioned then the sorting is done on the elements in that partition only
    // and at end output from each partition is merged.
    // To avoid this scenario you can enforce the numPartition as 1 in sortByKey method
    // and get the data into one partition and then sort it.
    val wordCountsSorted = wordCounts.sortBy(x => x._2, ascending = false, 1)
    val top10 = wordCountsSorted.take(10)
//    wordCountsSorted.foreach(println)
    top10.foreach(println)

    //    for(res <- wordCountsSorted){
//      println(res._1 + " : " + res._2)
//    }
  }
}
