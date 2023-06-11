package datasources



import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.SaveMode


// https://spark.apache.org/docs/latest/sql-data-sources-load-save-functions.html
object DataSources extends App {

    val spark = 
        SparkSession
            .builder
            .appName("Reading Data Sources and Formats")
            .config("spark.master", "local")
            .getOrCreate()

    
    val carsSchema = StructType(
        Array(
            StructField("Name", StringType),
            StructField("Miles_per_Gallon", DoubleType),
            StructField("Cylinders", LongType),
            StructField("Displacement", DoubleType),
            StructField("Horsepower", LongType),
            StructField("Weight_in_lbs", LongType),
            StructField("Acceleration", DoubleType),
            StructField("Year", StringType),
            StructField("Origin", StringType)
        )
    )


    // Reading a DF:
        // - format
        // - schema (optional) can use .option("inferSchema", "true")
        // - zero or more options
        // - path
        //  OPTIONS:

            // mode => the "mode" decides what Spark should do 
                        // in case it encounters a malformed record
            
    
    val carsDF = spark.read
        .format("json")
        .schema(carsSchema)
        .option("mode", "failFast") // other: "dropMalformed", "permissive" <- (default)
        .option("path", "src/main/resources/data/cars.json")
        .load()

    val carsDFWithOptionMap = spark.read
        .format("json")
        .options(Map(
            "mode" -> "failFast",
            "path" -> "src/main/resources/data/cars.json",
            "inferSchema" -> "true"
        ))
        .load()


    // Writing DataFrames
    //  - format
    //  - save mode => overwrite, append, ignore, errorIfExists
    //  - path
    //  - zero or more options

    carsDF.write
        .format("json")
        .mode(SaveMode.Overwrite)
        .save("src/main/resources/data/cars_dup.json")



}


//    // Create SparkSession
// val spark = SparkSession.builder()
//         .appName("Creating DataFrame")
//         .master("local[*]")
//         .getOrCreate()

// // Reading a CSV file
// val df = spark.read
//   .csv("path/to/file.csv")

// //Reading a JSON file
// val df = spark.read
//   .json("path/to/file.json")

// //Reading a text file
// val df = spark.read
//   .text("path/to/file.txt")

// //Reading a Parquet file with compression:
//   .parquet("path/to/file.parquet")

// //5.Reading a JDBC table with custom query:
// val df = spark.read
//   .format("jdbc")
//   .option("url", "jdbc:mysql://localhost:3306/mydb")
//   .option("dbtable", "mytable")
//   .option("user", "myuser")
//   .option("password", "mypassword")
//   .option("query", "SELECT * FROM mytable WHERE column1 > 100")
//   .load()

// 