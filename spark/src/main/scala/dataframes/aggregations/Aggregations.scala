package dataframes.aggregations


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._



object Aggregations {

    val spark = SparkSession.builder()
        .appName("Aggregations and Grouping")
        .config("spark.master", "local")
        .getOrCreate()


    import spark.implicits._


    val moviesDF = spark.read
        .format("json")
        .option("inferSchema", "true")
        .load("src/main/resources/data/movies.json")


    val genresCountDF = moviesDF.select(count(col("Major_Genre"))) 
    // also ==> movieDF.selectExpr("count(Major_Genre)")
    // counts all values except nulls
    genresCountDF.show()

// root +------------------+
// root |count(Major_Genre)|
// root +------------------+
// root |              2926|
// root +------------------+


    // count all the rows of a DF, including nulls
    val moviesCountDFIncludeNull = 
        moviesDF.select(count("*"))

    moviesCountDFIncludeNull.show()

// root +--------+
// root |count(1)|
// root +--------+
// root |    3201|
// root +--------+


    // counting distinct
    moviesDF.select(countDistinct(col("Major_Genre"))).show()

// root +---------------------------+
// root |count(DISTINCT Major_Genre)|
// root +---------------------------+
// root |                         12|
// root +---------------------------+


    // approximate count
    moviesDF.select(approx_count_distinct(col("Major_Genre"))).show()

// root +----------------------------------+
// root |approx_count_distinct(Major_Genre)|
// root +----------------------------------+
// root |                                12|
// root +----------------------------------+


    // min and max
    val minRating = moviesDF.select(min(col("IMDB_Rating")))
    // also
    moviesDF.selectExpr("min(IMDB_Rating)").show()

// root +----------------+
// root |min(IMDB_Rating)|
// root +----------------+
// root |             1.4|
// root +----------------+

    // sum

    val sumUSGross = moviesDF.select(sum(col("US_Gross")))
    sumUSGross.show()

// root +-------------+
// root |sum(US_Gross)|
// root +-------------+
// root | 140542660013|
// root +-------------+   


    // avg

    val averageRT = moviesDF.select(avg(col("Rotten_Tomatoes_Rating")))
    averageRT.show()

// root +---------------------------+
// root |avg(Rotten_Tomatoes_Rating)|
// root +---------------------------+
// root |          54.33692373976734|
// root +---------------------------+


    // StdDev and Mean
    
    val sdMeanRT = moviesDF.select(
        mean(col("Rotten_Tomatoes_Rating")),
        stddev(col("Rotten_Tomatoes_Rating"))
    )

    sdMeanRT.show()

// root +---------------------------+-----------------------------------+
// root |avg(Rotten_Tomatoes_Rating)|stddev_samp(Rotten_Tomatoes_Rating)|
// root +---------------------------+-----------------------------------+
// root |          54.33692373976734|                  28.07659263787602|
// root +---------------------------+-----------------------------------+

}
