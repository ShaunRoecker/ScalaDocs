

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
        // loopingOverDataStructuresWithFor()
        // usingForLoopsWithMultipleCounters()
        // usingAForLoopWithEmbeddedIfStatements()
        // creatingANewCollectionFromAnExistingCollectionWithForYield()
        usingTheIfConstructLikeATernaryOperator()
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
        println("Looping Over Data Structure With for")
        // Problem: You want to iterate over the elements in a collection in the manner of a traditional for loop
        // There are many ways to loop over collections, including for loops, while loops, and collection methods
        // like foreach, map, flatmap, and more.  This solution focuses primarily on the for loop
        val fruits = List("apple", "banana", "orange")

        for f <- fruits do println(f)
        //  This same approach works for all sequences, including List, Seq, Vector, Array, ArrayBuffer, etc.

        // Use indentation for multiple lines  
        for f <- fruits do 
            val s = f.toUpperCase
            println(s)
        

        //  for loop counters
        for i <- 0 until fruits.length do 
            println(s"$i is ${fruits(i)}")

        // Out:
            // 0 is apple
            // 1 is banana
            // 2 is orange
        
        // to access sequence elements by their index
        for (fruit, index) <- fruits.zipWithIndex do
            println(s"$index is $fruit")
        
        println(fruits.zipWithIndex) //List((apple,0), (banana,1), (orange,2))

        // GENERATORS
        for i <- 1 to 3 do println(i)
        // The "1 to 3" portion of the loop creates a "range"
        // Using a rane like this is know as using a generator

        // Looping over a map (dictionary in Python)
        val names = Map(
            "firstname" -> "Robert",
            "lastname" -> "Goren",
        )
        
        for (k, v) <- names do println(s"key: $k, value: $v")

        // Collection methods like foreach
        fruits.foreach(println)

        fruits.foreach(e => println(e.toUpperCase))

        fruits.foreach{e =>
            val s = e.toUpperCase
            println(s)
        }
        // further reading:
            // https://oreil.ly/UFVQ2
            // https://oreil.ly/3anl9
            // https://oreil.ly/RZGNP

        

    }

    // Using For Loops with Multiple Counters
    def usingForLoopsWithMultipleCounters(): Unit = {
        // Problem: You want to create a loop with multiple counters, such as when iterating
        // over a multi-dimensional array

        val a = Array.ofDim[Int](2, 2)
        println(a(1)(1))
        for 
            i <- 0 to 1
            j <- 0 to 1
        do 
            println(s"($i)($j) = ${a(i)(j)}")   

        
        


    }
    // Using a For Loop With Embedded If Statements (Guards)
    def usingAForLoopWithEmbeddedIfStatements(): Unit = {
        // Problem: You want to add one or more conditional clauses to a for loop, 
        // typically to filter out some elements in a collection while working 
        // on the others

        for
            i <- 1 to 10
            if i % 2 == 0
        do 
            println(s"$i ")
        
        // Working with for loops and the java.io library
        import java.io.File
        val dir = File(".")
        val files: Array[java.io.File] = dir.listFiles()

        for 
            file <- files
            if file.isFile
            if file.getName.endsWith(".scala")
        do
            println(s"Scala file: $file")



        val b: List[Int] = List(1, 2, 3)
        val c = for  //for expression
            i <- 0 until b.length
            if i == 0 || i == 2
        yield 
            i*10
        
        println(c) // Vector(0, 20)

        val d: List[String] = List("A", "B", "C")
        val e = for
            i <- d
            if i.toLowerCase == "a" || i.toLowerCase == "c"
        yield 
            i + " found"
            
        println(e) // List(A found, C found)
    }
    // Creating a New Collection from an Existing Collection with for/yield
    def creatingANewCollectionFromAnExistingCollectionWithForYield(): Unit = {
        println("Creating a New Collection from an Existing Collection with for/yield")
        val names = List("chris", "ed", "maurice")
        println(names) //List(chris, ed, maurice)

        val capNames = for name <- names yield name.capitalize
        println(capNames) //List(Chris, Ed, Maurice)

        //  Using a for loop with a yield statement is known as a for-comprehension

        val lengths: List[Int] = for name <- names yield
            // imagine multiple lines of code
            name.length
        
        println(lengths) // List(5, 2, 7)

        // both parts of your for-comprehension (also known as a for-expression)
        // can be as complicated as necessary

        val xs = List(1, 2, 3)
        val ys = List(4, 5, 6)
        val zs = List(7, 8, 9)

        val a = for 
            x <- xs
            if x > 2
            y <- ys
            z <- zs
            if y * z < 45
        yield 
            val b = x + y 
            val c = b * z
            c
        
        println(a) // List(49, 56, 63, 56, 64, 63)

        // A for comprehension can even be the complete body of a method:
        def between3and10(xs: List[Int]): List[Int] =
            for 
                x <- xs
                if x >= 3
                if x <= 10
            yield x
        
        println(between3and10(List(1,3,5,9,11))) // List(3, 5, 9)

        // Example
        val namesUpper = for n <- names yield n.toUpperCase
        println(namesUpper) // List(CHRIS, ED, MAURICE)

        // Calling the map method on a collection does the same thing
        val namesUpperMap = names.map(_.toUpperCase)
        println(namesUpperMap) // List(CHRIS, ED, MAURICE)

        // 
        val namesCapMap = names.map(_.capitalize)
        println(namesCapMap) // List(Chris, Ed, Maurice) 

    }
    // Using the if Construct Like a Ternary Operator
    def usingTheIfConstructLikeATernaryOperator(): Unit = {
        println("Using the if Construct Like a Ternary Operator:")
        // Problem: You're familiar with Java's special "ternary operator" syntax:
            // int absValue = (a < 0) ? -a : a;
        // and you'd like to know what the scala equivalent is.


    }

}
