

object Sequences extends App {
    println("SEQUENCES")
    @main
    def main(): Unit = {
        // vector()
        // list()
        // arrayBuffer()
        // array()
        // makingVectorYourGoToImmutableSequence()
        // creatingAndPopulatingAList()
        addingElementsToAList()
    }
    main()
    // VECTORS
    def vector(): Unit = {
        // How to create and use a vector
        val a = Vector(1, 2, 3, 4, 5)
        val b = a.filter(_ > 2)
        println(b) //Vector(3, 4, 5)
        val c = a.map(_ * 10)
        println(c) //Vector(10, 20, 30, 40, 50)

    }
    def list(): Unit = {
        val a = List(1, 2, 3, 4, 5)
        val b = a.filter(_ > 2)
        println(b) //List(3, 4, 5)
        val c = a.map(_ * 10)
        println(c) //List(10, 20, 30, 40, 50)
    }
    def arrayBuffer(): Unit = {
        import scala.collection.mutable.ArrayBuffer
        // ArrayBuffer is the preferred implementation of a mutable sequence
        // Because it's mutable, you perform transformations methods directly on it
        // to update its contents

        // for example, for Vector or List, you would use map:
        val x = Vector(1, 2, 3)
        val y = x.map(_ * 2)
        println(y)

        // With ArrayBuffer you use mapInPlace instead of map:
        val ab = ArrayBuffer(1, 2, 3)
        val abm = ab.mapInPlace(_ * 2)

        println(abm) //ArrayBuffer(2, 4, 6)

        // In Scala, a 'buffer' is just a sequence that can grow and shrink

    }
    def array(): Unit = {
        // In scala, arrays are unique: it's mutable in that it's elements can be changed
        // but immutable in size- it can't grow or shrink.  

        // For some operations, the Array can have better performance than other collections,
        // so it's important to know how it works.
    }
    // Making Vector Your Go-To Immutable Sequence
    def makingVectorYourGoToImmutableSequence(): Unit = {
        // // Problem: You want a fast general-purpose immutable sequential collection type
        // // for your Scala applications.
        // val v = Vector("a", "b", "c")
        // println(v(0)) //a
        // println(v(1)) //b

        // // Because Vector is indexed, this call to x(9_999_999) returns almost instantly:
        // val x = (1 to 10_000_000).toVector
        // println(x(9_999_999)) //10000000

        // // Another way to create a Vector
        // val a = Vector[String]()
        // val b = a ++ List("a", "b", "c")
        // println(b) //Vector(a, b, c)

        // // Adding, appending, and prepending elements
        // // 
        // // You cant modify a vector, so you add elements to an existing Vector as you assign
        // // the result to a new variable:
        // val aa = Vector(1, 2, 3)
        // val bb = aa ++ List(4, 5)
        // val c = bb ++ List(6, 7)
        // println(c) //Vector(1, 2, 3, 4, 5, 6, 7)

        // // You append and prepend elements to a Vector just like other immutable sequences,
        // // with the following methods:

        // // - The +: method, which is an aliea for 'prepended'
        // // - The ++: method, which is an aliea for 'prependedAll'
        // // - The :+ method, which is an aliea for 'appended'
        // // - The :++ method, which is an aliea for 'appendedAll'

        // // Here are some examples where I use a var variable and assign the results of 
        // // each operation back to that variable:
        
        // PREPENDING
        var d = Vector(6)

        d = 5 +: d // Vector(5, 6)
        d = d.prepended(4) // Vector(4, 5, 6)

        d = List(2, 3) ++: d // Vector(2, 3, 4, 5, 6)
        d = d.prependedAll(Seq(0, 1)) // Vector(0, 1, 2, 3, 4, 5, 6)

        // APPENDING
        var e = Vector(1)

        e = e :+ 2 // Vector(1, 2)
        e = e.appended(3) // Vector(1, 2, 3)

        e = e :++ List(4, 5) // Vector(1, 2, 3, 4, 5)
        e = e.appendedAll(List(6, 7)) // Vector(1, 2, 3, 4, 5, 6, 7)

        // MODIFYING ELEMENTS
        // To modify an element in a Vector, call the updated method to 
        // replace one element while assigning the result to a new variable, 
        // setting the index and elem parameters:

        val aaa = Vector(1, 2, 3)
        val bbb = aaa.updated(index=0, elem=10)
        println(bbb) //Vector(10, 2, 3)
        val cc = bbb.updated(1, 20)
        println(cc) //Vector(10, 20, 3)

        // Similarly, use the patch method to replace multiple elements
        // at one time
        val v1 = Vector(1, 2, 3, 4, 5, 6)

        // specify (a) the index to start at, (b) the new sequence
        // you want, and (c) the number of elements to replace

        val v2 = v1.patch(0, List(10, 20), 2)
        println(v2) //Vector(10, 20, 3, 4, 5, 6)

        val v3 = v2.patch(2, List(30, 40, 50), 3)
        println(v3) //Vector(10, 20, 30, 40, 50, 6)

        val v4 = v3.updated(index=5, elem=60)
        println(v4) //Vector(10, 20, 30, 40, 50, 60)

        // You don't have to replace elements, you can insert with a "0" as the last param
        val v5 = v4.patch(3, List("x", "x", "x"), 0)
        println(v5) //Vector(10, 20, 30, x, x, x, 40, 50, 60)

        // The default indexed sequence is a Vector, because it has essentially instant random access,
        // as well as pretty fast functional updates.

        // Some devs use IndexedSeq and let the compiler decide implementation details
        val is1 = IndexedSeq(1, 2, 3, 4, 5)
        println(is1) //Vector(1, 2, 3, 4, 5)

    }
    // Creating and Populating a List
    def creatingAndPopulatingAList(): Unit = {
        // Problem: You want to create and populate a List.
        // Many ways...
        // (1) Basic, general use cases
        val l1 = List(1, 2, 3)      // List(1, 2, 3)
        val l2 = 1 :: 2 :: 3 :: Nil // List(1, 2, 3)
        val l3 = 1 :: List(2, 3)

        // (2) both of these create an empty list
        val ls1: List[String] = List()
        val ls2: List[String] = Nil
        
        // Next, these examples demonstrate how to let the compiler 
        // implicitly set the list type

        // (3a) implicit and explicit types, with mixed values
        val xs1 = List(1, 2.0, 33D, 4_000L)  //implicit type (List[AnyVal])
        val xs2: List[Double] = List(1, 2.0, 33D, 4_000L) //explicit type


        // 3(b) another example of explicitly setting the list type,
        // where the second example declares the type to be List[Long]
        val xs4 = List(1, 2, 3) //List[Int] = List(1, 2, 3)
        val xs5: List[Long] = List(1, 2, 3) //List[Int] = List(1, 2, 3)

        val y: Double = 4_000L
        println(y)

        val t: List[Byte] = List(1, 2, 3, 4) //Bytes are -128 to 127 valued

        //  These examples demonstrate a number of ways to create lists from ranges, including
        // the to and by methods that are available on the Int and Char types (thanks to implicit
        // conversions on those types). 

        // (4) using ranges
        val xs6 = List.range(1, 10) //List(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val xs11 = List.range(0, 10, 2) //List(0, 2, 4, 6)

        (1 to 5).toList //List(1, 2, 3, 4, 5)
        (1 until 5).toList //List(1, 2, 3, 4)
        (1 to 10 by 2).toList //List(1, 3, 5, 7, 9)
        (1 to 10 by 3).toList //List(1, 4, 7, 10)

        ('a' to 'e').toList //List(a, b, c, d, e)
        ('a' to 'e' by 2).toList //List(a, c, e)

        // These examples demonstrate a variety of ways to fill and populate lists:
        // (5) different ways to fill lists
        val xx1 = List.fill(3)("foo")  //xx1: List(foo, foo, foo)
        val xx2 = List.tabulate(5)(n => n * n) //xx2: List(0, 1, 4, 9, 16)    
        val xx3 = "hello".toList  //xs: List[Char] = List(h,e,l,l,o)

        // create a list of alphanumeric characters
        val alphanum = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toList 

        // create a list of 10 printable characters
        val r = scala.util.Random
        val printableChars = (for i <- 0 to 10 yield r.nextPrintableChar).toList 

        // Finally, if you want to use a List, but the data is frequently changing,
        // use a ListBuffer while the data is changing, then convert it to a List
        // when the data changes stop:
        // (6) use a ListBuffer while data is frequently changing
        import scala.collection.mutable.ListBuffer
        val u1 = ListBuffer(1) //ListBuffer(1)
        u1 += 2 //ListBuffer(1, 2)
        u1 += 3 //ListBuffer(1, 2, 3)

        // convert to List when the data changes stop
        val i1 = u1.toList  //List(1, 2, 3)

        // A ListBuffer (https://oreil.ly/Cm5gT) is a Buffer that's backed by a linked list.
        // It offers constant-time prepend and append operations, and most other operations are
        // linear

        // val xs = 1 :: 2 :: 3  <-error
        // prepending a '1' to a List(2, 3)
        val xs7 = 1 :: List(2, 3)  //List(1, 2, 3)

        // As shown, the :: method-called 'cons'-takes two arguments
        // - a head element, which is a single element
        // -a tail element, which is either the remaining List or the Nil value

        // An important thing about List is that when you add elements to it,
        // it's intended to be used in a manner where you always 'prepend' elements to it,
        // like this:
        val a123 = List(3) // List(3)
        val b123 = 2 :: a123 // List(2, 3)
        val c123 = 1 :: b123 // List(1, 2, 3)

        // https://oreil.ly/IBtdo
        // Important properties of the List Class
        // 
        // This class (List) is optimal for last-in-first-out(LIFO), stack-like access patterns.
        // If you need another access pattern, for example random access or FIFO, consider using
        //  a collection more suited to this than List. List has O(1) prepend and head/tail access.
        // Most other operations are O(n) on the number of elements in the list

    }
    // Adding Elements to a List
    def addingElementsToAList(): Unit = {
        // Problem: You want to add elements to a list that you are working with
        var aj = List(2)

        // prepend with ::
        var bj = 1 :: aj
        var cj = 0 :: bj

        // You can also use the ::: method to prepend one list in front of another

        var a1j = List(3, 4)
        var b1j = List(1, 2) ::: a1j

        // Rather than continually reassigning the result of prepend operations to a new
        // variable, you can declare your variable as a var and reassign the result to it:
        var xj = List(5)
        xj = 4 :: xj
        xj = 3 :: xj
        xj = List(1, 2) ::: xj

        // lists are constructed from left to right
        // I'm going to use var because im using the same variable names constantly,
        // but in real-life these would be values

        var kaj = 3 :: Nil
        var kbj = 2 :: aj
        var kcj = 1 :: bj
        var kdj = 1 :: 2 :: Nil

        println(List(1, 2) ::: List(3, 4)) //List(1, 2, 3, 4)
        println(List(3, 4).:::(List(1, 2))) //List(1, 2, 3, 4)
        // Notice how this method is used by the compiler

        // OTHER METHODS TO PREPEND OR APPEND
        // Though using :: and ::: are the common methods with Lists, there are additional methods
        // that let you prepend or append single elements to a List:
        var x = List(1)

        // prepend
        var y = 0 +: x  //List(0, 1)

        // append
        y = x :+ 2 //List(1, 2)

        // Remember appending to a List is relatively slow, only use lists for prepend
        // operations, similar to stacks

        // If you don't work with the List class a lot, another way to concatenate two lists
        // into a new list is with the ++ or concat methods

        val q = List(1, 2, 3)
        val w = List(4, 5, 6)

        // '++' is an alias for 'concat', so they work the same
        val o = q ++ w      //List(1, 2, 3, 4, 5, 6)
        val p = q.concat(w) //List(1, 2, 3, 4, 5, 6)

        // Because these methods are used consistently accross immutable collections,
        // they can be easier to remember.


        // In Scala , any method that ends with a : character is evaluated from right to left
        val hh = List(1)
        val nn = 5 +: hh
        println(nn) //List(5, 1)
        
        val hh2 = List(1)
        val nn2 = hh2 :+ 5
        println(nn2) //List(1, 5)

        val hh3 = List(1)
        val nn3 = hh3 :+ List(6, 7)
        println(nn3) //List(1, List(6, 7))

        val hh4 = List(1)
        val nn4 = hh4 :++ List(6, 7)
        println(nn4) //





    }


}
