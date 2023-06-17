package dataframes.joins


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._




object Joins extends App {

    val spark = SparkSession.builder()
        .appName("Joins")
        .config("spark.master", "local")
        .getOrCreate()


    import spark.implicits._


    val guitarsDF = spark.read
        .option("inferSchema", "true")
        .json("src/main/resources/data/guitars.json")

    guitarsDF.show()

// root +--------------------+---+------+------------+
// root |          guitarType| id|  make|       model|
// root +--------------------+---+------+------------+
// root |Electric double-n...|  0|Gibson|    EDS-1275|
// root |            Electric|  5|Fender|Stratocaster|
// root |            Electric|  1|Gibson|          SG|
// root |            Acoustic|  2|Taylor|         914|
// root |            Electric|  3|   ESP|        M-II|
// root +--------------------+---+------+------------+

    val guitaristsDF = spark.read
        .option("inferSchema", "true")
        .json("src/main/resources/data/guitarPlayers.json")

    guitaristsDF.show()


// root +----+-------+---+------------+
// root |band|guitars| id|        name|
// root +----+-------+---+------------+
// root |   0|    [0]|  0|  Jimmy Page|
// root |   1|    [1]|  1| Angus Young|
// root |   2| [1, 5]|  2|Eric Clapton|
// root |   3|    [3]|  3|Kirk Hammett|
// root +----+-------+---+------------+


    val bandsDF = spark.read
        .option("inferSchema", "true")
        .json("src/main/resources/data/bands.json")

    bandsDF.show()

// root +-----------+---+------------+----+
// root |   hometown| id|        name|year|
// root +-----------+---+------------+----+
// root |     Sydney|  1|       AC/DC|1973|
// root |     London|  0|Led Zeppelin|1968|
// root |Los Angeles|  3|   Metallica|1981|
// root |  Liverpool|  4| The Beatles|1960|
// root +-----------+---+------------+----+

    val joinCondition1 = guitaristsDF.col("band") === bandsDF.col("id")
    val guitaristsBandsDF = 
        guitaristsDF.join(bandsDF, joinCondition1, "inner")

    guitaristsBandsDF.show()

// root +----+-------+---+------------+-----------+---+------------+----+
// root |band|guitars| id|        name|   hometown| id|        name|year|
// root +----+-------+---+------------+-----------+---+------------+----+
// root |   1|    [1]|  1| Angus Young|     Sydney|  1|       AC/DC|1973|
// root |   0|    [0]|  0|  Jimmy Page|     London|  0|Led Zeppelin|1968|
// root |   3|    [3]|  3|Kirk Hammett|Los Angeles|  3|   Metallica|1981|
// root +----+-------+---+------------+-----------+---+------------+----+


    


    


    













    }