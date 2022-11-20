

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
        // usingTheIfConstructLikeATernaryOperator()
        // usingAMatchExpressionLikeASwitchStatement()
        // matchingMultipleConditionsWithOneCaseStatement()
        // assigningTheResultOfTheMatchExpressionToAVariable()
        // accessingTheValueOfTheDefaultCaseInAMatchExpression()
        // usingPatternMatchingInMatchExpressions()
        // usingEnumsAndCaseClassesInMatchExpressions()
        // addingIfExpressionsGuardsToCaseStatements()
        // usingAMatchExpressionInsteadOfisInatanceOf()
        // workingWithAListInAMatchExpression()
        // matchingOneOrMoreExceptionsWithTryCatch()
        // declaringAVariableBeforeUsingItInATryCatchFinallyBlock()
        creatingYourOwnControlStructures()


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

        // Trick problem: there isn't one, just use if/else/then expression:
        val a = 1
        val absValue = if a < 0 then -a else a
        println(absValue) // 1

        // because an if expression returns a value, you can embed it in a print statement
        println(if a < 0 then -a else a) // 1

        // you can also use it in another expression, such as this portion of a hashCode method:
        // hash = hash * prime + (if name == null then 0 else name.hashCode)

        // The fact that if/else expressions return a value also lets you write concise methods
        // Version 1: one-line style
        def abs1(x: Int) = if x >= 0 then x else -x
        def max1(a: Int, b: Int) = if a > b then a else b
        
        // Version  2: the method body on a separate line, if you prefer
        def abs2(x: Int) =
            if x >= 0 then x else -x
        
        def max2(a: Int, b: Int) = 
            if a > b then a else b
        
    }
    // Using a Match Expression Like a Switch Statement
    def usingAMatchExpressionLikeASwitchStatement(): Unit = {
        println("Using a Match Expression Like a Switch Statement")
        // Problem: You have a situation where you want to create something like a simple java 
        // integer-based 'switch' statement, such as matching the days in a week, the months
        // in a year, and other situations where an integer maps to a result

        // return a value from a match expression:
        import scala.annotation.switch

        // 'i' is an integer
        val i = 1
        val day = (i: @switch) match
            case 0 => "Sunday"
            case 1 => "Monday"
            case 2 => "Tuesday"
            case 3 => "Wednesday"
            case 4 => "Thursday"
            case 5 => "Friday"
            case 6 => "Saturday"
            case 7 => "invalid day"
        // When writing simple match expressions like this, its recommended to use the
        // @switch annotation, as shown. This annotation provides a warning at compile time 
        // if the switch statement cant be compiled to a 'tableswitch' or 'lookupswitch'. 
        // Compiling your match expressions to a 'tableswitch' or 'lookupswitch' is better
        // for performance because it results in a branch table rather than a decision tree.  
        // When a value is given to the expression, it can jump directly to the result rather
        // than working through the decision tree

        // Scala @switch docs: https://oreil.ly/rrNBP

        // tableswitch optimization example pg.100
            // import scala.annotation.switch

            // class SwitchDemo:
            //     val i = 1
            //     val one = 1
            //     val x = (i: @switch) match 
            //         case one => "One"
            //         case 2 => "Two"
            //         case 3 => "Three"
            //         case _ => "Other"

        // The following conditions must be true for Scala to apply the 'tableswitch' optimization
            // 1. The matched value must be a known integer
            // 2. The matched expression must be "simple". It cant contain any type checks, if statements
            //     or extractors
            // 3. The expression must have its value available at compile time
            // 4. There should be more than two 'case' statements
         
        //  if not concerned with the value of the default case:
            //  case _ => println("Got a default case")
        // If you are interested what fell down to the default case :
            // case default => println(default)
        // Note, name doesnt have to be default, can be any legal name
            //  case oops => println(oops)
        //  you can generate a MatchError if you don't handle the default case, 
            // so unless writing a partial function, handle the damn default case.
        
        // Note: you may not need a match expression for examples like these, for example
        // any time you are just mapping one value to another, it may be preferable to 
        // use a Map(Python dictionary, still getting used to that)

        val days = Map(
            0 -> "Sun", 
            1 -> "Mon",
            2 -> "Tue", 
            3 -> "Wed", 
            4 -> "Thu", 
            5 -> "Fri", 
            6 -> "Sat"
        )
        println(days.get(3)) // Some(Wed)
        println(days(3)) // Wed
        
        // JVM switches: https://oreil.ly/oUcwX
        // Diff between tableswitch and lookupswitch: https://oreil.ly/JvE3P
        // basically 
            // tableswitch: tableswitch uses a table with labels only. 
                    // (O(1) operation)
            // lookupswitch uses a table with keys and labels
                    //  (O(log n) operation)
            // hence we want to optimize by using tableswitch with our 
            // switch expressions where possible

    }
    // Matching Multiple Conditions With One Case Statement
    def matchingMultipleConditionsWithOneCaseStatement(): Unit = {
        println("Matching Multiple Conditions With One Case Statement")
        // Problem: You have a situation where several 'match' conditions 
        // require that the same business logic be executed, and rather than 
        // repeating your business logic for each case, you'd like to use 
        // one copy of the business logic for the matching conditions

        //  separate them with pipe '|'
        val i = 1
        i match
            case 1 | 3 | 5 | 7 | 9 => println("odd")
            case 2 | 4 | 6 | 8 | 10 => println("even")
            case _ => println("too big")
        
        // "odd"

        // same works for String matching
        val cmd = "stop"
        cmd match
            case "start" | "go" => println("starting...")
            case "stop" | "quit" | "exit" => println("stopping...")
            case _ => println("doing nothing")
        
        // "stopping..."

        // this example shows how to match multiple objects on each 'case' statement:
        
        enum Command:
            case Start, Go, Stop, Whoa

        import Command.*
        def executeCommand(cmd: Command): Unit = cmd match
            case Start | Go => println("start")
            case Stop | Whoa => println("stop")

        executeCommand(Start)

        // As demonstrated, the ability to define multiple possible matches for each case statement
        // can simplify your code

    }
    // Assigning the Result of a Match Expression to a Variable
    def assigningTheResultOfTheMatchExpressionToAVariable(): Unit = {
        println("Assigning the Result of a Match Expression to a Variable")
        // Problem: You want to return a value from a match expression and assign it 
        // to a variable, or use a match expression as the body of a method
        
        val someNumber = scala.util.Random.nextInt()
        val evenOrOdd = someNumber match
            case 1 | 3 | 5 | 7 | 9 => "odd"
            case 2 | 4 | 6 | 8 | 10 => "even"
            case _ => "other"

        println(s"Number $someNumber is ${evenOrOdd.toUpperCase}")

        def isTrue(a: Matchable): Boolean = a match 
            case false | 0 | "" => false
            case _ => true
        
        println(isTrue(42)) // true

        // You may hear that Scala is an "expression-oriented programming (EOP) language"
        // EOP means that every construct is an expression, yields a value, and doesn't have
        // a side effect. Unlike other languages, in Scala every construct like if, match, for
        // and try returns a value.

    }
    // Accessing the Value of the Default Case in a Match Expression
    def accessingTheValueOfTheDefaultCaseInAMatchExpression(): Unit = {
        println("Accessing the Value of the Default Case in a Match Expression")
        // Problem: You want to access the value of the default "catch all" case when using a 
        // match expression , but you can't access the value when you match it with the
        // _ wildcard syntax

        // solution: use a name like default in place of the wildcard
        val i = 2
        val newMatch: String = i match
            case 1 => "one"
            case default => "default"
        
        println(newMatch)

        i match
            case 0 => println("1")
            case 1 => println("2")
            case default => println(s"You gave me: ${default}")

    }
    // Using Pattern Matching in Match Expressions
    def usingPatternMatchingInMatchExpressions(): Unit = {
        println("Using Pattern Matching In Match Expressions")
        // Problem: You need to match one or more patterns in a match expression,
        // and the pattern may be a constant pattern, variable pattern, constructor pattern,
        // sequence pattern, tuple pattern, or type pattern. 

        // many different types of patterns you can use in a match expressions:
        def test(x: Matchable): String = x match
            // Constant Patterns
            case 0 => "zero"
            case true => "true"
            case "hello" => "you said 'hello'"
            case Nil => "an empty list"

            // Sequence Patterns
            case List(0, _, _) => "a 3-element list with 0 as the first element"
            case List(1, _*) => "list, starts with 1, has any number of elements"
            case list @ List(2, _*) => s"list, starts with 1. This is the whole list: ${list}"

            // Tuple Patterns
            case (a, b) => s"got ${a} and ${b}"
            case (a, b, c) => s"got ${a}, ${b}, and ${c}"

            // Constructor Patterns
            //case Person(first, "Alexander") => s"Alexander, first name = ${first}"
            //case Dog("Zeus") => s"found a dog named Zeus"

            // Typed Patterns
            case s: String => s"got a string: ${s}"
            case i: Int => s"got an int: ${i}"
            case f: Float => s"got a float: ${f}"
            case a: Array[Int] => s"array of int: ${a.mkString(",")}"
            case as: Array[String] => s"array of strings: ${as.mkString(",")}"
            //case d: Dog => s"dog: ${d.name}"
            case list: List[_] => s"got a list: ${list}"
            case m: Map[_, _] => m.toString
            
            // The Default Wildcard Pattern
            case _ => "Unknown"
        
        end test

        // pg. 106
        // Note that the List and Map expressions could have been written like this:
            // case m: Map[A, B] => m.toString
            // case list: List[X] => s"thanks for the list: ${list}"
        
        // /////////////////////////////////////////////////////////////////////////
        // Patterns

        // Constant Patterns
        // A constant pattern can only match itself. Any literal may be used as a constant.
        // If you specify a 0 as the literal, only an Int value of 0 will be matched
        // Examples include:
        // case 0 => "zero"
        // case true => "true"

        // Variable Patterns
        // This was not shown in the large match example in the Solution ^^^ , 
        // but a variable pattern matches any object, just like the _ wildcard character.  
        // Scala binds the variable to whatever the object is, which lets you use the variable 
        // on the right side of the case statement. for example, at the end of a match 
        // expression you can use the _ wildcard character like this to catch anything else.
        
        // case _ => s"Hmm, you gave me something..."
        // but with a variable pattern you can write this instead...
        // case foo => s"Hmm, you gave me a ${foo}"

        // Constructor Pattern
        // The constructor pattern lets you match a constructor in a case statement. 
        // As shown in the examples, you can specify constants or variable patterns 
        // as needed in the constructor pattern:
        // 
        // case Person(first, "Alexander") => s"found an Alexander, first name = $first"
        // case Dog("Zeus") => s"found a dog named Zeus"

        // Sequence Patterns
        // You can match against sequences like List, Array, Vector, etc. 
        // Use the _ character to stand for one element in the sequence, and use _* to 
        // stand for zero or more elements
            //  case List(0, _, _) => "a 3-element list with 0 as the first element"
            // case List(1, _*) => "list, starts with 1, has any number of elements"
        
        // Tuple patterns
        // As shown in the examples, you can match tuple patterns and access the value 
        // of each element in the tuple: You can use the _ wildcard if you're not interested 
        // in the value of an element:
            // case (a, b) => s"got ${a} and ${b}"
            // case (a, b, c, _) => s"4 element tuple: got ${a}, ${b}, and ${c}"
        
        // Typed Patterns
        // In the following example, str: String is a typed pattern, and str is a pattern variable:
            // case str: String => s"you gave me this string: ${str}"
        // As shown in the examples, you can acces the pattern variable on the right side of the expression
        // after declaring it.
  
        // Variable-binding patterns
        // At times you may want to add a variable to a pattern. You can do this with the following general syntax:
            // case variableName @ pattern => ...
        // This is called a 'variable-binding' pattern. When it's used, the input variable to the match expression
        // is compared to the pattern, and if it matches, the input variable is bound to 'variableName'.

        // The usefulness of this is best shown by demonstrating the problem it solves. 
        // Suppose you had the List pattern that was shown earlier:
            // case List(1, _*) => "list, starts with 1, has any number of elements"
        // This works, but we can't use the match pattern on the right side of the pattern with this approach,
        // to allow us to use the match pattern as a variable do this:
            // case list @ List(1, _*) => "list, starts with 1. This is the whole list: ${list}"

        // The following code demonstrates this ^^^ example and the usefulness of this approach:
        case class Person(firstName: String, lastName: String)

        def matchType(x: Matchable): String = x match
            //case x: List(1, _*) = > s"${x}"  // doesn't compile
            case x @ List(1, _*) => s"${x}" // prints the list

            // case Some(_) => "got a Some"  // works, but we cant access the variable
            // case Some(x) => s"${x}" // returns "foo"
            case x @ Some(_) => s"${x}" // returns "Some(foo)"

            case p @ Person(first, "Doe") => s"${p}" //returns "Person(John, Doe)"

            case default => s"You only get what you give: ${default}"
        end matchType

        println(matchType(List(1,2,3))) //List(1, 2, 3)
        println(matchType(Some("foo"))) //Some(foo)

        val john = Person("John", "Doe")
        val jane = Person("Jane", "Doe")
        val otherGuy = Person("Other", "Guy")

        println(matchType(john)) //Person(John,Doe)
        println(matchType(jane)) //Person(Jane,Doe)
        println(matchType(otherGuy)) //doesn't match our Person Search for "Doe" -> defaults
        // very cool

        // Using "Some" and "None"/////////////////////////////////////////////////////
        // To round out these examples, you'll often use Some and None with match expressions. 
        // For instance, when you attempt to create a number from a string with a method like
        // toIntOption, you can handle the result in a match expression:
        val s = "42"

        // later in the code
        s.toIntOption match 
            case Some(i) => println(i)
            case None => println("That was not an integer")
        
        // Further Reading: Type Erasure:
            // http://bit.ly/15odxST
            // http://bit.ly/139WrFj

    }
    // Using Enums and Case Classes in Match Expressions
    def usingEnumsAndCaseClassesInMatchExpressions(): Unit = {
        println("Using Enums and Case Classes in Match Expressions")
        // Problem: You want to match enums, case classes, or case objects in a match expression.

        // The following example demonstrates how to usw patterns to match enums in different ways,
        // depending on what information you need on the right side of each case statement. First, 
        // here's an enum named Animal that has three instances- Dog, Cat, and Woodpecker:
        enum Animal:
            case Dog(name: String)
            case Cat(name: String)
            case Woodpecker
        
        // vvv Note below is how this was done in Scala 2.

        // sealed trait Animal
        // case class Dog(name: String) extends Animal
        // case class Cat(name: String) extends Animal
        // case class Woodpecker extends Animal


        // Given that enum, this getInfo method shows the different ways you can match the enum
        // types in a match expression.
        import Animal.*

        def getInfo(a: Animal): String = a match
            case Dog(moniker) => s"Got a Dog, name = ${moniker}"
            case _: Cat       => s"Got a Cat (ignoring the name)"
            case Woodpecker   => s"That was a Woodpecker"
        
        println(getInfo(Dog("Fido")))
        println(getInfo(Cat("Maurice")))
        println(getInfo(Woodpecker))

        val violet = Dog("Violet")
        println(getInfo(violet))
    }
    // Adding If Expressions (Guards) to Case Statements
    def addingIfExpressionsGuardsToCaseStatements(): Unit = {
        println("Adding If Expressions (Guards) to Case Statements")
        // Problem: You want to add qualifying logic to a case statement in a match expression, 
        // such as allowing a range of numbers or matching a pattern, but only if that 
        // pattern matches some additional criteria
        
        // Add an if guard to your case statement. Use it to match a range of numbers
        def addIfGuard(i: Matchable) =
            i match
                case a if 0 to 9 contains a   => println("0-9 range: " + a)
                case b if 10 to 19 contains b => println("10-19 range: " + b)
                case c if 20 to 29 contains c => println("20-29 range: " + c)
                case _                        => println("Hmmm...")
        end addIfGuard

        addIfGuard(25)

        // Use it to match different values of an object:
        def matchObject(x: Matchable) =
            x match
                case x if x == 1             => println("one, a lonely number")
                case x if (x == 2 || x == 3) => println(x)
                case _                       => println("some other value")

        matchObject(1)

        // As long as your class has an unapply method, you can reference 
        // class fields in your if guards. For instance, becuase a case class 
        // has an automatically generated unapply method, 
        // given this Stock class and instance:
        case class Stock(symbol: String, price: BigDecimal)
        val stock = Stock("AAPL", BigDecimal(132.50))


        // stock match
            // case s if s.symbol == "AAPL" && s.price < 140 => buy(s)
            // case s if s.symbol == "AAPL" && s.price > 160 => sell(s)
            // case _ => //do nothing
        
        // You can also extract fields from case classes0 and classes that have 
        // properly implemented unapply methods- and use those in your guard conditions. 
        // For example, the case statements in this match expression


        // //////////////////////////////////////////////////////////////////
        // This next example deconstructs a case class so that you can use 
        // the class's attributes in the match expression business logic
        
        // extract the 'name' in the 'case' and then use that value
        def speak(p: Person): Unit = p match
            case Person(name) if name == "Fred"    => println("Yabba Dabba Do")
            case Person(name) if name == "Bam Bam" => println("Bam Bam!")
            case _                                 => println("Watch the Flintstones")
        // will work if Person is defined as a case class
        case class CaseClassPerson(aName: String)

        // or as a class with a properly implemented unapply method
        class Person(val aName: String)
        object Person:
            // 'unapply' deconstructs a Person. It's also known as an
            // extractor, and Person is an "extractor object"
            def unapply(p: Person): Option[String] = Some(p.aName)
        
        // not this example is a little contrived because Scala allows you to 
        // write the cases like this:
        def speak2(p: Person): Unit = p match
            case Person("Fred") =>  println("Yabba Dabba Do")
            case Person("Bam Bam") => println("Bam Bam!")
            case _ => println("Watch the Flintstones")

        // I wrote these match expressions in a couple different ways to show how they can be written
        val fred = Person("Fred")
        
        speak(fred) // Yabba Dabba Do

    }
    // Using a Match Expression Instead of isInstanceOf
    def usingAMatchExpressionInsteadOfisInatanceOf(): Unit = {
        println("Using a Match Expression Instead of isInstanceOf")
        // Problem: You want to write a block of code to match one type, or multiple different types
        case class Person(name: String)
        val person = Person("John")

        // can use isInstanceOf
        val isPerson1 = if person.isInstanceOf[Person] then true else false
        println(isPerson1)

        // can also use  a match expression
        def isPerson(m: Matchable): Boolean = m match
            case p: Person => true
            case _ => false
        end isPerson

        println(isPerson(person)) // true1

        ///////////////////////////////////////////////////////////////////////////////////////
        // A more common scenario is that you'll have a model like this
        enum Shape:
            case Circle(x0: Double, y0: Double, radius: Double)
            case Square(x0: Double, y0: Double, length: Double)
        
        import Shape.*  // Note: we dont have to import in this case, but likely you will

        def area(s: Shape): Double = s match 
            case Circle(_, _, r) => Math.PI * r * r 
            case Square(_, _, l) => l * l
        
        // examples
        println(area(Square(0, 0, 2.0))) //4.0
        println(area(Circle(0, 0, 2.0))) //12.566370614359172

        // Side note Practice
        def typeCheck(x: Matchable) = x match
            case s: String => println("string")
            case i: Int => println("integer")
            case b: Boolean => println("boolean")
        
        typeCheck(1) //integer
        typeCheck(true) //boolean
        typeCheck("Hello World!") //string

        // //////////////////////////////////////////////////////////////////
    }
    // Working with a list in a Match Expression
    def workingWithAListInAMatchExpression(): Unit = {
        println("Working with a list in a Match Expression")
        // Problem: You know that a List data structure is a little different 
        // than other sequential data structures: it's built from 'cons' cells 
        // and ends in a Nil element. You want to use this to your advantage when 
        // working with a match expression, such as when writing a recursive function.

        // You can create a list like this
        val xs = List(1, 2, 3)
        // or like this
        val ys = 1 :: 2 :: 3 :: Nil

        val ls = List("one", "two", "three")

        // SCALA "Lists" are Linked-Lists under the hood. 
        // This class is optimal for last-in-first-out (LIFO), 
        // stack-like access patterns. If you need another access pattern, 
        // for example, random access or FIFO, consider using a collection 
        // more suited to this than List.

        // As shown in in the second example, a List ends with a Nil element, 
        // and you can take advantage of that when writing match expressions to work on lists, 
        // especially when writing recursive algorithms

        def listToString(list: List[String]): String = list match
            case s :: rest => s + " " + listToString(rest)
            case Nil => ""
        
        println(listToString(ls)) //one two three 

        // A match and recursion sum method for List[Int]
        def sum(list: List[Int]): Int = list match
            case Nil => 0
            case n :: rest => n + sum(rest)
        
        println(sum(xs)) //6

        // Similarly, this is a product algorithm
        def product(list: List[Int]): Int = list match
            case Nil => 0
            case n :: rest => n * sum(rest)
        
        println(product(xs)) //6

        // While recurion is great don't forget Scala's reduce and fold functions
        // long form
        def sumReduceLong(list: List[Int]): Int = list.reduce((x, y) => x + y)
        // short form
        def sumReduceShort(list: List[Int]): Int = list.reduce(_ + _)
        println(sumReduceShort(xs)) // 6
        println(sumReduceLong(xs)) // 6

    }
    // Matching One or More Exceptions with try/catch
    def matchingOneOrMoreExceptionsWithTryCatch(): Unit = {
        println("Matching One or More Exceptions with try/catch")
        // Problem: You want to catch one or more exceptions in a try/catch block

        // When you need to catch and handle multiple exceptions, 
        // just add the exception types as different case statements
        // def tryCatchExample() =
        //     try 
        //         openAndReadFile(filename)
        //     catch 
        //         case e: FileNotFoundException => 
        //             println(s"Couldn't find ${filename}")
        //         case e: IOException => 
        //             println(s"Han an IOException trying to read ${filename}")
            

        // If you don't care about any specific exception, 
        //      but still want to use them, such as log them:
        
        // try 
        //     openAndReadFile()
        // catch 
        //     case t: Throwable => logger.log(t)

        //If you even don't care about the value of the exception:
        // try 
        //     openAndReadFile()
        // catch 
        //     case _: Throwable => println("Nothing to worry about, just an exception")
    
        // Methods based on try/catch
        import scala.io.Source
        import java.io.{FileNotFoundException, IOException}

        def readFile(filename: String): Option[String] =
            try
                Some(Source.fromFile(filename).getLines.mkString)
            catch
                case _: (FileNotFoundException | IOException) => None
        
        // ^^ this shows one way to return a value with a try/catch block

        // The "Scala" way is to never throw an exception
        // Instead, you should use Option, or use Try/Success/Failure 
            // if you don't want to return None

        import scala.io.Source
        import java.io.{FileNotFoundException, IOException}
        import scala.util.{Try, Success, Failure}

        def readFile2(filename: String): Try[String] =
            try
                Success(Source.fromFile(filename).getLines.mkString)
            catch
                case t: Throwable => Failure(t)
        
        
        // Whenever an exception message is involved, 
        // I always prefer using Try or Either instead of Option,
        // because they give you access to the message in Failure
        // or Left, when Option only returns None.

        // 
        import scala.util.control.Exception.allCatch
        // OPTION
        allCatch.opt("42".toInt) // Option[Int] = Some(42)
        allCatch.opt("foo".toInt) // Option[Int] = None

        // TRY
        allCatch.toTry("42".toInt) // Matchable = 42
        allCatch.toTry("foo".toInt) // Matchable = Failure(NumberFormatException: For input string: "foo")
        
        // EITHER
        allCatch.either("42".toInt) // Either[Throwable, Int] = Right(42)
        allCatch.either("foo".toInt) // Either[Throwable, Int] = Left(NumberFormatException: For input string: "foo")

    }
    // Declaring a Variable Before Using It in a try/catch/finally Block
    def declaringAVariableBeforeUsingItInATryCatchFinallyBlock(): Unit = {
        println("// Declaring a Variable Before Using It in a try/catch/finally Block")
        // Problem: You want to use an object in a try block and need to access it 
        // in the 'finally' portion of the block, such as when you need to call a 
        // 'close' method on an object

        import scala.io.Source
        import java.io.*

        var sourceOption: Option[Source] = None

        try
            sourceOption = Some(Source.fromFile("/etc/passwd"))
            sourceOption.foreach { source =>
                // do whatever you need to do with the 'source' here
                for line <- source.getLines do println(line.toUpperCase)
            }
        catch
            case ioe: IOException => ioe.printStackTrace
            case fnf: FileNotFoundException => fnf.printStackTrace
        finally
            sourceOption match
                case none => 
                    println("bufferedSource == None")
                case Some(s) => 
                    println("closing the bufferedSource...")
                    s.close
        
        // This example is contrived, 16.1 "Reading Text Files" is better
        // But this shows the approach

        var in: Option[FileInputStream] = None
        var out: Option[FileOutputStream] = None
        // Dont use Null





    }
    // Creating Your Own Control Structures
    def creatingYourOwnControlStructures(): Unit ={
        println("Creating Your Own Control Structures")
        // Problem: You want to define your own control structures to customize the Scala language,
        // simplify your code, or create a domain-specific language (DSL)

        // Thanks to features like 'multiple parameter lists', 'by-name parameters', 'extension methods',
        // 'higher-order functions', and more, you can create your own code that works just like 
        // a control structure

        import scala.annotation.tailrec
        object WhileTrue:
            @tailrec
            def whileTrue(testCondition: => Boolean)(codeBlock: => Unit): Unit =  // uses 1) by-name parameters(call-by-name)
                if (testCondition) then                                                 //2) multiple parameter lists ()()
                    codeBlock
                    whileTrue(testCondition)(codeBlock)
                end if 
            end whileTrue
        
        
        import WhileTrue.whileTrue
        var i = 0
        whileTrue (i < 5) {
            println(i)
            i += 1
        }

        // Further Reading: https://oreil.ly/KOAHI
        // "How to use By-name parameters in scala: https://oreil.ly/fuWGM"
        // "Scala call-by-name parameters" : https://oreil.ly/shdre
        // "The using control structure in Beginning Scala" : https://oreil.ly/fiLHH
        // "Scala: How to use break and continue in for and while loops": https://oreil.ly/KOAHI
        // Breaks class source code: https://oreil.ly/xI78S

        // Another Example
        // create a control structure whose expression looks like this, a double-if statement

        // doubleIf(age > 18)(numAccidents == 0){ println("Discount!") }

        def doubleIf(testCondition1: => Boolean)(testCondition2: => Boolean)(codeBlock: => Unit) = 
            if (testCondition1) && (testCondition2) then
                codeBlock
            end if
        end doubleIf

        val age = 20
        val numAccidents = 0
        doubleIf(age > 18)(numAccidents == 0){ println("Discount!") } // Discount!

        def doubleIfshorter(testCondition1: => Boolean)(testCondition2: => Boolean)(codeBlock: => Unit) =
            if testCondition1 && testCondition2 then codeBlock
        

    }



}
