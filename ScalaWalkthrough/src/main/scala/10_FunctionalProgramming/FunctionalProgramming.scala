object FunctionalProgramming extends App {
    def main(): Unit = {
        println("FUNCTIONAL PROGRAMMING")
        // introduction()
        // fPIsASupersetOfExpressionOrientedProgrammingEOP()
        // usingFunctionLiterals()
        // passingFunctionsAroundAsVariables()
        // definingAMethodThatAcceptsASimpleFunctionParameter()
        // declaringMoreComplexHigherOrderFunctions()
        // usingPartiallyAppliedFunctions()
        // creatingAMethodThatReturnsAFunction()
        // creatingPartialFunctions()
        // implementingFunctionalErrorHandling()
        passingFunctionsAroundInAnAlgorithm()

    }
    main()
    def introduction(): Unit = {
        // Functional Programming is a way of writing software applications using
        // only pure functions and immutable values

        // In essence "the absence of side effects"

        // Pure functions are functions:
            // whose algorithm and output depend only on (a) the function's input
            // parameters and (b) calling other pure functions

            // thats doesn't mutate the parameters it's given

            // that doesn't mutate anything anywhere else in the application

            // That doesn't interact with the outside world, such as interacting 
            // with files, databases, networks, or users

            // Their internal algorithm doesn't call other functions whose responses 
            // vary over time, date, and random number (random anything) functions

            // When called any number of times with the same input, a pure function
            // always returns the same value.  

        // Examples of 'impure' functions:
            // any sort of input/output (I/O) function (including input from a user,
            // output to the user, and reading from and writing to files, databases 
            // and networks)

            // Functions that return differentresults at different times (date, time,
            // and random functions)

            // A function that modifies mutable state (such as a mutable field in a class)

            // A function that recieves a mutable type like array or arrayBuffer,
            // and modifies its elements


        // A pure function gives you comfort that when you call it with a given set
        // of input, you'll always get the exact same answer back, such as:
        "zeus".length
        // sum(2,2)
        List(4,5,6).max

        // SIDE EFFECTS
        // A function that has a side effect modifies state, mutates variables, and/or
        // interacts with the outside world. This includes:
            // Writing (or reading) data to (from) a database or file or web service

            // Mutating the state of a variable that was given as input, changing data 
            // in a data structure, or modifying the value of a mutable field in an
            // object

            // Throwing an exception, or stopping the application when an error occurs

            // Calling other functions that have side effects

        //THINKING IN FP
        // You need to think of pure functions as algebraic functions

        // algebra:
            //  f(x) = x^2 + 2x = 1
        
        // in Scala:
        def f(x: Int): Int = x*x + 2*x + 1

        // Notice a few things about this function:
        // -The function results depends only on the value of x and the function's 
        // alorithm
        // -The function only relies on the * and + operators, which can be though of 
        // as calling other pure functions.
        // The function doesn't mutate x

        // furthermore the function doesn't mutate anything else in the outside world

        // FP is about writing all of your functions like this, and then combining them 
        // together to create a complete application.

        // REFERENTIAL TRANSPARENCY AND SUBSTITUTION
        // Referential transparency is the property that an expression can be replaced by
        // its resulting value without changing the behavior of the program (or vice versa)

        // a = b + c
        // d = e + f + b
        //  x = a + d

        // x = a + d
        // x = (b + c) + (e + f + b)
        // x = b + c + e + f + b
        // x = 2b + c + e + f

        // can do the same thing in Scala
        // val a = f(x)
        // val b = g(a)
        // val c = h(y)
        // val d = i(b, c)

        // // not that 
        // val b = g(a)

        // // is exactly the same thing as 
        // val b = g(f(x))

        // val d = i(g(f(x)), h(y))

    }
    // FP is a Superset of Expression-Oriented Programming (EOP)
    def fPIsASupersetOfExpressionOrientedProgrammingEOP(): Unit = {
        // A feature that makes Scala such a great FP language is that all
        // of your code can be written as expressions, including 'if' expressions

        // IF EXPRESSIONS:
        val a = 1
        val b = 2
        val max = if a > b then a else b
        println(max) //2

        // MATCH EXPRESSIONS:
        val i = 3
        val evenOrOdd: String = i match
            case 1| 3 | 5 | 7 | 9  => "odd"
            case 2| 4 | 6 | 8 | 10 => "even"
        println(evenOrOdd) //odd

        // FOR EXPRESSIONS:
        val xs = List(1,2,3,4,5)
        val ys = for
            x <- xs
            if x > 2
        yield
            x * 10
        println(ys) //List(30, 40, 50)
        println(xs) //List(1, 2, 3, 4, 5) -> xs has not mutated 

        // TRY/CATCH BLOCKS return values
        def makeInt(s: String): Int =
            try
                s.toInt
            catch
                case _ : Throwable => 0
            
        println(makeInt("5")) //5
        println(makeInt("Hello World")) //0




    }
    // Rules For Functional Programming in Scala
    def rulesForFunctionalProgrammingInScala(): Unit = {
        // Rules for a FP mindset:
        // 1. Never use null values. Forget that Scala even has a 'null' keyword
        // 2. Write only pure functions
        // 3. Use only immutable values(val) for all fields
        // 4. Every line of code must be an algebraic expression. Whenever you use
        //  an if, you must also use an else
        // 5. Pure functions should never throw exceptions; instead, they yield
        //  values like Option, Try, Either
        // 6. Don't create OOP "classes" that encapsulate data and behavior. Instead,
        //  create immutable data structures using case classes, and then write pure
        //  functions that operate on those data structures

        // You'll see that only using immutable fields naturally leads to recursive 
        // algorithms. Then you'll see that you won't need recursion that often
        // because of all the functional methods that are built into the immutable Scala
        // collections classes.


        
    }
    // IO With Functional Programming
    def iOwithFunctionalProgramming(): Unit = {
        // No application would be useful without I/O,
        // so Scala/FP has facilities for working with I/O
        // in a functional manner.

        // For example, Scala code that handles command line I/O in
        // a functional manner tends to look like this:
        // def mainloop: IO[Unit] =
        //     for
        //         _   <- putStr(prompt)   //putStr is a FP replacement for println
        //         cmd <- getLine.map(Command.parse _) //getLine is a FP replacement 
        //         _   <- if cmd == Quit then              // for reading user input
        //                     IO.Unit
        //                 else
        //                     processCommand(cmd) >> mainLoop
        //     yield
        //         ()
        // mainLoop.unsafeRunSync()

    }
    // Using Function Literals (Anonymous Functions)
    def usingFunctionLiterals(): Unit = {
        // Problem: You want to use an anonymous function - also known as a 
        // function literal- so you can pass it into a method that takes a function
        // or assign it to a variable

        // Given this list:
        val x = List.range(1, 10)
        // you can pass an anonymous function to the list's filter method to create a new list
        // that contains only even numbers:
        val evens = x.filter((i: Int) => i % 2 == 0)

        // The following code is a "function literal"
        //  (i: Int) => i % 2 == 0
        
        // And when it's passed into a method like this it's also known as an anonymous function
        // what some programming languages refer to as a 'lambda'

        // Thanks to several Scala shortcuts, that code can be expressed:
        val evens2 = x.filter(_ % 2 == 0)
        println(evens2) //List(2, 4, 6, 8)


        // Side note
        val isOdd = (i: Int) => i % 2 != 0
        val odds = x.filter(isOdd)
        println(odds) //List(1, 3, 5, 7, 9)

        // It helps to think of the "=>" symbol as a transformer, because the 
        // expression transforms the parameter list on the left side of the 
        // symbol into a new result using the algorithm on the right side of the
        // symbol.
        // (i: Int) => i % 2 == 0

        // ANONYMOUS FUNCTIONS WITH MULTIPLE PARAMETERS
        val map = Map(1 -> 10, 2 -> 20, 3 -> 30)
        // This example shows the syntax for using an anonymous function with the transform
        // method on an immutable Map instance, where the key and value from each element
        // is passed to the anonymous function
        val newMap = map.transform((k,v) => k + v)
        println(newMap) //Map(1 -> 11, 2 -> 22, 3 -> 33)

        // The anonymous function
        // (k,v) => k + v

        // See also: https://oreil.ly/nP3ZU



    }
    // Passing Functions Around as Variables
    def passingFunctionsAroundAsVariables(): Unit = {
        // Problem: You want to create a function as a variable and pass it around, 
        // just like you pass String, Int, and other variables around in an object 
        // oriented programming language.

        // assign a function literal to a variable
        val double = (i: Int) => i * 2

        // At this point the double variable is a variable instance, just like
        // an instance of a String, Int, or other type, but in this case it's an
        // instance of a function, known as a function value. You can now invoke double
        // just like calling a method

        println(double(2)) //4
        println(double(33)) //66

        // you can also pass it to any method that takes a function parameter
        // -map, filter, transform, etc. that matches it's signature.
        val list = List.range(1,5)

        val newList = list.map(double)
        println(newList) //List(2, 4, 6, 8)

        val length = (s: String) => s.length
        val list2 = List("this", "is", "a", "list")

        val listLengths = list2.map(length)
        println(listLengths) //List(4, 2, 1, 4)

        // You can write function literals in two ways:
        val funcOne = (i: Int) => i * 2

        val funcTwo = (i: Int) => { i * 2 }

        // if your anonymous function requires more than one line
        val funcThree = (i: Int) => { 
            val x = i * 2
            x + 2  // note you don't need a return statement, since Scala is expressive,
        }           // it's just itchin' to return something, no need to ask

        // The Scala compiler can infer the return type, but you can define it like this:
        val isEven1: (Int) => Boolean = i => { i % 2 == 0 }

        val isEven2: Int => Boolean = i => { i % 2 == 0 }

        val isEven3: Int => Boolean = i => i % 2 == 0 

        val isEven4: Int => Boolean = _ % 2 == 0 

        // These functions all take two Int parameters and return a single Int value,
        // which is the sum of the two input values:
        
        // explicit approach
        val add1: (Int, Int) => Int = (a, b) => a + b
        val add2: (Int, Int) => Int = (a, b) => { a + b }
        // val functionName: (inputType1, inputType2) => outputType = (input1, input2) => output
    
        // implicit approach
        val add3 = (x: Int, y: Int) => { x + y }
        val add4 = (x: Int, y: Int) =>  x + y 


        // Multiline function without parentheses
        val addThenDouble: (Int, Int) => Int = (x, y) =>
            val a = x + y 
            a * 2
        
        println(addThenDouble(2, 3)) //10

        // Using a def method like a val function

        // You can also define a method using def and then pass it around as an instance variable.
        // Again using a modulus algorithm, you can define a def method in any of these ways:
        
        def isEvenMethod1(i: Int) = i % 2 == 0

        def isEvenMethod2(i: Int) = { i % 2 == 0 }

        def isEvenMethod3(i: Int): Boolean = i % 2 == 0

        def isEvenMethod4(i: Int): Boolean = { i % 2 == 0 }

        // Eta expansion

        val list1 = List.range(1, 10)
        val filteredList = list1.filter(isEvenMethod1)
        println(filteredList) //List(2, 4, 6, 8)

        // Both functions and methods can be passed as arguments to functions like this
        // Higher-Order Functions

        // ASSIGNING AN EXISTING FUNCTION/METHOD TO A FUNCTION VARIABLE
        val c = scala.math.cos
        println(c(0)) //1.0

        // how to create a square function from the pow method in scala.math
        val square = scala.math.pow(_, 2)
        println(square(2)) //4.0

        // That example shows how to you can create a more specific function
        // square from a more generic function pow

        // STORING FUNCTIONS IN A MAP
        // functions can be used like Int or String in all ways
        // They can be used as function parameters
        // And just like Int or String, you can store function in a Map

        def add(i: Int, j: Int) = i + j
        def multiply(i: Int, j: Int) = i * j

        // store functions in a Map
        val functions = Map(
            "add" -> add, 
            "multiply" -> multiply
        )

        // get a function out of the Map and use it
        val f = functions("add")
        println(f(2,3)) //5

        // Functions and methods truly are variables, in every sense
        // See Also: https://oreil.ly/7v4ch

    }
    // Defining A Method That Accepts A Simple Function Parameter
    def definingAMethodThatAcceptsASimpleFunctionParameter(): Unit = {
        // function that takes a function as a parameter

        def executeFunction(callback:() => Unit) =
            callback()

        // Next, define a function or method that matches this signature

        val sayHelloF = () => println("Hello, Function")  //Function
        def sayHelloM(): Unit = println("Hello, Method")  //Method

        executeFunction(sayHelloF) //Hello, Function
        executeFunction(sayHelloM) //Hello, Method

        // example2
        def runAFunction(f:() => Unit) = f()
        runAFunction(sayHelloF)

        // To define a function that takes a string and returns an Int:
        def stringFunc1(f: String => Int) = ???
        def stringFunc2(f: (String) => Int) = ???





    }
    // Declaring More Complex Higher-Order Functions
    def declaringMoreComplexHigherOrderFunctions(): Unit = {
        // Problem: You want to define a method that takes a function as a parameter, 
        // and that function may have one or more input parameters and may return a 
        // value other than Unit. Your method may also have additional parameters.

        def exec(callback: Int => Unit) =
            callback(1)
        

        val plusOneF = (i: Int) => println(i + 1)
        def plusOneM(i: Int) = println(i + 1)

        exec(plusOneF) //2
        exec(plusOneM) //2

        val plus10 = (i: Int) => println(i + 10)
        def plus10M(i: Int): Unit = println(i + 10)

        exec(plus10) // 11
        exec(plus10M) //11

        def exec4(f: (String) => Int) = ???
        def exec5(f: String => Int) = ???

        // signature for function with 2 Int params that return a boolean
        def exec2(f: (Int, Int) => Boolean) = ???


        def exec3(f: (String, Int, Double) => Seq[String]) = ???
        
        // Passing in a function with other parameters
        def executeXTimes(callback:() => Unit, numTimes: Int): Unit = 
            for i <- 1 to numTimes do callback()

        def printSome(): Unit = println("Some")

        val printSomeF = () => println("Some")

        executeXTimes(printSome, 5)
        executeXTimes(printSomeF, 5)

        // Some
        // Some
        // Some
        // Some
        // Some

        // This demonstrates that you can use this technique to pass variables
        //  into a method can then be used by the function inside the method body.

        // example
        def executeAndPrint(f:(Int, Int) => Int, x: Int, y: Int): Unit =
            val result = f(x, y)
            println(result)
        
        def add6(x: Int, y: Int): Int = x + y
        val add6F = (x: Int, y: Int) =>  x + y

        def multiply6(x: Int, y: Int): Int = x * y
        val multiply6F = (x: Int, y: Int) =>  x * y

        // with add function
        executeAndPrint(add6, 2, 3) //5
        executeAndPrint(add6F, 2, 3) //5
        // with multiply function
        executeAndPrint(multiply6, 2, 3) //6
        executeAndPrint(multiply6F, 2, 3) //6

        // Here's another example of this 3step process
        // 1- define the method
        def executeAll(callback:(Any, Any) => Unit, x: Any, y: Any): Unit =
            callback(x, y)

        // 2- define a function to pass in
        def printTwoThings(a: Any, b: Any): Unit =
            println(a)
            println(b)
        
        // 3- pass the function and some params to the method
        executeAll(printTwoThings, "Hello", "World!")

        // Hello
        // World!

        case class Person(name: String)
        executeAll(printTwoThings, "Hello", Person("Dave"))
        // Hello
        // Person(Dave)


    }
    // Using Partially Applied Functions
    def usingPartiallyAppliedFunctions(): Unit = {
        // Problem: You want to eliminate repetitively passing variables into a function by
        // (a) passing common variables into the function to (b) create a new function that is 
        // preloaded with those values, and then (c) use the new function, passing it only the 
        // unique variable it needs.

        val sum = (a: Int, b: Int, c: Int) => a + b + c

        val addTo3 = sum(1, 2, _) //val sum: (Int, Int, Int) => Int = Lambda$1326/0x00000008014a43e0@595ec862
        // since you haven't applied a variable for the third parameter,
        // this is called a partially-applied function
        println(sum)

        println(addTo3(10)) //13

        // The addTo3 function can also be defined as a method
        def sum2(a: Int, b: Int, c: Int): Int = a + b + c
        val addToThree = sum2(1, 5, _)
        println(addToThree(2)) //8

        // This partially-applied function can be passed around like any other type
        def summation(a: Int, b: Int, c: Int): Int = a + b + c
        val addTo3_ = summation(1, 2, _)

        def intoTheWormhole(f: Int => Int) = throughTheWormhole(f)
        def throughTheWormhole(f: Int => Int) = otherSideOfWormhole(f)

        // supply 10 to whatever function you receive:
        def otherSideOfWormhole(f: Int => Int) = f(10)

        println(intoTheWormhole(addTo3_)) //13

        // Function variables are also called function values

        // Real-World use:
            // One use of this technique is to create a more-specific version
            // of a general function.
        
        def wrap(prefix: String, html: String, suffix: String) =
            prefix + html + suffix
        
        val wrapWithDiv = wrap("<div>", _, "</div>")

        println(wrapWithDiv("hello, World!")) // <div>hello, World!</div>
        println(wrapWithDiv("""<img src="/images/foo.png" />""")) // <div><img src="/images/foo.png" /></div>

        // Also, you can still call the original wrap method

    }
    // Creating a Method That Returns A Function
    def creatingAMethodThatReturnsAFunction(): Unit = {
        // Problem: You want to return a function (algorithm) from a function or method

        // Define an anonymous function, and return that from your method. Then
        // assign that to a function variable, and later invoke that function variable as needed

        def saySomething(prefix: String): (String => String) = 
            (str: String) => s"${prefix} ${str}"

        val sayHello = saySomething("Hello")
        println(sayHello("Chunkoz")) //Hello Chunkoz

        // You can use this approach anytime you want to encapsulate an algorithm
        // inside a method. A bit like th OOP Factory or Strategy patterns, the 
        // function your method returns can be based on the input parameter it receives.
        // For example, create a greeting method that returns an appropriate greeting 
        // based on the language specified

        def greeting(language: String) = 
            (name: String) => language.toLowerCase match
                case "english" => s"Hello, ${name}"
                case "spanish" => s"Buenos Dias, ${name}"
                case _         => s":) ${name}"
            
        val spanishGreeting = greeting("Spanish")
        val greetsp = spanishGreeting("Javier")
        println(greetsp) //Buenos Dias, Javier

        // If it doesn't seem clear that greeting is returning a String => String function,
        // you can make the code more explicit by (a) specifying the method return type and 
        // (b) creating function values inside the method

        // [a] declare the 'String => String' return type
        def greeting2(language: String): (String => String) =
            (name: String) =>
                // [b] create the function values here, then return them 
                // from the match expression
                val englishFunc = () => s"Hello, ${name}"
                val spanishFunc = () => s"Buenos Dias, ${name}"
                val defaultFunc = () => s":) ${name}"
                language.toLowerCase match
                    case "english" => println("Returning the english function")
                                      englishFunc()
                    case "spanish" => println("Returning the spanish function")
                                      spanishFunc()
                    case _         => println("default function")
                                      defaultFunc()
        
        val spanishGreeting2 = greeting2("Spanish")
        val greetsp2 = spanishGreeting2("Javier")
        println(greetsp2) //Buenos Dias, Javier
        // Returning the spanish function
        // Buenos Dias, Javier         

        // RETURNING METHODS FROM A METHOD
        def greeting3(language: String): (String => String) =
            (name: String) => 
                def englishMethod = s"Hello, ${name}"
                def spanishMethod = s"Buenos Dias, ${name}"
                def defaultMethod = s":) ${name}"
                language.toLowerCase match 
                    case "english" => println("Returning the english function")
                                      englishMethod
                    case "spanish" => println("Returning the spanish function")
                                      spanishMethod
                    case _         => println("default function")
                                      defaultMethod


        val spanishGreeting3 = greeting3("Spanish")
        val greetsp3 = spanishGreeting3("Javier")
        println(greetsp3) //Buenos Dias, Javier
        // Returning the spanish function
        // Buenos Dias, Javier
        


    }
    // Creating Partial Functions
    def creatingPartialFunctions(): Unit = {
        // Problem: You want to define a function that only works for a subset of possible
        // input values, or you want to define a series of functions that only work for a 
        // subset of input values and then combine those functions to completely solve
        // a problem.

        // In Scala a partial function can also be quried to determine if it can handle a 
        // particular value 

        val divide = (x: Int) => 42 / x
        // divide(0) //ArithmeticException: / by zero

        val divideZero = new PartialFunction[Int, Int] {    //PartialFunction[INPUT TYPE, OUTPUT TYPE]
            def apply(x: Int) = 42 / x
            def isDefinedAt(x: Int): Boolean = x != 0
        }

        // the apply method defines the function signature and body.
        // Now you can do several nice things. One thing is to test the
        // function before you attempt to use it:
        
        println(divideZero.isDefinedAt(0)) //false
        println(divideZero.isDefinedAt(2)) //true

        val x = if divideZero.isDefinedAt(1) then Some(divideZero(1)) else None
        println(x) //Some(42)

        // Partial functions can also be written using case statements:
        val divide2 : PartialFunction[Int, Int] = 
            case d if d != 0 => 42 / d

        println(divide2.isDefinedAt(0)) //false
        println(divide2.isDefinedAt(2)) //true

        // Partial Functions Scaladoc: https://oreil.ly/hpqyJ

        // The divide2 function transforms an input Int into an output Int,
        // so it's signature looks like this:

            // val divide2 : PartialFunction[Int, Int] = ...
        
        // But if it returned a String instead, it would be declared like this:

            // val divide2 : PartialFunction[Int, String] = ...

        // As an example, the following method uses that signature:
        
        // converts 1 to "one", etc., up to 5
        val convertLowNumToString = new PartialFunction[Int, String] {
            val nums = Array("one", "two", "three", "four", "five")
            def apply(i: Int) = nums(i - 1)
            def isDefinedAt(i: Int) = i > 0 && i < 6
        }

        // A terrific feature of partial functions is that you can chain them together.
        // For instance, one method may only work with even numbers, and another method may only 
        // work with odd numbers, and together they can solve all integer problems.

        // To demeonstrate this approach, the following example shows two functions that can 
        // each handle a small number of Int inputs and convert them to String results

        val convert1to5 = new PartialFunction[Int, String] {
            val nums = Array("one", "two", "three", "four", "five")
            def apply(i: Int) = nums(i - 1)
            def isDefinedAt(i: Int) = i > 0 && i < 6
        }

        val convert6to10 = new PartialFunction[Int, String] {
            val nums = Array("six", "seven", "eight", "nine", "ten")
            def apply(i: Int) = nums(i - 6)
            def isDefinedAt(i: Int) = i > 5 && i < 11
        }

        // Taken separately they can handle only five numbers. But combined with
        // orElse, the resulting function can handle 10

        val handle1to10 = convert1to5 orElse convert6to10

        println(handle1to10(3)) //three
        println(handle1to10(9)) //nine

        // One example of where you'll run into partial functions is with the collect
        // method on collections classes.  The collect method takes a partial function as 
        // input.  collect builds a new collection by applying a partial function to
        // all the elements of the collection of which the function is defined.

        // For instance, the divide2 function shown earlier is a partial function that
        // is not defined at the Int value zero. Here's that function again:
            // val divide2 : PartialFunction[Int, Int] = 
            //      case d if d != 0 => 42 / d

        // If you attempt to use this partial function with the map method and a list that
        // contains 0, it will explode with a MatchError

        // println(List(0,1,2).map(divide2))
        
        // However, if you use the same function with the collect method, it wont throw an error:
        
        println(List(0,1,2).collect(divide2)) //List(42, 21)

        // This is because the collect method is written to test the isDefinedAt method for 
        // each element it's given. Conceptually, it's similar to this:
        
        List(0,1,2).filter(divide2.isDefinedAt(_)).map(divide2)

        // As a result, the collect method doesn't run the algorithm for elements
        // that are not defined by the partial function.

        // You can see the collect method work in other situations, such as passing it a List
        // that contains a mix of data types, with a function that works only with Int values:

        println(List(42, "cat").collect{ case i: Int => i + 1 })  //List(43)

        // Because it checks the isDefinedAt method under the hood, collect can handle 
        // the fact that your anonymous function can't work with a string as an input

        // Another use of collect is when a list contains a series of Some and None values
        // and you want to extract all the Some values

        val possibleNums = List(Some(1), None, Some(3), None) 

        println(possibleNums.collect{ case Some(x) => x })  //List(1, 3)

        // Another approach to reducing a Seq[Option] to only the values inside its Some elements
        // is to call flatten on the list

        val onlySome = List(Some(1), Some(3), Some(10), None, Some(9), None).flatten
        println(onlySome) //List(1, 3, 10, 9)

        // side note: flatten's purpose in life is to take a list of lists and "flatten" it
        // to one list:
            val flattenList = List(List(1,2,3), List(4,5,6), List(7,8,9), List(10,11,12)).flatten
            println(flattenList) //List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)




        // Partial function article: https://oreil.ly/IVR6U
        List(41, 34) map { case i: Int => i + 1 } // <- notice this syntax, it works
        List(41, "cat") collect { case i: Int => i + 1 } 



    }
    // Implementing Functional Error Handling
    def implementingFunctionalErrorHandling(): Unit = {
        // Problem: You've started to write code in a functional programming style, but
        // you're not sure how to handle exceptions and other errors when writing pure functions.

        // Pure functions don't throw exceptions, instead you handle errors with Scala's
        // error handling types:
            // -Option/Some/None
            // -Try/Success/Failure
            // -Either/Left/Right

        def makeInt(str: String): Option[Int] =
            try
                Some(Integer.parseInt(str)) 
            catch 
                case e: NumberFormatException => None

        println(makeInt("5"))

        makeInt("6") match 
            case Some(i) => println(s"i = ${i}") 
            case None => println("Could not create Integer")
        
        // Given a list of strings that may or may not convert to integers, you can
        // also use makeInt like this:

        val listOfStrings: List[String] = List("1", "2", "3", "four", "five", "6")

        val optionalListOfInts: Seq[Option[Int]] =
            for s <- listOfStrings yield makeInt(s)
        
        println(optionalListOfInts)  //List(Some(1), Some(2), Some(3), None, None, Some(6))
        println(optionalListOfInts.flatten) //List(1, 2, 3, 6)

        // In addition to using the Option types, you can use the Try and Either types:
        
        import scala.util.control.Exception.*
        import scala.util.{Try, Success, Failure}
        // Option Version
        def makeIntOption(s: String): Option[Int] = allCatch.opt(Integer.parseInt(s))
        // Try Version
        def makeIntTry(s: String): Try[Int] = Try(Integer.parseInt(s))
        // Either
        def makeIntEither(s: String): Either[Throwable, Int] = allCatch.either(Integer.parseInt(s))

        // The key to these approaches is that you don't throw an exception; instead, you
        // return these error-handling types.

        // 2 benefits of using Either are that (a) it's more flexible than Try, because
        // you can control the error type and (b) it gets you ready to use FP libraries 
        // like ZIO, which use Either and similar approaches extensively.

        // A BAD (non-FP) approach to this problem is to write the method like this
        // to throw an exception:

        // don't write code like this!
        @throws(classOf[NumberFormatException])
        def makeIntBAD(s: String): Int =
            try
                Integer.parseInt(s)
            catch
                case e: NumberFormatException => throw e
        
        // Basically we never want to throw exceptions


    }
    // Real-World Example: Passing Functions Around in an Algorithm
    def passingFunctionsAroundInAnAlgorithm(): Unit = {
        
        def newtonsMethod(
            fx: Double => Double,
            fxPrime: Double => Double,
            x: Double,
            tolerance: Double
        ): Double =
                var x1 = x
                var xNext = newtonsMethodHelper(fx, fxPrime, x1)
                while math.abs(xNext - x1) > tolerance do
                    x1 = xNext
                    print(xNext)
                    xNext = newtonsMethodHelper(fx, fxPrime, x1)
                end while
                // >>
                xNext
        end newtonsMethod

        def newtonsMethodHelper(
            fx: Double => Double,
            fxPrime: Double => Double,
            x: Double
        ): Double = 
            x - fx(x) / fxPrime(x)
        end newtonsMethodHelper

        // @main
        def fx(x: Double): Double = 3*x + math.sin(x) - math.pow(math.E, x)
        def fxPrime(x: Double): Double = 3 + math.cos(x) - math.pow(math.E, x)

        val initialGuess = 0.0
        val tolerance = 0.00005

        val answer = newtonsMethod(fx, fxPrime, initialGuess, tolerance)
        println(answer)

        
    }




}
