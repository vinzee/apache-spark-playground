# Apache Spark Playground

[Udemy - Apache Spark with Scala](https://mathworks.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data)

### Installation
https://www.tutorialkart.com/apache-spark/how-to-install-spark-on-mac-os/
```sh
$ brew install scala
$ brew install apache-spark
$$ scala home - /usr/local/opt/scala/idea
```

### Commands
```sh
$ spark-shell
$ sbt assembly
```

### UI Dashboard
http://localhost:4040

### Must have imports
```java
import org.apache.spark.sql.functions._
import spark.implicits._
```