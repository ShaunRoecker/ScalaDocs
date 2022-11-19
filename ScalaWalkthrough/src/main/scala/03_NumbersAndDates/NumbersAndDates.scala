object NumbersAndDates extends App {
    def scalaNumDate(): Unit = {
        // In Scala, the types Byte, Short, Int, Long, and Char are known
        //  as integral types because they are represented by integers

        // All of the number types extend AnyVal -> https://www.scala-lang.org/api/2.12.4/scala/AnyVal.html
        // All other types in the scala class hierarchy extend AnyRef ->  

        // If you ever want to know the exact values of the data, use:
        println(Short.MaxValue) // Char, Byte, Short, Long, Int, Float, Double
        println(Short.MinValue)

        // underScoresInNumericLiterals()
        // parsingANumberFromAString()
        // convertingBetweenNumericTypes_Casting()
        // comparingFloatingPointNumbers()
        // handlingLargeNumbers()
        // generatingRandomNumbers()
        // formattingNumbersAndCurrency()
        // creatingNewDateAndTimeInstances()
        // CalculatingTheDifferenceBetweenTwoDates()
        // formattingDates()
        parsingStringsIntoDates()

    }
    scalaNumDate()
    // Underscores In Numeric Literals
    def underScoresInNumericLiterals(): Unit = {
        // Can use underscore to represent numeric literals

        // Int
        val x = 1_000
        val y = 100_000
        val z = 1_000_000

        // Long
        val long = 1_000_000L

        // Double
        val double = 1_123.45
        val double2 = 1_123.45D
        val double3 = 1_123.45d
        val double4 = 1_1234e2  //123400.0

        // BigInt and BigDecimal
        val bigInt: BigInt = 1_000_000
        val bigDec: BigDecimal = 1_234.56
        

        // Numeric literal with underscores can be used almost* anywhere
        val x2 = 1_000 + 1
        if x2 > 1_000 && x2 < 1_000_000 then println(x2)

        x2 match 
            case 1_000 => println("got 1,000")
            case _     => println("got something else")
        
        for 
            i <- 1 to 1_000
            if i > 999
        do 
            println(i)

        // Only place they can't be used is when casting from string to numeric types
        // Integer.parseInt("1_000") <- error
        // "1_000".toInt <- error

        // for complex numbers, check out "https://typelevel.org/spire"      
    }
    //Parsing A Number From A String
    def parsingANumberFromAString(): Unit = {
        //Problem: You want to convert a string to one of Scala's numeric types
        "1".toByte
        "1".toShort
        "1".toInt
        "1".toLong
        "1".toFloat
        "1".toDouble

        // Can also use to*Option methods
        println("1".toIntOption) // Some(1)

        //  Handling a base and radix with Int 
        println(Integer.parseInt("1", 2)) //1
        println(Integer.parseInt("10", 2)) //2
        println(Integer.parseInt("100", 2)) //4
        println(Integer.parseInt("1", 8)) //1
        println(Integer.parseInt("10", 8))  //8

        // In Scala, you are not required to declare "throws NumberFormatException"
        def makeIn(s: String): Int = s.toInt

        // ^^^ Not for functional programming though, can short-circuit a caller's code. Not cool.

        // makeIn functional style:  <- same as toIntOption
        def makeInFP(s: String): Option[Int] =
            try 
                Some(s.toInt)
            catch 
                case e: NumberFormatException => None
            
        
        // shorter version of makeInFP
        import scala.util.Try
        def makeInFP_short(s: String): Option[Int] = Try(s.toInt).toOption

        // using try for error verbosity
        import scala.util.{Try, Success, Failure}
        def makeIntFP_try(s: String): Try[Int] = Try(s.toInt)

    }
    //Converting Between Numeric Types: Casting
    def convertingBetweenNumericTypes_Casting(): Unit = {
        // Problem: You want to convert from one numeric type to another
        val b: Byte = 1
        b.toInt
        b.toFloat

        // for boolean responses, can use
        // x.isValidInt
        // x.isValidByte

        // x.asInstanceOf[Float]
    }
    //Overriding The Default Numeric Type
    def overridingTheDefaultNumericType(): Unit = {
        // Problem: When using an implicit type declaration style, Scala automatically assigns
        // types based on their numeric values, and you need to override the default type declaration   
        // when you create a numeric field.

    }
    // Comparing Floating Point Numbers
    def comparingFloatingPointNumbers(): Unit = {
        val a = 0.1 + 0.2
        println(a)  // 0.30000000000000004
        // This inaccuracy can make comparing floating point numbers difficult

        val point3a = 0.3
        val point3b = 0.1 + 0.2
        println(point3a == point3b)  // false

        // To fix this problem we need to write our own functions to compare floating point numbers
        // with a tolerance

        import scala.annotation.targetName
        @targetName("approxEqual")
        def ~=(x: Double, y: Double, tolerance: Double): Boolean = 
            if (x - y).abs < tolerance then true else false
        

        val aEqualsB = ~=(point3a, point3b, 0.0001)
        println(aEqualsB)

        // These method names, explained...
        // Scala is not nearly as limited it terms of the names you can assign to methods
        // than some other program languages.  This is a legit method name:  F*$%_O|||\()
        // @targetName() allows you to have an alias for your weird (or convienient) method
        // name, that can be used to increase interoperability with other languages or 
        // makes it easier to use stacktraces, where the tagetName is supplies instead of the
        // method's symbolic name.  Not necessary in most situations, but it's just nice, you know?
        // It's nice to have nice things like @targetName

        //  To make this method an "extension" of the built-in class Double, you can do this

        extension (x: Double)
            def =~=(y: Double, tolerance: Double): Boolean =
                if (x - y).abs < tolerance then true else false


        // You can hard code the tolerance however you want
        extension (x: Double)
            def ~==(y: Double): Boolean =
                if (x - y).abs < 0.05 then true else false

        // Or make it a percentage of the values being compared...
        extension (x: Double)
            def ~=%(y: Double): Boolean =
                // allow a +/- 10% variance
                val xHigh = if x > 0 then x*1.1 else x*0.9
                val xLow = if x > 0 then x*0.9 else x*1.1
                if y >= xLow && y<= xHigh then true else false
        
        
        println("custom equals with @targetName")
        println(5.9 ~=% 5.99)  // true
        println(5 ~=% 10)  // false
        println(5.0 =~=(5.1, 0.2)) // true
        println(5.0 =~=(5.1, 0.0001)) // false
        

        // We can extend any built-in class with custom methods, even using names
        // considered uncouth in other programming languages.   
        // Programmer 1: "I love scala."
        // Programmer 2: "There's a method for that."

        // What every programmer should know about floating-point arithmatic -"Dogs, to the layperson"
        
    }
    // Handling Large Numbers
    def handlingLargeNumbers(): Unit = {
        // Problem: You're writting an application and need to use very large integer or decimal numbers
        println("HANDLING LARGE NUMBERS")
        
        println(Double.MaxValue)
        println(Long.MaxValue)

        // BigDecimal is often used for currency
        // BigDecimal is more exact than Double, BigDecimal(0.10) + BigDecimal(0.20) // => BigDecimal = 0.3

        // However be careful about using Double to construct BigDecimal values
        // BigDecimal(0.10 + 0.20) => BigDecimal = 0.30000000000000004

        println(s"Nanotime: ${System.nanoTime()}")
        


        
    }
    // Generating Random Numbers
    def generatingRandomNumbers(): Unit = {
        println("GENERATING RANDOM NUMBERS")
        val r = scala.util.Random

        // Random Integers
        println(r.nextInt) //-1225422615
        println(r.nextInt) // -572260221

        // Returns a value between 0.0 and 1.0 
        println(r.nextDouble) //0.48162503555225866
        println(r.nextDouble) // 0.006933981711459314
        // Returns a value between 0.0 and 1.0 
        println(r.nextFloat)  // 0.543987
        println(r.nextFloat) // 0.006444633

        // Set a seed when creating a new instance of Random
        // (I seed is a way to gernerate the same random number at different times)
        val rando1 = scala.util.Random(31)
        println(rando1)
        // You can update the seed after you already have a Random instance
        rando1.setSeed(1_000L)

        //   Limit the integers to a maximum value
        println(rando1.nextInt(6)) // max is 5
        // when setting a maximum value on nextInt, the Int returned is between 0 (inclusive),
        // and the value you specify, so specifying 100 returnan integer from 0 to 99

        // You can also create random length ranges:
        0 to rando1.nextInt(10) // range 0 to 9

        // You can always convert a range to another sequence type
        println((0 to rando1.nextInt(10)).toList)
        println((0 to rando1.nextInt(10)).toVector)
        // A random size LazyList
        val llr = (0 to rando1.nextInt(1_000_000)).to(LazyList) 
        println(llr) // LazyList(<not computed>)

        // A for/yield loop gives you a nice way to modify the values in a sequence
        println(for i <- 0 to rando1.nextInt(10) yield i*10) //Vector(0, 10, 20, 30, 40, 50, 60, 70, 80, 90)

        // Fixed-Length ranges with random values
        val seq = for i <- 1 to 5 yield rando1.nextInt(2) 
        println(seq)  //Vector(0, 1, 0, 0, 0)

        // You can do the same thing with nextFloat and nextDouble
        val floats = for i <- 1 to 5 yield rando1.nextFloat() 
        println(floats)  // Vector(0.04350257, 0.60081404, 0.41084892, 0.5503761, 0.51346815)

        val doubles = for i <- 1 to 5 yield rando1.nextDouble() 
        println(doubles)  //Vector(0.6580583901495688, 0.9744965039734514, 0.6300783865329214, 0.848943650191653, 0.35625029673016806)

        // Shuffling an existing Sequence
        import scala.util.Random
        val x = List(1, 2, 3)

        println(Random.shuffle(x)) // List(2, 3, 1)

        // Getting a random element from a sequence
        def getRandomElement[A](list: Seq[A], random: Random): A =
            list(random.nextInt(list.length))

        val ran = scala.util.Random

        // integers
        val ints = (1 to 100).toList
        
        println(getRandomElement(ints, ran)) // Int = 77
        println(getRandomElement(ints, ran)) // Int = 89

        // strings
        val names = List("Hala", "Helia", "Hannah", "Hope")
        println(getRandomElement(names, ran)) // Hannah
        println(getRandomElement(names, ran)) // Hope


    }
    // Formatting Numbers and Currency
    def formattingNumbersAndCurrency(): Unit = {
        import java.text.NumberFormat

        println(NumberFormat.getInstance)
        NumberFormat.getIntegerInstance
        NumberFormat.getCurrencyInstance
        NumberFormat.getPercentInstance

        val pi = scala.math.Pi
        println(f"${pi}%1.2f")  //3.14
        println(f"${pi}%1.3f")  //3.142
        println(f"${pi}%1.5f")  //3.14159
        println(f"${pi}%6.2f")  //   3.14
        println(f"${pi}%06.2f")  //003.14

        // whole numbers
        val x = 10_000
        println(f"${x}%d") //10000
        println(f"${x}%2d") //10000
        println(f"${x}%8d") //
        println(f"${x}%-8d") //
        
        // another version of format, hey just like Python
        println("%06.2f".format(pi))  //003.14

        // Commas, Locales, and Integers
        // import java.text.NumberFormat
        val formatter = NumberFormat.getIntegerInstance
        println(formatter.format(10_000))  //10,000
        println(formatter.format(1_000_000))  //1,000,000

        import java.util.Locale
        
        // Switch formats to locales, such as US or Europe
        val formatter2 = NumberFormat.getIntegerInstance(Locale.GERMANY)
        println(formatter2.format(1_000))  //1.000
        println(formatter2.format(1_000_000)) //1.000.000

        // CURRENCY
        val formatterCurrency = NumberFormat.getCurrencyInstance
        println(formatterCurrency.format(10_000)) // $10,000.00
        println(formatterCurrency.format(1_000_000)) // $1,000,000.00
        println(formatterCurrency.format(1_123_567.54)) // $1,123,567.54

        // Use Locale to format international currency
        import java.util.{Currency, Locale}

        val deCurrency = Currency.getInstance(Locale.GERMANY)
        val deFormatter = java.text.NumberFormat.getCurrencyInstance
        deFormatter.setCurrency(deCurrency)

        println(deFormatter.format(123.456789)) // €123.46
        println(deFormatter.format(1_234.456789)) // €1,234.46
        println(deFormatter.format(1_123_567.456789)) // €1,123,567.46

        //  If you don't use a currency library, you can use BigDecimal
        //import java.text.NumberFormat
        import scala.math.BigDecimal.RoundingMode
        import scala.math.BigDecimal.RoundingMode

        val a = BigDecimal("10000.995")
        val b = a.setScale(2, RoundingMode.DOWN)
        val formatterA = NumberFormat.getCurrencyInstance
        println(formatterA.format(b)) //$10,000.99

        val c = BigDecimal("1234567.891").setScale(2, RoundingMode.DOWN)
        println(c) // 1234567.89
        println(deFormatter.format(c)) //€1,234,567.89

        val ukFormatter = NumberFormat.getCurrencyInstance(Locale.UK)
        println(ukFormatter.format(c)) // £1,234,567.89

        // Custom Formatting Patterns
        import java.text.DecimalFormat
        
        val df = DecimalFormat("0.##")
        println(df.format(123.35)) //123.35
        println(df.format(123.355)) //123.36
        println(df.format(.1234355)) //0.12

        val df2 = DecimalFormat("0.####")
        println(df2.format(123.35)) //123.35
        println(df2.format(123.35543535)) //123.36
        println(df2.format(.1234355)) //0.12

        val df3 = DecimalFormat("#,###,##0.00")
        println(df3.format(122)) //122.00
        println(df3.format(123.46)) //123.46
        println(df3.format(1_234.567889)) //1,234.57

        //Reference: "https://oreil.ly/nvJda"

        // LOCALES  Ref: https://oreil.ly/fjQXp
        // java.util.Locale
        // Locale("en-AU", "AU")
        // Locale("pt-BR","BR")

        // Demonstrate how to use
        // India
        import java.util.{Currency, Locale}

        val indiaLocale = Currency.getInstance(Locale("hi-IN", "IN"))
        val formatterIn = java.text.NumberFormat.getCurrencyInstance
        formatterIn.setCurrency(indiaLocale)
        println(formatterIn.format(123.456789)) //₹123.46
        println(formatterIn.format(1_234.456789)) //₹1,234.46

        // Set a default locale
        val default = Locale.getDefault
        val formatterDefault = NumberFormat.getInstance(default)

        println(formatterDefault.format(12.34)) //12.34
        println(formatterDefault.format(1_200.34)) //1,200.34
        println(formatterDefault.format(1_245_656.34)) //1,245,656.34

    }
    // Creating Date and Time Instances from Java Classes and 
    // using the Instance class to build a timer
    def creatingNewDateAndTimeInstances(): Unit = {
        // Problem: You need to create new date and time instances
        // using the Date and Time API that was introduces with Java 8
        // Ref: https://oreil.ly/7T8dh
        import java.time.*
        println("CREATING NEW DATE AND TIME INSTANCES")
        println(LocalDate.now) //2022-11-16
        println(LocalTime.now) //12:57:22.790298
        println(LocalDateTime.now) //2022-11-16T12:57:22.790445
        println(Instant.now) //2022-11-16T17:57:22.790547Z
        println(ZonedDateTime.now) //2022-11-16T12:57:22.792938-05:00[America/New_York]

        // Past or Future  //ref: https://oreil.ly/qXo7f
        val squirrelDay = LocalDate.of(2020, 1, 21) //2020-01-21
        println(squirrelDay)
        val squirrelDay2 = LocalDate.of(2020, Month.JANUARY, 21) //2020-01-21
        println(squirrelDay2)
        val squirrelDay3 = LocalDate.of(2020, 1, 21).plusDays(20) //2020-02-10
        println(squirrelDay3)

        //java.time.LocalTime(https://oreil.lyTNNWb) has 5 of* factory methods, including these
        // LocalTime.of(hour: Int, minute: Int)
        // LocalTime.of(hour: Int, minute: Int, second: Int)

        println(LocalTime.of(0, 0)) //00:00
        println(LocalTime.of(0, 1)) // 00:01
        println(LocalTime.of(1, 1)) //01:01
        println(LocalTime.of(23, 59)) //23:59

        //java.time.LocalDateTime(https://oreil.ly/eHIH4) has 9 of* factory method constructors,
        // including these
        //LocalDateTime.of(year: Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int)
        //LocalDateTime.of(year: Int, month: Month, dayOfMonth: Int, hour: Int, minute: Int)
        //LocalDateTime.of(date: LocalDate, time: LocalTime)


        //java.time.ZonedDateTime(https://oreil.ly/nmoY3) has 5 of* factory methods, including these
        //ZonedDateTime.of(int year, int month, int dayOfMonth, int hour, int minute, int second, 
            //int nanosecond, ZoneId zone)
        //ZonedDateTime.of(LocalDate date, LocalTime time, ZoneId zone)  ** used below **
        //ZonedDateTime.of(Instant instant, ZoneId zone)

        // Example of the second method:
        val zdt = ZonedDateTime.of(
            LocalDate.now,
            LocalTime.now,
            ZoneId.of("America/New_York")
        )
        println(zdt)  //2022-11-16T13:58:06.191462-05:00[America/New_York]
        // ref(zone IDs) https://oreil.ly/h7A9T

        // The Instant Class
        // The instant class is nice for many reasons, including giving you the ability to
        // calculate the time duration between two instants 
        import java.time.{Instant, Duration}

        val start = Instant.now
        Thread.sleep(2_000)
        val stop = Instant.now
        val delta = Duration.between(start, stop)
        println(delta)           // PT2.003014S
        println(delta.toMillis)  // 2003
        println(delta.toNanos)   // 2003014000
        
    }
    // Calculating the difference between two dates
    def CalculatingTheDifferenceBetweenTwoDates(): Unit = {
        // Problem: You need to calculate the difference bewteen two dates
        println("Calculating the difference between two dates")
        import java.time.LocalDate
        import java.time.temporal.ChronoUnit.*

        val now = LocalDate.of(2022, 11, 16)
        val xmas = LocalDate.of(2022, 12, 25)

        println(DAYS.between(now, xmas)) // 39
        println(MONTHS.between(now, xmas)) // 1
        println(YEARS.between(now, xmas)) // 0

        val nextXmas = LocalDate.of(2023, 12, 25)
        val years: Long = YEARS.between(now, nextXmas)
        println(years) // 1
        println(DAYS.between(now, nextXmas)) // 404

        // Using the same LocalDate values, you can also use the Period class,
        // but notice the significant difference in the output between the ChronoUnit
        // and Period Approaches

        import java.time.Period

        val diff = Period.between(now, nextXmas)
        println(diff.getYears) // 1
        println(diff.getMonths) // 1
        println(diff.getDays) // 9
        // This Period method measures the difference of the specific time unit,
        // such as date1 month vs. date2 month, not the difference as a whole

        // The between method of ChronoUnit:
        import java.time.LocalDateTime
        import java.time.temporal.ChronoUnit

        // of(year, month, dayOfMonth, hour, minute)
        val d1 = LocalDateTime.of(2020, 1, 1, 1, 1)
        val d2 = LocalDateTime.of(2063, 4, 5, 1, 1)

        println(ChronoUnit.DAYS.between(d1, d2)) // 15800
        println(ChronoUnit.MONTHS.between(d1, d2)) // 519
        println(ChronoUnit.YEARS.between(d1, d2)) // 43
        println(ChronoUnit.MINUTES.between(d1, d2)) // 22752000
        println(ChronoUnit.SECONDS.between(d1, d2)) // 1365120000

        // The ChronoUnit class has many other enum constants, including CENTURIES, DECADES,
        // HOURS, MICROS, MILLIS, WEEKS, and more.


    }
    // Formatting dates
    def formattingDates(): Unit = {
        // Problem: You need to print dates in a desired format
        println("FORMATTING DATES")
        import java.time.LocalDate
        import java.time.format.DateTimeFormatter

        val d = LocalDate.now 
        println(d) // 2022-11-16
        val f = DateTimeFormatter.BASIC_ISO_DATE
        println(f.format(d)) // 20221116

        // Other Formatters:
        //    ISO_LOCAL_DATE  //2021-02-04
        //    ISO_DATE        //2021-02-04
        //    BASIC_ISO_DATE  //20210204
        //    ISO_ORDINAL_DATE  //2021-035
        //    ISO_WEEK_DATE  //2021-W05-4

        //  Locale Formatters
        //  ofLocalizedDate
        //  ofLocalizedTime
        //  ofLocalizedDateTime

        // java.time.format.FormatStyle (https://oreil.ly/K50kH)
        // (SHORT, MEDIUM, LONG, FULL)

        // import java.time.LocalDate
        import java.time.format.{DateTimeFormatter, FormatStyle}

        val d2 = LocalDate.of(2021, 1, 1)
        val fFull = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
        println(fFull.format(d2)) // Friday, January 1, 2021

        val fShort = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        println(fShort.format(d2)) // 1/1/21

        // Custom Patterns with ofPattern
        val d3 = LocalDate.now
        val f3 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        println(f3.format(d3)) // 2022-11-16

        val f4 = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        println(f4.format(d3)) // 11/16/2022

        val f5 = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        println(f5.format(d3)) // Nov 16, 2022

        val f6 = DateTimeFormatter.ofPattern("E, MMM dd yyyy")
        println(f6.format(d3)) // Wed, Nov 16 2022

        //  This example demonstrates how to format LocalTime:
        import java.time.LocalTime
        val t = LocalTime.now
        val ft = DateTimeFormatter.ofPattern("h:mm a")
        println(ft.format(t)) //3:03 PM
        // Other time formats: 
            // "HH:mm:ss a"

        // Formatting LocalDateTime
        import java.time.LocalDateTime
        val dt = LocalDateTime.now
        val fdt = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a")
        
        println(fdt.format(dt)) // Nov 16, 2022 3:05 PM

    }
    // Parsing Strings into Dates
    def parsingStringsIntoDates(): Unit = {
        println("Parsing strings into dates")
        // Problem: You need to parse a string into one of the date/time types 
        // introduced in Java 8
        // ** If your string is in the expected format, pass it to the parse method
        // of the desired class.  If the string is not in the expected (default) format,
        // create a formatter to define the format you want to accept.

        // LocalDate
        import java.time.LocalDate
        val d = LocalDate.parse("2020-12-10") // LocalDate = 2020-12-10
        println(d) //2020-12-10
        // if val d = LocalDate.parse("2020/12/10") // Error: DateTimeParseException

        import java.time.format.DateTimeFormatter
        val df = DateTimeFormatter.ofPattern("yyy/MM/dd")
        val d2 = LocalDate.parse("2020/12/10", df) //2020-12-10
        println(d2)

        // LocalTime
        import java.time.LocalTime
        val t = LocalTime.parse("01:02") //01:02
        val t2 = LocalTime.parse("13:02:03") //13:02:03
        println(t) //
        println(t2)

        // Notice each field requires a leading 0
        // println(LocalTime.parse("1:01"))// Error

        println(LocalTime.parse("00:00", DateTimeFormatter.ISO_TIME)) //00:00
        println(LocalTime.parse("23:59", DateTimeFormatter.ISO_LOCAL_TIME)) //23:59
        println(LocalTime.parse("23 59 59", DateTimeFormatter.ofPattern("HH mm ss"))) //23:59:59
        println(LocalTime.parse("11 59 59 PM", DateTimeFormatter.ofPattern("hh mm ss a"))) //23:59:59

        // LocalDateTime
        import java.time.LocalDateTime
        // Default LocalDateTime format
        val s = "2021-01-01T12:13:14"
        val ldt = LocalDateTime.parse(s) //2021-01-01T12:13:14
        println(ldt)

        val s2 = "1999-12-31 23:59"
        val fdtf = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm")
        val ldt2 = LocalDateTime.parse(s2, fdtf) //1999-12-31T23:59
        println(ldt2)

        val y = "1999-12-31 11:59:59 PM"
        val z = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a")
        val x = LocalDateTime.parse(y, z) // 1999-12-31T23:59:59
        println(x)

        // Instant
        // java.time.Instant only has one parse method that requires a date/time stamp
        // in the proper format
        import java.time.Instant
        println(Instant.parse("1970-01-01T00:01:02.00Z")) //1970-01-01T00:01:02Z
        println(Instant.parse("2021-01-22T23:59:59.00Z")) //2021-01-22T23:59:59Z

        // ZonedDateTime
        // Demonstrate the default parse methods for java.time.ZonedDateTime
        import java.time.ZonedDateTime
        println(ZonedDateTime.parse("2020-12-13T23:59:59-06:00")) //2020-12-13T23:59:59-06:00
        println(ZonedDateTime.parse("2020-12-13T23:59:59-00:00[US/Mountain]")) //2020-12-13T16:59:59-07:00[US/Mountain]

        import java.time.format.DateTimeFormatter.*

        val zdt = ZonedDateTime.parse("2021-01-01T01:02:03Z", ISO_ZONED_DATE_TIME) //2021-01-01T01:02:03Z
        println(zdt)
        println(ZonedDateTime.parse("2021-01-01T23:59:59+01:00", ISO_DATE_TIME)) //2021-01-01T23:59:59+01:00
        println(ZonedDateTime.parse("2021-03-29T00:59:59-01:00", ISO_OFFSET_DATE_TIME)) //2021-03-29T00:59:59-01:00
        println(ZonedDateTime.parse("Sat, 29 Feb 2020 00:01:02 GMT", RFC_1123_DATE_TIME)) //2020-02-29T00:01:02Z


    }

}