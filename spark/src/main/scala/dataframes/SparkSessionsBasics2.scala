package dataframes.basics2

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._




object SparkDataFramesBasics2 {

    val spark = 
        SparkSession
            .builder
            .appName("Spark DataFrames Basics 2")
            .config("spark.master", "local")
            .getOrCreate()

    // val smartphoneSchema =
    //     StructType(
    //         Array(
    //             StructField("Make", "StringType"),
    //             StructField("Model", "StringType")
    //             StructField("Dimension", "StringType")
    //             StructField("MegaPixels", "DoubleType")
    //         )
    //     )

    val smartphones = Seq(
        ("Apple", "iPhoneX", "iOS", 13),
        ("Samsung", "Galaxy S10", "Android", 12),
        ("Nokia", "3310", "The Best", 10)
    )

    import spark.implicits._

    val smartphoneDF = smartphones.toDF("Make", "Model", "Platform", "MegaPixels")

    smartphoneDF.show()
    smartphoneDF.printSchema()

    val moviesDF = spark.read
        .format("json")
        .option("inferSchema", "true")
        .load("src/main/resources/data/movies.json")

    
    moviesDF.show(5)
    moviesDF.printSchema()

    println(s"The Movies DF has ${moviesDF.count()} rows")
    // The Movies DF has 3201 rows











}
