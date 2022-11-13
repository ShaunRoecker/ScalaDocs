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
        convertingBetweenNumericTypes_Casting()

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
        
    }
}