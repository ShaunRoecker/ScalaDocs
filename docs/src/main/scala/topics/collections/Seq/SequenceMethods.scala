

object SequenceMethods extends App {
    //////////////////////////////////////////////////////////////////////////////
    // andThen

    val list001 = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    def f1(xs: List[Int]): List[Int] =
        xs.map(_ + 1)

    def g1(xs: List[Int]): List[Int] =
        xs.map(_ - 1)

    val newFunction: List[Int] => List[Int] = 
        f1 _ andThen g1 _

    val newFuncOps = newFunction(list001)
    println(newFuncOps)

    /////////////////////////////////////////////////////////////////////////
    // applyOrElse

    // Sequences in Scala are actually functions- that's how this works...
    val x001 = List(1, 2, 3).apply(2)
    println(x001)  // 3

    // The applyOrElse method returns the value for the index x, if found,
    // otherwise executes a default function

    // Seqence.applyOrElse(index, default: (A) => B)

    val x002 = List(1, 2, 3).applyOrElse(5, _ => 0)
    println(x002)  // 0

    /////////////////////////////////////////////////////////////////////////
    // collect

    // The collect method builds a new sequence by applying a partial function
    // to all elements of the sequence on which the partial function is defined

    val pf001: PartialFunction[Int, Boolean] = {
        case i if i > 2 => i % 2 == 0
    }

    val x003 = list001.collect(pf001)
    println(x003) //List(false, true, false, true, false, true, false, true)

    // the partial function can also be place in the collect method...

    val x004 = list001.collect {
        case i if i > 2 => i % 2 == 0
    }
    println(x004)
    //List(false, true, false, true, false, true, false, true)

    /////////////////////////////////////////////////////////////////////////
    // collectFirst

    // finds the first element of a sequence for which the given partial function
    // is defined, and applies the partial function to it

    val x005 = Seq("a", 1, 5l).collectFirst { 
        case x: Int => x * 10
    }
    println(x005) // Some(10)

    val x006 = Seq("a", 1, 5l).collectFirst { 
        case x: Int => x * 10
    } match {
        case Some(value) => value
        case None => 0
    }
    println(x006) // 10

    /////////////////////////////////////////////////////////////////////////
    // combinations

    // Iterates over all possible combinations of a sequence in groups of "n"
    val list2 = List("a", "b", "c", "d")

    val combo1 = list2.combinations(2)
    println(combo1) // <iterator>
    println(combo1.toList) // List(List(a, b), List(a, c), List(a, d), List(b, c), List(b, d), List(c, d))

    val combo2 = list2.combinations(3)
    println(combo2.toList) // List(List(a, b, c), List(a, b, d), List(a, c, d), List(b, c, d))

     /////////////////////////////////////////////////////////////////////////
    //  contains

    // Tests whether the sequence contains a given value as an element
    val x007 = Seq(1, 2, 3, 4).contains(3) // true
    val x008 = Seq(1, 2, 3, 4).contains(5) // false

    val x009 = Seq('a', 'b', 'c').contains('b') // true
    val x010 = Seq('a', 'b', 'c').contains('f') // false

    /////////////////////////////////////////////////////////////////////////
    //  containsSlice

    // Tests whether the sequence contains a given sequence as an slice
    val x011 = Seq(1, 2, 3, 4).containsSlice(Seq(2,3)) // true
    val x012 = Seq(1, 2, 3, 4).containsSlice(List(4, 5)) // false
    
    val x013 = "abcde".containsSlice("bcd") // true

    /////////////////////////////////////////////////////////////////////////
    //  corresponds

    // Tests whether every element of this collections iterator relates to 
    // the corresponding element of another collection by satisfying a test 
    // predicate

    val firstTen = (1 to 10).toList
    val nextTen = (10 to 19).toList

    val x014 = firstTen.corresponds(nextTen) { (x, y) =>
        y == x + 9    
    }
    println(x014) // true

    /////////////////////////////////////////////////////////////////////////
    //  count

    // Counts the number of elements in the sequence which satisfy a predicate
    val x015 = List(1, 2, 3, 2, 1, 2, 3, 4, 2, 3).count(_ == 2)
    println(x015)

    def charCounts(str: String): List[(Char, Int)] = {
        str.distinct.map( char => (char, str.count(_ == char))).toList
    }
    println(charCounts("abracadabra"))
    // List((a,5), (b,2), (r,2), (c,1), (d,1))

    /////////////////////////////////////////////////////////////////////////
    // diff

    // Compose the multiset difference between this sequence and another sequence

    // Multiset difference - 
        // multiset difference is the set of all elements that are in the first 
        // multiset but not in the second multiset.

    val x016 = "foo bar baz"
    val x017 = x016.diff("foo")
    println(x017) // " bar baz"

    // Means "what is in the first sequence that is NOT in the second sequence"

    /////////////////////////////////////////////////////////////////////////
    //  distinct

    // Selects all the elements of this sequence ignoring the duplicates

    val x018 = List(1, 2, 3, 4, 3, 2, 1, 2, 3, 4).distinct
    println(x018) // List(1, 2, 3, 4)

    val x019 = List("a", "b", "c", "d", "e", "b", "c", "d", "e").distinct
    println(x019) // List(a, b, c, d, e)

    /////////////////////////////////////////////////////////////////////////
    //  distinctBy

    // Selects all the elements of this sequence ignoring the duplicates as 
    // determined by == after applying the transforming function f.

    val x020 = List(("a", 2.7), ("b", 2.1), ("a", 5.4)).distinctBy(_._2.floor)
    println(x020) // List((a,2.7), (a,5.4))

    // Basically, distinctBy allows us to select the criteria we use to
    // apply the distinct method.  If there are more than one occurrences
    // of elements that match this criteria, the first occurrence is included
    // in the resulting sequence.

    case class Person(name: String, age: Int)
    val people = List(
        Person("John", 25),
        Person("Sarah", 20),
        Person("John", 20),
        Person("Vinny", 22),
        Person("Vinny", 22)
    )

    val x021 = people.distinctBy(_.name)
    println(x021) // List(Person(John,25), Person(Sarah,20), Person(Vinny,22))

    val x022 = people.distinctBy(_.age)
    println(x022) // List(Person(John,25), Person(Sarah,20), Person(Vinny,22))

    // sequence.distinctBy(identity) is the same as sequence.distinct
    println(people.distinctBy(identity))
    // List(Person(John,25), Person(Sarah,20), Person(John,20), Person(Vinny,22))

    // You can use this to incorperate more complex logic into how elements are 
    // considered distinct...
    val x023 = people.distinctBy{(p: Person) => if (p.age > 21) p.name else identity}
    println(x023) // List(Person(John,25), Person(Sarah,20), Person(John,20), Person(Vinny,22))

    /////////////////////////////////////////////////////////////////////////
    //   drop

    // Selects all elements except the FIRST n elements

    val x024 = List(1, 2, 3, 4, 5, 6).drop(3)
    println(x024) // List(4, 5, 6)

    /////////////////////////////////////////////////////////////////////////
    //  dropRight

    // Selects all elements except the LAST n elements

    val x025 = List(1, 2, 3, 4, 5, 6).dropRight(3)
    println(x025)  // List(1, 2, 3)

    /////////////////////////////////////////////////////////////////////////
    // dropWhile

    // Drops longest prefix of elements that satisfy a predicate

    val mmmDrop = "  ab c".dropWhile(_ == ' ')
    println(mmmDrop)  //ab c

     def uniqueInOrder[T](xs: Iterable[T]): Seq[T] = {
        def concat(list: List[T]): List[T] = {
            list match {
                case Nil => list 
                case x :: xs => x :: concat(xs.dropWhile(_ == x))
            }
        }
        concat(xs.toList)
    }
    println(uniqueInOrder("aaabbddddaabss")) // List(a, b, d, a, b, s)


    /////////////////////////////////////////////////////////////////////////
    //  empty

    // Returns an empty iterable of the same type as this iterable

    val emptyList = List(1, 2, 3).empty
    println(emptyList)  //  List()

    /////////////////////////////////////////////////////////////////////////
    // endsWith

    val endw1 = List(1, 2, 3).endsWith(List(3))
    println(endw1) // true

    val endw2 = "this is a string".endsWith("ring") 
    println(endw2) // true

    // case class Person(name: String, age: Int)
    // val people = List(
    //     Person("John", 25),
    //     Person("Sarah", 20),
    //     Person("John", 20),
    //     Person("Vinny", 22),
    //     Person("Vinny", 22)
    // )

    val endw3 = people.endsWith(List(Person("Vinny", 22)))
    println(endw3) // true

    val endw4 = people.endsWith(List(Person("Vincent", 22)))
    println(endw4) // false

    /////////////////////////////////////////////////////////////////////////
    //  equals

    // Same as '=='

    val x026 = List(1, 2, 3).equals(List('1', '2', '3')) // false
    val x027 = List(1, 2, 3).equals(List(1, 2, 3)) // false

    /////////////////////////////////////////////////////////////////////////
    //  exists

    // Tests whether a predicate holds true for at least one element of this Sequence

    val x028 = List(1, 2, 3).exists(_ == 2) // true
    val x029 = List(1, 2, 3).exists(_ > 4) // false

    
    /////////////////////////////////////////////////////////////////////////
    //  filter

    // Selects all elements of this sequence which satisfy a predicate

    val x030 = people.filter(p => (p.name == "Vinny" && p.age >= 21))
    println(x030) // List(Person(Vinny,22), Person(Vinny,22))


    /////////////////////////////////////////////////////////////////////////
    //  filterNot

    // Selects all elements of this sequence which do NOT satisfy a predicate

    val x031 = people.filterNot(p => (p.name == "Vinny" && p.age >= 21))
    println(x031) // 
    
    










}