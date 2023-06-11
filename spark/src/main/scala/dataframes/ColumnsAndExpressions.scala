package dataframes.columns

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, column, expr}

object ColumnsAndExpressions extends App {
    val spark = SparkSession.builder()
        .appName("Columns and Expressions")
        .config("spark.master", "local")
        .getOrCreate()

    import spark.implicits._

    val cars = spark.read
        .option("inferSchema", "true")
        .json("src/main/resources/data/cars.json")

    
    val firstColumn = cars.col("Name")


    val carNames = cars.select(firstColumn)

    // Note: below is many ways to do the same thing
    cars.select(
        cars.col("Name"),
        col("Acceleration"),
        column("Weight_in_lbs"),
        Symbol("Year"), // Scala Symbol, auto-converted to column
        $"Horsepower", // interpolated string, returns a column object
        expr("Origin") // EXPRESSION
    )

    // Another shorter way to select, however cannot interchange with the methods above
    cars.select("Name", "Year")

    val simplestExpression = cars.col("Weight_in_lbs")
    val weightInKgExpression = cars.col("Weight_in_lbs") / 2.2

    val carsWithWeight = cars.select(
        col("Name"),
        col("Weight_in_lbs"),
        weightInKgExpression.as("Weight_in_kg"),
        expr("Weight_in_lbs / 2.2").as("Weight In Kilograms(Kg)")
    )

    carsWithWeight.show(3)
// root +--------------------+-------------+------------------+-----------------------+
// root |                Name|Weight_in_lbs|      Weight_in_kg|Weight In Kilograms(Kg)|
// root +--------------------+-------------+------------------+-----------------------+
// root |chevrolet chevell...|         3504|1592.7272727272725|            1592.727273|
// root |   buick skylark 320|         3693|1678.6363636363635|            1678.636364|
// root |  plymouth satellite|         3436|1561.8181818181818|            1561.818182|
// root +--------------------+-------------+------------------+-----------------------+


    val carsWithSelectExprWeightsDF = cars.selectExpr(
        "Name",
        "Weight_in_lbs",
        "Weight_in_lbs / 2.2",
    )

    carsWithSelectExprWeightsDF.show(3)
// root +--------------------+-------------+---------------------+
// root |                Name|Weight_in_lbs|(Weight_in_lbs / 2.2)|
// root +--------------------+-------------+---------------------+
// root |chevrolet chevell...|         3504|          1592.727273|
// root |   buick skylark 320|         3693|          1678.636364|
// root |  plymouth satellite|         3436|          1561.818182|
// root +--------------------+-------------+---------------------+

    // Adding a column
    val carsWithKg3DF = cars.withColumn("Weight_in_kg_3", col("Weight_in_lbs") / 2.2)
    // renaming a column
    val carsWithColumnRenamed = cars.withColumnRenamed("Weight_in_lbs", "Weight in pounds")
    // be careful with column name spaces
    // Just like in normal Scala code, use backticks for variables with spaces or hyphens
    carsWithColumnRenamed.selectExpr("`Weight in pounds`")

    carsWithColumnRenamed.show(3)
// root +------------+---------+------------+----------+----------------+--------------------+------+----------------+----------+
// root |Acceleration|Cylinders|Displacement|Horsepower|Miles_per_Gallon|                Name|Origin|Weight in pounds|      Year|
// root +------------+---------+------------+----------+----------------+--------------------+------+----------------+----------+
// root |        12.0|        8|       307.0|       130|            18.0|chevrolet chevell...|   USA|            3504|1970-01-01|
// root |        11.5|        8|       350.0|       165|            15.0|   buick skylark 320|   USA|            3693|1970-01-01|
// root |        11.0|        8|       318.0|       150|            18.0|  plymouth satellite|   USA|            3436|1970-01-01|
// root +------------+---------+------------+----------+----------------+--------------------+------+----------------+----------+


    // removing columns
    carsWithColumnRenamed.drop("Cylinders", "Displacement").show(2)

// root +------------+----------+----------------+--------------------+------+----------------+----------+
// root |Acceleration|Horsepower|Miles_per_Gallon|                Name|Origin|Weight in pounds|      Year|
// root +------------+----------+----------------+--------------------+------+----------------+----------+
// root |        12.0|       130|            18.0|chevrolet chevell...|   USA|            3504|1970-01-01|
// root |        11.5|       165|            15.0|   buick skylark 320|   USA|            3693|1970-01-01|
// root +------------+----------+----------------+--------------------+------+----------------+----------+


    // Filtering

    val euroCars = cars.filter(col("Origin") === "Europe")
    val euroCars2 = cars.where(col("Origin") === "Europe")
    val euroCars3 = cars.filter((col("Origin") =!= "USA") && (col("Origin") =!= "Japan"))

    val americanCars = cars.filter("Origin = 'USA'")

    euroCars.show(2)

// root +------------+---------+------------+----------+----------------+--------------------+------+-------------+----------+
// root |Acceleration|Cylinders|Displacement|Horsepower|Miles_per_Gallon|                Name|Origin|Weight_in_lbs|      Year|
// root +------------+---------+------------+----------+----------------+--------------------+------+-------------+----------+
// root |        17.5|        4|       133.0|       115|            null|citroen ds-21 pallas|Europe|         3090|1970-01-01|
// root |        20.5|        4|        97.0|        46|            26.0|volkswagen 1131 d...|Europe|         1835|1970-01-01|
// root +------------+---------+------------+----------+----------------+--------------------+------+-------------+----------+


    val americanPowerCars = 
        cars.filter {
            (col("Origin") === "USA") && 
            (col("Horsepower") > 150)
        }

    americanPowerCars.show(3)

// root +------------+---------+------------+----------+----------------+-----------------+------+-------------+----------+
// root |Acceleration|Cylinders|Displacement|Horsepower|Miles_per_Gallon|             Name|Origin|Weight_in_lbs|      Year|
// root +------------+---------+------------+----------+----------------+-----------------+------+-------------+----------+
// root |        11.5|        8|       350.0|       165|            15.0|buick skylark 320|   USA|         3693|1970-01-01|
// root |        10.0|        8|       429.0|       198|            15.0| ford galaxie 500|   USA|         4341|1970-01-01|
// root |         9.0|        8|       454.0|       220|            14.0| chevrolet impala|   USA|         4354|1970-01-01|
// root +------------+---------+------------+----------+----------------+-----------------+------+-------------+----------+

    val americanPowerCars2 =
        cars.filter("Origin = 'USA' and Horsepower > '150'")

    
    
    //  UNIONS = adding more rows

    val moreCars = spark.read
        .option("inferSchema", "true")
        .json("src/main/resources/data/more_cars.json")


    val allCars = cars.union(moreCars) // works if DFs have the same schema


    val allCountriesDF = cars.select("Origin").distinct()
    allCountriesDF.show()

// root +------+
// root |Origin|
// root +------+
// root |Europe|
// root |   USA|
// root | Japan|
// root +------+



    val movies = spark.read
        .option("inferSchema", "true")
        .json("src/main/resources/data/movies.json")

    movies.show(3)

    val movies2 = movies.select(col("Title"), col("IMDB_Rating"))
    movies2.show(3)

// root +--------------------+-----------+
// root |               Title|IMDB_Rating|
// root +--------------------+-----------+
// root |      The Land Girls|        6.1|
// root |First Love, Last ...|        6.9|
// root |I Married a Stran...|        6.8|
// root +--------------------+-----------+


    val movies3 = movies.select(
        col("Title"),
        col("US_Gross"),
        col("Worldwide_Gross"),
        (col("US_Gross") + col("Worldwide_Gross")).as("Total_Gross")
    )

    val movies4 = movies.withColumn("Total_Profit", col("US_Gross") + col("Worldwide_Gross"))

    val movies5 = movies.selectExpr(
        "Title",
        "US_Gross",
        "Worldwide_Gross",
        "US_Gross + Worldwide_Gross as Total_Gross"
    )


    movies3.show(3)
    movies4.show(3)
    movies5.show(3)


// root +--------------------+--------+---------------+-----------+
// root |               Title|US_Gross|Worldwide_Gross|Total_Gross|
// root +--------------------+--------+---------------+-----------+
// root |      The Land Girls|  146083|         146083|     292166|
// root |First Love, Last ...|   10876|          10876|      21752|
// root |I Married a Stran...|  203134|         203134|     406268|
// root +--------------------+--------+---------------+-----------+

    
    movies
        .filter(
            (col("Major_Genre") === "Comedy") && 
            (col("IMDB_Rating") > 6)
        )
        .select(col("Title"), col("Major_Genre"), col("IMDB_Rating"))
        .show(3)

// root +--------------------+-----------+-----------+
// root |               Title|Major_Genre|IMDB_Rating|
// root +--------------------+-----------+-----------+
// root |I Married a Stran...|     Comedy|        6.8|
// root |24 7: Twenty Four...|     Comedy|        6.9|
// root |          Four Rooms|     Comedy|        6.4|
// root +--------------------+-----------+-----------+
    

}


