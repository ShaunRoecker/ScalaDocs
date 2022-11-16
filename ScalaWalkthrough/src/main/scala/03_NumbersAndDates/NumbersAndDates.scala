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
        formattingNumbersAndCurrency()


    }
    scalaNumDate()
    //
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
    //
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
    //
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
    //
    def overridingTheDefaultNumericType(): Unit = {
        // Problem: When using an implicit type declaration style, Scala automatically assigns
        // types based on their numeric values, and you need to override the default type declaration   
        // when you create a numeric field.

    }
    //
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
    //
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
        println(deFormatter.format(1_234.456789)) // €123.46
        println(deFormatter.format(1_123_567.456789)) // €123.46
        
    }
}