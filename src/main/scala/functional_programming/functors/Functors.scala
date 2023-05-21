package functional.functors


// Check out the Scala3 documentation for more info on functors, although a bit intimidating
// https://dotty.epfl.ch/docs/reference/contextual/type-classes.html


object Functors {
    // Functors 

    //      A Functor is like a box of data. You can put any data type you want in the box,
    // it doesn't matter.

    //      For example, you could put a list of numbers in a the functor, or a string
    // of text, or even an object.  The functor(box) doesn't care what kind of data is inside
    // it, it just knows how to hold it.

    //      One of the things that makes a functor so useful is that they can be used to apply 
    // functions to data, specifically the data that you put in the box.

    //      For example, you could use a functor to sort a list of numbers, or find the first 
    // occurrence of a particular string in a string of text, or if you have a bunch 
    // of apples in the box, you could use a functor to find the red ones.

    //      Essentially, functors are a means of organizing data as well as applying functions to
    // to this data.  Another analogy might be- What if you have a bunch of tools that you want
    // to put away in a closet. You could put them all in a big pile in the closet, but
    // that would make them difficult to find and maintain, since the different types of
    // tools in your closet require different methods of fixing, cleaning, and generally
    // maintaining them. A better way to store the tools would be to use a functor to 
    // organize the tools.  

    //      For example, you could use one functor for all of the chisels, 
    // another for the power saws, and another for handled-tools(axes/hammers) and so on.
    // This would make it much easier to not only find specific tools when you need them,
    // but when it comes time to sharpen the chisels, you can sharpen them in succession,
    // making the process much easier and faster, the same goes for when its time to polish
    // the axe handles, and so on. 

    //      Functors work in a similar way. They allow you to organize data in a way that makes 
    // it easier to work with. 

    //      So for it's simplest definition, a functor is something that can be mapped over,
    //  with mapped over meaning that you can apply a function to each of it's elements, or
    //  each item in the box.

    //  For implementations in Scala, a Functor is a trait with a map method,  and any "container"
    //  type data type that extends this trait, or implements a map method on it's own
    //  (List, Map, Option, etc.), is a functor.

    // Functors in Scala:

    trait Functor[F[_]]:
        def map[A, B](x: F[A], f: A => B): F[B]


    //  F replresents all types that can be mapped over
    
    //  **look at the HigherKindedTypes.scala file for more on what is up with 'F[_]'

    //      To break this down, we have a trait 'Functor' that is parameterized on some
    // container type- think List, Vector, Option, etc. (a type that encapsulates other types)

    //      This trait has a map method that takes 2 arguments: 'x' which is some container type,
    // that holds some other type.  This other type can be anything and it is represented
    // as A in our Functor's map method.  The second argument is 'f' which is a function that 
    // takes the A type that we have in our container type, and transforms it into a new type B

    ////////////////////
    // **note, one point of confusion- at least for me and something helpful to remember about the
    // relationship between the type A and the type B, is that they do not HAVE to be different 
    // types.  By having an initial A type and a resulting B type, we are mearly representing that 
    // these can be different types.  An example of this is as follows:

    val listOfStrings: List[String] = List("a", "b", "c")
    // when we use the map method on "list of strings", which in this case would be
    // def map(f: A => B): List[B] 

    // Our initial A type, that is the type held in our container type List, is String
    // def map[B](f: String => B): List[B]

    // B can also be a String, or it can be some other type:
    // def map[B](f: String => String): List[String]
    val stringToString: List[String] = listOfStrings.map(_.toUpperCase)
    println(stringToString) // List(A, B, C)

    // def map[B](f: String => Int): List[Int]
    val stringToInt: List[Int] = listOfStrings.map(_.length)
    println(stringToInt) // List(1, 1, 1)
    /////////////////////

    // We can write a given instance of Functor for lists types, that we can
    // then use with Lists.  This pattern is called a type class, and it doesn't
    // actually have to do with classes as the name implies.  See TypeClasses.scala
    // for how to create type classes and use them in your code.

    given Functor[List] with
        def map[A, B](x: List[A], f: A => B): List[B] =
            x.map(f) // List already has a `map` method

    val functor = 
        summon[Functor[List]].map(List("a", "b", "c"), _.toUpperCase)

    println(functor) // List(A, B, C)

    // In Scala 3, we can take our previous Functor trait and 
    // add an extension method to make the map method directly available
    // to our container type F, instead of having to summon it

    trait Functor2[F[_]]:
        extension [A](x: F[A])
            def map[B](f: A => B): F[B]

    
    given Functor2[List] with 
        extension [A](xs: List[A])
            def map[B](f: A => B): List[B] =
                xs.map(f)

    val functor2 = 
        List("a", "b", "c").map(_.toUpperCase)

    println(functor2) // List(A, B, C)

    //          In Scala2, the ideas are the same, but we use implicits instead of 
    // givens to create our type class

    // To summarize functors:
    
    //      A functor is a type constructor or abstraction that groups together a set of 
    // functions that operate on the same type of data. Functors are a powerful tool 
    // in functional programming, as they allow you to abstract away the details of 
    // how data is stored and manipulated. This makes it easier to write code that 
    // is more concise, reusable, and efficient.

    //      Functors can be combined to create more complex functors. For example, 
    // you can combine a list and a map to create a list of maps. This is a powerful
    // technique that can be used to represent complex data structures.


}
