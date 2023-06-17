
package dataframes.grouping


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._




object Grouping {

    val spark = SparkSession.builder()
        .appName("Aggregations and Grouping")
        .config("spark.master", "local")
        .getOrCreate()


    import spark.implicits._


    val moviesDF = spark.read
        .format("json")
        .option("inferSchema", "true")
        .load("src/main/resources/data/movies.json")

    
    
    //  Grouping

    val countByGenreDF = moviesDF
        .groupBy(col("Major_Genre")) // groupBy includes null
        .count()

    countByGenreDF.show()

// root +-------------------+-----+
// root |        Major_Genre|count|
// root +-------------------+-----+
// root |          Adventure|  274|
// root |               null|  275|
// root |              Drama|  789|
// root |        Documentary|   43|
// root |       Black Comedy|   36|
// root |  Thriller/Suspense|  239|
// root |            Musical|   53|
// root |    Romantic Comedy|  137|
// root |Concert/Performance|    5|
// root |             Horror|  219|
// root |            Western|   36|
// root |             Comedy|  675|
// root |             Action|  420|
// root +-------------------+-----+


    val avgRatingByGenreDF = moviesDF
        .groupBy(col("Major_Genre"))
        .avg("IMDB_Rating")

    avgRatingByGenreDF.show()

// root +-------------------+------------------+
// root |        Major_Genre|  avg(IMDB_Rating)|
// root +-------------------+------------------+
// root |          Adventure| 6.345019920318729|
// root |               null|  6.50082644628099|
// root |              Drama| 6.773441734417339|
// root |        Documentary| 6.997297297297298|
// root |       Black Comedy|6.8187500000000005|
// root |  Thriller/Suspense| 6.360944206008582|
// root |            Musical|             6.448|
// root |    Romantic Comedy| 5.873076923076922|
// root |Concert/Performance|             6.325|
// root |             Horror|5.6760765550239185|
// root |            Western| 6.842857142857142|
// root |             Comedy| 5.853858267716529|
// root |             Action| 6.114795918367349|
// root +-------------------+------------------+

    val aggregationsByGenreDF = moviesDF
        .groupBy(col("Major_Genre"))
        .agg(
            count("*").as("N_Movies"),
            avg("IMDB_Rating").as("Avg_Rating")
        )
        .orderBy(col("Avg_Rating"))
    
    aggregationsByGenreDF.show()

// root +-------------------+--------+------------------+
// root |        Major_Genre|N_Movies|        Avg_Rating|
// root +-------------------+--------+------------------+
// root |             Horror|     219|5.6760765550239185|
// root |             Comedy|     675| 5.853858267716529|
// root |    Romantic Comedy|     137| 5.873076923076922|
// root |             Action|     420| 6.114795918367349|
// root |Concert/Performance|       5|             6.325|
// root |          Adventure|     274| 6.345019920318729|
// root |  Thriller/Suspense|     239| 6.360944206008582|
// root |            Musical|      53|             6.448|
// root |               null|     275|  6.50082644628099|
// root |              Drama|     789| 6.773441734417339|
// root |       Black Comedy|      36|6.8187500000000005|
// root |            Western|      36| 6.842857142857142|
// root |        Documentary|      43| 6.997297297297298|
// root +-------------------+--------+------------------+


    val sumAllMovieProfits = moviesDF
        .select(
            (col("US_Gross") + col("Worldwide_Gross") + col("US_DVD_Sales")).as("Total_Gross")
        )
        .agg(sum("Total_Gross"))

    sumAllMovieProfits.show()

    // also
    moviesDF
        .select((col("US_Gross") + col("Worldwide_Gross") + col("US_DVD_Sales")).as("Total_Gross"))
        .select(sum(col("Total_Gross")))
    

// root +----------------+
// root |sum(Total_Gross)|
// root +----------------+
// root |    139190135783|
// root +----------------+


    val distinctDirectors = moviesDF
        .select(countDistinct(col("Director")))

    distinctDirectors.show()

// root +------------------------+
// root |count(DISTINCT Director)|
// root +------------------------+
// root |                     550|
// root +------------------------+

    val aggsUSGross = moviesDF
        .agg(
            mean(col("US_Gross")),
            stddev(col("US_Gross"))
        )

    aggsUSGross.show()
// root +--------------------+---------------------+
// root |       avg(US_Gross)|stddev_samp(US_Gross)|
// root +--------------------+---------------------+
// root |4.4002085163744524E7|  6.255531139066214E7|
// root +--------------------+---------------------+


    val imdbPerDir = moviesDF
        .groupBy(col("Director"))
        .agg(
            avg(col("IMDB_Rating")).as("Avg_Rating"),
            avg(col("US_Gross")).as("US_Total_Gross")
        )
        .sort(col("Avg_Rating").desc)  // .desc_nulls_last

    imdbPerDir.show(3)


// root +------------+----------+--------------+
// root |    Director|Avg_Rating|US_Total_Gross|
// root +------------+----------+--------------+
// root |  Katia Lund|       8.8|     7563397.0|
// root | Ben Affleck|       8.7|  2.56404125E7|
// root |John Sturges|       8.4|   1.1744471E7|
// root +------------+----------+--------------+

}