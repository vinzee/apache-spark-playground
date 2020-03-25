# Apache Spark Playground

### Udemy Apache Spark with Scala
- Official Spark Examples - https://github.com/apache/spark/tree/master/examples/src/main/scala/org/apache/spark/examples
- Udemy - https://udemy.com/course/apache-spark-with-scala-hands-on-with-big-data
- Udemy Dataset - http://media.sundog-soft.com/SparkScala/SparkScala.zip

### Movie Lens Dataset
- https://grouplens.org/datasets/movielens/
- download the 100k dataset to begin with
- copy the contents in following folder the ~/apache-spark-playground/resources/ml-100k

### Installation
https://www.tutorialkart.com/apache-spark/how-to-install-spark-on-mac-os/
```sh
$ brew install scala
$ brew install apache-spark
$ scala home - /usr/local/opt/scala/idea
```

### Commands
```sh
$ spark-shell
$ sbt assembly
```

### Intellij Setup
- https://docs.scala-lang.org/getting-started/intellij-track/building-a-scala-project-with-intellij-and-sbt.html


### UI Dashboard
http://localhost:4040

### Must have imports
```java
import org.apache.spark.sql.functions._
import spark.implicits._
```