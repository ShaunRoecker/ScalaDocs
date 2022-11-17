

object ControlStructures extends App {
  def scalaControlStructures(): Unit = {
        //  As their name implies, "control structures" provide a way for programmers to control
        // the flow of a program.  They're are a fundamental feature of programming languages that let
        // you handle decision making and looping tasks

        println("CONTRIOL STRUCTURES")

        // Control Structures in Scala:
            // 'for' loops and 'for' expressions
            // if/then/else 'if' expressions
            // 'match' expressions (pattern matching)
            // try/catch/finally blocks
            // while loops
        
        // controlStructureOverview()
        loopingOverDataStructuresWithFor()
    }
    scalaControlStructures()

    def controlStructureOverview(): Unit = {
                // 'for' loops and 'for' expressions
        // basic: loop through a collection to perform an operation on the elements of the collection
        for i <- List(1, 2, 3) do println(i)

        // for loops can also have "guards"- embedded if statements:
        for 
            i <- 1 to 10
            if i > 3
            if i < 6
        do
            println(i)
        
        // With the use of the "yield" keyword, for loops also become 'for' expressions - 
        // loops that yield a result:
        val listOfInts = for 
            i <- 1 to 30
            if i > 3
            if i < 6
        yield 
            i * 10

        println(listOfInts) // Vector(40, 50)

        // if/then/else-if Expressions
        // while for loops and expressions let you traverse over a collection, if/then/else expressions
        // provide a way to make branching decisions.

        //val absValue = if a < 0 then -a else a

        def compare(a: Int, b: Int): Int =
            if a < b then
                -1
            else if a == b then
                0
            else
                1
        end compare

        println(compare(2, 3)) // -1

        // As shown in both of those examples, an if expression truly is an expression that returns a value

        //  Matching Expressions and Pattern Matching
        // Pattern Matching are a defining feature of Scala and very powerful
        
        def isTrue(a: Matchable): Boolean = a match
            case false | 0 | "" => false
            case _ => true

        println(isTrue(0)) //false

        // ty/catch/finally blocks
        //
        // try
            // some exception-throwing code here
        // catch
            // case e1: Exception1Type => //Handle that exception
            // case e2: Exception2Type => //Handle that exception
        // finally
            // close your resources and do anything else necessary here
        
        // Like if and match, try is an expression that returns a value, so you can write
        // code like this to transform a String into an Int
        def toInt(s: String): Option[Int] =
            try 
                Some(s.toInt)
            catch 
                case e: NumberFormatException => None
        
        println(toInt("1")) //Option[Int] = Some(1)
        println(toInt("YOLO!")) //Option[Int] = None

        // 'while' loops
        // while loops are rarely used in Scala
        // This is because while loops are mostly used for side-effects("booo!"), such as updating mutable
        // variables and printing with println, and these are things you can also do with 'for' loops 
        // and the foreach method on collections.  However, if you ever need to use one, they look like this:
        var i = 0
        while 
            i < 10
        do
            println(i)
            i += 1
    }

    // Control Structures As A Defining Feature of Programming Languages

    // Looping over Data Structures with 'for'
    def loopingOverDataStructuresWithFor(): Unit = {


    }
}
