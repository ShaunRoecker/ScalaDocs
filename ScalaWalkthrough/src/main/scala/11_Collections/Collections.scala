package `11_Collections`

object Collections extends App {
    println("COLLECTIONS")
    def main(): Unit = {
        // introduction()
        // choosingACollectionsClass()
        // understandingThePerformanceOfCollections()
        // understandingMutableVariablesWithImmutableCollections()
        creatingALazyViewOnACollection()
    }
    main()

    def introduction(): Unit = {
        println("INTRODUCTION")
        // Scala collections are contained in the following packages:
        import scala.collection.*
        import scala.collection.mutable.{ArrayBuffer}
        import scala.collection.immutable.{IndexedSeq, Vector}
        
        // In general, the collections is scala.collection are superclasses
        // (or, more accurately, supertypes) of the collections in scala.collection.immutable
        // and scala.collection.mutable. This means that the base operations are supplied to the
        // types in scala.collection, the immutable and mutable operations are added to the types 
        // in the other two packages
        // 

        // By default, specifying that you want an IndexedSeq with Scala3 creates a Vector
        val x = IndexedSeq(1, 2, 3)
        println(x) //Vector(1, 2, 3)

        // IndexedSeq indicates that random access is efficient

        // LinearSeq in Scala are singly linked-lists and the default is a List:
        val y = LinearSeq(1, 2, 3)
        println(y) //List(1, 2, 3)

        // Of the mutable sequences, ArrayBuffer is the most commonly used and is recommended
        // when you want a mutable sequence/
        // How to use ArrayBuffer:
        val xs = ArrayBuffer(1, 2, 3)

        xs.addOne(4)
        println(xs) //ArrayBuffer(1, 2, 3, 4)

        xs.addAll(List(5, 6, 7))
        println(xs)  //ArrayBuffer(1, 2, 3, 4, 5, 6, 7)

        // += and ++= do the same thing, respectively.
        xs += 8
        println(xs) //ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8)

        xs ++= List(9, 10)
        println(xs)  //ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        // MAP
        // Maps are a collection of key/value pairs (a hash map)

        // when you need a simple Map, you can create one without requiring
        // an import statement
        val m = Map(1 -> "a", 2 -> "b", 3 -> "c", 4 -> "d", 5 -> "e")
        println(m) //HashMap(5 -> e, 1 -> a, 2 -> b, 3 -> c, 4 -> d)

        // The mutable map is not in scope by default, so you must import or specify
        // it's full use path
        
        val mm = collection.mutable.Map(1 -> "a", 2 -> "b")

        // SET
        // Sets are a collection of unique elements

        val s = Set(1, 2, 3)
        println(s) //Set(1, 2, 3)

    }
    //   Choosing a Collections Class
    def choosingACollectionsClass(): Unit = {
        // There are 3 main catagories of colelctions to choose from:
            // Sequence
            // Map
            // Set
        
        // In addition to these main types, there are other useful collections including
            // Range
            // Stack
            // Queue

        // A few other classes act like collections, including tuples and the
        // Option, Try, and Either error-handling classes.

        // When choosing a Sequence - a sequential collection of elements - you
        // have two main decisions:
            // 1. Should the sequence be indexed, allowing rapid access to any element,
            //  or should it be implemented as a linked-list?

            // 2.  Do you want a mutable or immutable collection?
        
        // Recommended collections:
        // IMMUTABLE/INDEXED:             Vector
        // IMMUTABLE/LINEAR(LINKED_LIST): List
        // MUTABLE/INDEXED:               ArrayBuffer
        // MUTABLE/LINEAR(LINKED_LIST):   ListBuffer

        // Choosing a Map

    }
    //   Understanding the Performance of Collections
    def understandingThePerformanceOfCollections(): Unit = {
        // Problem: When choosing a collection for an application where performance 
        // is important, you want to choose the right collection for the algorithm


    }
    // Understanding Mutable Variables with Immutable Collections
    def understandingMutableVariablesWithImmutableCollections(): Unit = {
        // Problem: You may have seen that mixing mutable variable(var) with an immutable collection
        // makes it appear like the collection is somehow mutable.  For instance, when you create a var 
        // field with an immutable Vector, it appears you can somehow add new elements to the Vector:

        var x = Vector(1) 
        println(x) //Vector(1)

        x = x :+ 2
        println(x) //Vector(1, 2)

        x = x ++ List(3, 4)
        println(x) //Vector(1, 2, 3, 4)

        // How can this be?

        // Though it looks like you're mutating an immutable collection in that example, 
        // what's really happening is that the variable x points to a new sequence each 
        // time you add elements. The variable x is mutable- like a non-final field in Java
        // - so what's going on is that it's being reassigned to a new sequence during each step
        // The end result is similar to these lines of code:
        

    }
    // Creating a Lazy View on a Collection
    def creatingALazyViewOnACollection(): Unit = {
        // Problem: You're working with a large collection and want to create a lazy 
        // version of it so it will only compute and return results as they are needed.

        // create a 'view' on the collection by calling its view method. That creates a 
        // new collection whose 'transformer methods' are implemented in a nonstrict,
        // or lazy, manner.  For example, given a large list:
        val xs = List.range(0, 3_000_000) // a list from 0 to 2,999,999

        // imagine that you want to call several transformation methods on it, such as map,
        // and filter. This is contrived example, but it demonstrates a problem:

        val ys = xs.map(_ + 1)
                   .map(_ * 10)
                   .filter(_ > 1_000)
                   .filter(_ < 10_000)
        
        // If you attempt to run that example in the REPL, you'll probvably see this fatal
        // "out of memory" error 

        // Conversely, this example returns almost immediately and doesn't throw an error
        // because all it does is create a view and then four lazy transformer methods:
        
        val ysv = xs.view
                    .map(_ + 1)
                    .map(_ * 10)
                    .filter(_ > 1_000)
                    .filter(_ < 10_000)

        // Now you can work with ysv without running out of memory:
        ysv.take(3).foreach(println)
        //1010
        //1020
        //1030

        // Calling view on a collection makes the resulting collection lazy. Now when
        // transformer methods are called on the view, the elements will only be calculated
        // as they are accessed or used

        // The Scala documentation states that a view "constructs only a proxy for the result
        // collection, and its elements get constructed only as one demands them... a view is 
        // a special kind of collection that represents some basic collection, but implements all
        // transformers lazily.

        // A 'transformer' is a method that constructs a new collection from one or more existing
        // collections. This includes methods like map, filter, take, and many more.
        
        // Other methods like foreach that don't transform a collection are evaluated eagerly.
        // This explains why transformer methods like these return a view:
        val a = List.range(1, 1_000_000)
        
        val b = a.view.map(_ + 1) //SeqView(<not computed>)
        println(b)
        val c = b.take(3) //SeqView(<not computed>)
        println(c)
        // and why foreach causes action to happen
        c.foreach(println)
        // 2
        // 3
        // 4
        
        // The Use Case for Views

        // The main use case for using a view is performance, in terms of speed memory, or both.
        // The problem with the first solution is that it attempts to create new, intermediate
        // collections each time a transformer method is called:
        val bb = a.map(_ + 1) // 1st copy of the data
                  .map(_ * 10) // 2nd copy of the data
                  .filter(_ < 10_000) // 3rd copy of the data
                  .filter(_ < 10_000) // 4th copy of the data     

    }

}
