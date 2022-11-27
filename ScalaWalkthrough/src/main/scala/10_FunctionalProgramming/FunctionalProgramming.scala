object FunctionalProgramming extends App {
  def main(): Unit = {
        println("FUNCTIONAL PROGRAMMING")
        // introduction()
        // fPIsASupersetOfExpressionOrientedProgrammingEOP()
        usingFunctionLiterals()
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

    }



}
