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
        //handlingLargeNumbers()

    }
    scalaNumDate()
    
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

    def overridingTheDefaultNumericType(): Unit = {
        // Problem: When using an implicit type declaration style, Scala automatically assigns
        // types based on their numeric values, and you need to override the default type declaration   
        // when you create a numeric field.

    }

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
}