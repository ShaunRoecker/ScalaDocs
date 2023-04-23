

object SequenceMethods {
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
    println(x031) // List(Person(John,25), Person(Sarah,20), Person(John,20))


    /////////////////////////////////////////////////////////////////////////
    //  find

    // Finds the FIRST element of the sequence that matches the predicate
    // (returns an option value)

    val x032 = List(1, 2, 3, 4, 5, 6).find(_ > 5)
    println(x032)  // Some(6)

    val x033 = people.find(p => p.name == "John" || p.age >= 21) match {
        case Some(person) => person
        case _ => None
    }
    println(x033) // Person(John,25)


    /////////////////////////////////////////////////////////////////////////
    //  findLast

    // Finds the LAST element of the sequence that matches the predicate
    // (returns an option value)

    val x034 = List(1, 2, 3, 4, 2, 3, 4, 5).findLast(_ >= 4)
    println(x034) // Some(5)


    /////////////////////////////////////////////////////////////////////////
    //  flatMap

    //  def flatMap[B](f: (A) => IterableOnce[B]): Seq[B]

    // Builds a new sequence by applying a function at all elements of this 
    // sequence and using the elements of the resulting sequence

    // It's good to think of flatMap as a 'map then flatten' operation or
    // combinator, the operated-on sequence is first mapped over by applying a 
    // function, and then the result is flattened.

    val list035 = List("this", "is", "a", "list", "of", "strings")
    val x035flatMap = list035.flatMap(_.toUpperCase)
    println(x035flatMap) //  List(T, H, I, S, I, S, A, L, I, S, T, O, F, S, T, R, I, N, G, S)
    // just like...

    val x035map = list035.map(_.toUpperCase)
    println(x035map) // List(THIS, IS, A, LIST, OF, STRINGS)

    val x035flatten = x035map.flatten
    println(x035flatten) // List(T, H, I, S, I, S, A, L, I, S, T, O, F, S, T, R, I, N, G, S)


    /////////////////////////////////////////////////////////////////////////
    //   flatten

    // def flatten(implicit isIterable: (A) => IterableOnce[B]): Seq[B]

    // Converts this sequence of traversable collections into a sequence formed
    // by the elements of these traversable collections

    val x036 = List.tabulate(3, 3)((a, b) => (a * 2) + (b * 2))
    println(x036) // List(List(0, 2, 4), List(2, 4, 6), List(4, 6, 8))

    val x036flatten = x036.flatten
    println(x036flatten) // List(0, 2, 4, 2, 4, 6, 4, 6, 8)

    /////////////////////////////////////////////////////////////////////////
    //   fold

    // Folds the elements of this sequence using the specified 
    // associative binary operator

    // - difference between fold and foldLeft is fold goes in no particular
    // order or direction, so that's why the binary operation needs to be
    // 'associative'  --> (A + B) + C == A + (B + C)

    val x037 = List(1, 2, 3).fold(0)(_ + _)

    /////////////////////////////////////////////////////////////////////////
    //   foldLeft  (much more common than fold and foldRight)

    // Applies a binary operator to a start value (usually the 'identity' value,
    // like that of one of the rules of monoids and what separates them from
    // semigroups) and all elements of this sequence, going LEFT to RIGHT.

    // Once you truly understand the power of this method for solving common
    // problems, it will become your best friend and will never leave your batbelt

    def combineMaps(map1: Map[String, Int], map2: Map[String, Int]): Map[String, Int] =
        map1.foldLeft(map2) { case (map, (key, value)) =>
            map.get(key) match {
                case Some(newValue) => map + (key -> (value + newValue))
                case None => map + (key -> value)
            }    
        }

    println(combineMaps(Map("a" -> 1, "b" -> 2, "c" -> 1), Map("a" -> 3, "c" -> 2)))
    // Map(a -> 4, c -> 3, b -> 2)

    
    /////////////////////////////////////////////////////////////////////////
    //   foldRight

    // Applies a binary operator to all elements of this sequence, 
    // going RIGHT to LEFT

    val x038 = List(1, 2, 3).foldRight(0)(_ + _)
    println(x038) // 6

    // 1 + (2 + (3 + 0))

    // 0 is the identity value of integer additon, since foldRight (or any fold)
    // starts with the identity value, we use it here because 
    // 'identity' + integer == same integer
    // -and thus doen't effect our result

    
    /////////////////////////////////////////////////////////////////////////
    //   forall

    //  - tests whether a predicate holds for all elements of this sequence

    val x039 = List(2, 4, 6, 8).forall(x => x % 2 == 0)
    println(x039) // true

    val x040 = List(1, 2, 3, 4).forall(_ >= 2)
    println(x040)  // false


    /////////////////////////////////////////////////////////////////////////
    //   foreach
    //   def foreach(f: (A) => U): Unit 

    // foreach iterates over and iterable and performs an effect to each element
    // of the sequence.  Usually foreach it used to print each element.

    val x041 = people.foreach(println)

    // Person(John,25)
    // Person(Sarah,20)
    // Person(John,20)
    // Person(Vinny,22)
    // Person(Vinny,22)

    /////////////////////////////////////////////////////////////////////////
    //  groupBy

    // Partitions this sequence into a map of sequences according to some discriminator
    // function

    val x042 = List("Scala", "Linux", "Tom")
    val x043 = x042.groupBy(identity)
    println(x043) // HashMap(Scala -> List(Scala), Tom -> List(Tom), Linux -> List(Linux))

    val grpby = List(1, 2, 3, 4, 5, 6, 7).groupBy(x => x match {
        case a if a % 2 == 0 => "groupA"
        case b if b % 3 == 0 => "groupB"
        case _ => "groupC"
    })
    println(grpby) // HashMap(groupA -> List(2, 4, 6), groupB -> List(3), groupC -> List(1, 5, 7))


    /////////////////////////////////////////////////////////////////////////
    //  groupMap 

    // Partitions a sequence into a map of sequences according to a discriminator 
    // function key

    // sequence.groupMap('value to groupBy')('value to group')

    val tuples1 = List((1, 10), (2, 20), (1, 30), (2, 40))
    val groups1 = tuples1.groupMap(_._1)(_._2)
    println(groups1) // Map(1 -> List(10, 30), 2 -> List(20, 40))

    val grpPeepsBy = people.groupMap(_.name)(_.age)
    println(grpPeepsBy) // Map(Sarah -> List(20), John -> List(25, 20), Vinny -> List(22, 22))


    /////////////////////////////////////////////////////////////////////////
    //  groupMapReduce

    // Partitions this sequence into a map according to a discriminator 
    // function key. All the values that have the same discriminator are 
    // then transformed by the f function and then reduced into a single 
    // value with the reduce function.

    // It is equivalent to groupBy(key).mapValues(_.map(f).reduce(reduce)), 
    // but more efficient.

    // sequence.groupMapReduce('what to group by')('count value of each occurence')('function to combine')
    

    List("sam", "alex", "sam").groupMapReduce(identity)(_ => 1)(_ + _)
    //Map(alex -> 1, sam -> 2)

    val x044 = "aabbbdddbbccc".groupMapReduce(identity)(_ => 10)(_ * _)
    println(x044) // Map(a -> 100, b -> 100000, c -> 1000, d -> 1000)


    /////////////////////////////////////////////////////////////////////////
    //   grouped

    // Partitions elements in fixed size sequences.
    //  returns an Iterator

    val listToGroup = List(1, 2, 3, 4, 5, 6, 7, 8)
    val group3 = listToGroup.grouped(3)
    println(group3.toList) // List(List(1, 2, 3), List(4, 5, 6), List(7, 8))


    /////////////////////////////////////////////////////////////////////////
    //   sliding

    // sliding(3, 3) means they are iterators of 3, and it takes 3 steps each
    // iterator

    // sequence.sliding('size of groups')('steps between groups')

    // I convert to list to display results, but these are actually returned
    // as Iterators

    println(list001) // List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val slid3 = list001.sliding(3, 3)
    println(slid3.toList)
    // List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9), List(10))

    val slid23 = list001.sliding(2, 3)
    println(slid23.toList)
    // List(List(1, 2), List(4, 5), List(7, 8), List(10))

    val slid22 = list001.sliding(2, 2)
    println(slid22.toList)
    // List(List(1, 2), List(3, 4), List(5, 6), List(7, 8), List(9, 10))

    /////////////////////////////////////////////////////////////////////////
    // head

    // selects the first element of a sequence, note** throws an exception
    // if the sequence is empty

    val firstPerson: Person = people.head
    println(firstPerson) // Person(John,25)

    /////////////////////////////////////////////////////////////////////////
    // headOption

    // Optionally selects the first element of a sequence.  
    val firstPersonOption: Option[Person] = people.headOption
    println(firstPersonOption) // Some(Person(John,25))


    /////////////////////////////////////////////////////////////////////////
    //  indexOf

    // def indexOf[B >: A](elem: B): Int
    // def indexOf[B >: A](elem: B, from: Int): Int

    // Finds the index of the first occurrence of some element in a sequence,
    // can also have a starting place (from)

    val x047 = List(1, 2, 3).indexOf(2)
    println(x047) // 1

    val x048 = List(1, 2, 3, 4, 1, 2, 3).indexOf(2, 3)
    println(x048) // 5

    //  indexOfSlice /////////////////

    // finds the first index where this sequence contains a given sequence
    // as a slice

    val idxSlice = List(5, 4, 0, 3, 4, 2).indexOfSlice(List(0, 3, 4))
    println(idxSlice) // 2

    //  indexWhere ///////////////////

    // def indexWhere(p: (A) => Boolean): Int
    // def indexWhere(p: (A) => Boolean, from: Int): Int

    // Finds the index of the first element that satisfies a predicate. 
    // Optionally (from) a given index

    val x049 = List(1, 2, 3, 4).indexWhere(_ > 2)
    println(x049) // 2


    /////////////////////////////////////////////////////////////////////////
    //  indices

    // def indices: immutable.Range

    // Produces the range of all indices in a sequence

    val x050 = 
        for {
            i <- List(1, 2, 3, 4, 5).indices
        } do println(i)

    // 0
    // 1
    // 2
    // 3
    // 4


    /////////////////////////////////////////////////////////////////////////
    // init

    // def init: Seq[A]

    // The initial elements of the sequence, expluding the last element

    val x051 = List(1, 2, 3, 4).init
    println(x051) // List(1, 2, 3)


    /////////////////////////////////////////////////////////////////////////
    // inits

    // Iterates over the inits of this sequence

    val x052 = List(1, 2, 3).inits
    println(x052.toList) // List(List(1, 2, 3), List(1, 2), List(1), List())

    /////////////////////////////////////////////////////////////////////////
    //  intersect

    // computes the multiset intersection between this sequence and another sequence
    
    // a multiset intersection of two multisets A and B is the multiset that 
    // contains all elements that are in both A and B.

    // For example, if A = {1, 2, 3} and B = {2, 3, 4}, then the multiset intersection is {2, 3}.

    val intersection = List(1, 2, 3, 4).intersect(List(1, 2, 4, 5))
    println(intersection) // List(1, 2, 4)


    /////////////////////////////////////////////////////////////////////////
    //  isDefinedAt

    // Tests whether a sequence contains a given index

    val x053 = List(1, 2, 3).isDefinedAt(2)  
    println(x053)  // true

    val x054 = List(1, 2, 3).isDefinedAt(5)  
    println(x054)  // false 


    /////////////////////////////////////////////////////////////////////////
    //  isEmpty

    // Tests whether the sequence is empty
    println(List(1, 2, 3).isEmpty)  // false
    println(List().isEmpty)  // true

    /////////////////////////////////////////////////////////////////////////
    //  isTraverableAgain

    // Tests whether this seq can be traversed more than once

    println(List(1, 2, 3).isTraversableAgain)  // true
    println(Iterator.range(0, 1).isTraversableAgain) // false


    /////////////////////////////////////////////////////////////////////////
    // last

    // selects the last element of a sequence, note** throws an exception
    // if the sequence is empty

    val lastPerson: Person = people.last
    println(firstPerson) // Person(Vinny,22)

    /////////////////////////////////////////////////////////////////////////
    // lastOption

    // Optionally selects the last element of a sequence.  
    val lastPersonOption: Option[Person] = people.lastOption
    println(lastPersonOption) // Some(Person(Vinny,22))


    /////////////////////////////////////////////////////////////////////////
    //  lastIndexOf

    // def lastIndexOf[B >: A](elem: B): Int
    // def lastIndexOf[B >: A](elem: B, from: Int): Int

    // Finds the index of the last occurrence of some element in a sequence,
    // can also have a starting place (from)

    val x055 = List(1, 2, 3, 2).lastIndexOf(2)
    println(x055) //  3

    //  lastIndexOfSlice /////////////////

    // finds the last index where this sequence contains a given sequence
    // as a slice

    val lastIdxSlice = List(5, 4, 0, 3, 4, 7, 0, 3, 4).lastIndexOfSlice(List(0, 3, 4))
    println(lastIdxSlice) // 6

    //  lastIndexWhere ///////////////////

    // def lastIndexWhere(p: (A) => Boolean): Int
    // def lastIndexWhere(p: (A) => Boolean, from: Int): Int

    // Finds the index of the last element that satisfies a predicate. 
    // Optionally (from) a given index

    val x057 = List(1, 2, 3, 4, 2, 9).lastIndexWhere(_ > 2)
    println(x057) // 5


    /////////////////////////////////////////////////////////////////////////
    //  max, maxOption, min, minOption

    // def max[B >: A](implicit ord: Math.Ordering[B]): A

    implicit val personAgeOrdering: Ordering[Person] = 
        Ordering.fromLessThan((a, b) => a.age < b.age)

    // max
    val oldestPerson: Person = people.max
    println(oldestPerson)  // Person(John,25)
    // maxOption
    val oldestPersonOption: Option[Person] = people.maxOption
    println(oldestPersonOption)  // Some(Person(John,25))

    // min
    val youngestPerson: Person = people.min
    println(youngestPerson)  // Person(Sarah,20)
    // minOption
    val youngestPersonOption: Option[Person] = people.minOption
    println(youngestPersonOption)  // Some(Person(Sarah,20))

    /////////////////////////////////////////////////////////////////////////
    //  maxBy, maxByOption, minBy, minByOption

    // def maxBy[B](f: (A) => B)(implicit ord: Math.Ordering[B]): A

    // maxBy
    val maxByAge: Person = people.maxBy(_.age)
    println(maxByAge) // Person(John,25)

    // maxByOption
    val maxByNameOption: Option[Person] = people.maxByOption(_.name)
    println(maxByNameOption) // Some(Person(Vinny,22))

    // minBy
    val minByAge: Person = people.minBy(_.age)
    println(minByAge) // Person(Sarah,20)

    // minByOption
    val minByNameOption: Option[Person] = people.minByOption(_.name)
    println(minByNameOption) // Some(Person(John,25))

    /////////////////////////////////////////////////////////////////////////
    //  mkString

    // Creates a String from all elements of this sequence

    val mkstring1 = List("A", "B", "C").mkString
    println(mkstring1) // "ABC"

    val mkstring2 = List("A", "B", "C").mkString("-->")
    println(mkstring2) // "A-->B-->C"

    val mkstring3 = List("A", "B", "C").mkString("[{", "-->", "}]")
    println(mkstring3) // "[{A-->B-->C}]"


    /////////////////////////////////////////////////////////////////////////
    //   padTo

    // A copy of this sequence with an element value appended until a given target
    // length is reached

    // If you use Char as the pad value, you get back a String

    val padTenA = "aloha".padTo(10, 'a')
    println(padTenA) // "alohaaaaaa"

    val padOnLeft = "1234".reverse.padTo(6, '0').reverse
    println(padOnLeft) // "001234"

    // If you use a String value as the pad value, you get back a Vector by default.

    val padTenAVector = "aloha".padTo(10, "a")
    println(padTenAVector) // Vector(a, l, o, h, a, a, a, a, a, a)


    /////////////////////////////////////////////////////////////////////////
    //  partition

    //  def partition(p: (A) => Boolean): (Seq[A], Seq[A])

    // The default implementation needs to traverse the collection twice,
    // but strict collects have overridden the method to only traverse once

    // partition returns: a pair of first- all elements that satisfy pred p
    // and second, all elements that don't

    val words = List("the", "quick", "brown", "fox")
    val partitioned = words.partition(_.contains("brown"))
    println(partitioned) // (List(brown),List(the, quick, fox))

    // partition is analogous to (sequence.filter(x), sequence.filterNot(x))

    /////////////////////////////////////////////////////////////////////////
    // partitionMap

    // Applies a function f to each element of the Seq and returns a pair of sequences:
        // The first one made of those values returned by f that were wrapped in
        // scala.util.Left, the second of those values wrapped in Right

    val (strings, ints) =
        List("a", 1, 2, "b", 19).partitionMap {
            case s: String => Left(s)
            case x: Int => Right(x)
        }
    println(strings) // List(a, b)
    println(ints) // List(1, 2, 19)


    /////////////////////////////////////////////////////////////////////////
    //  permutations

    // def permutations: Iterator[Seq[A]]

    // Iterates over distinct permutations.
    // Note: Even when apploed to a view or a lazy collection,
    // it will always force the elements

    val x058 = List(1, 2, 3).permutations
    println(x058.toList)

    // List(List(1, 2, 3), List(1, 3, 2), List(2, 1, 3), List(2, 3, 1), List(3, 1, 2), List(3, 2, 1))

    val x059 = "abc".permutations
    println(x059.toList) // List(abc, acb, bac, bca, cab, cba)

    val x060 = "abb".permutations
    println(x060.toList) // List(abb, bab, bba)

    
    /////////////////////////////////////////////////////////////////////////
    //   product

    // multiplies up the product of this sequence

    val x061 = List(1, 2, 3).product
    println(x061)  // 6

    /////////////////////////////////////////////////////////////////////////
    //  reduce / reduceLeft / reduceLeftOption

    // def reduce[B >: A](op: (B, B) => B): B

    // Reduces the elements of this sequence using the specified associative binary operator.
    // Essentially the same thing as foldLeft, without the parameter for an identity element.
    
    val x062 = List(1, 2, 3, 4).reduce(_ + _)
    println(x062) // 10

    /////////////////////////////////////////////////////////////////////////
    //  reverse

    // Returns new sequence with elements in reversed order.

    val x063 = List(1, 2, 3, 4).reverse
    println(x063)  // List(4, 3, 2, 1)

    val x064 = "abcd".reverse
    println(x064)  // dcba

    
    /////////////////////////////////////////////////////////////////////////
    //   scan,  scanLeft,  scanRight

    // def scanLeft[B](z: B)(op: (B, A) => B): Seq[B]

    // Produces a sequence containing cumulative results of applying the 
    // operator going left to right, including the initial value.

    // Very similar to foldLeft, but instead it returns the result of the
    //  binary operation for each iteration of the sequence.

    // Note: will not terminate for infinite-sized collections.
    val abcdList = List("a", "b", "c", "d")
    val x065 = abcdList.scanLeft("")(_ + _)
    println(x065) // List(, a, ab, abc, abcd)

    // One trick to get make this method more useful, since as it it has to return
    // something for the first parameter, which goes to the first elements, is
    // to scanLeft from the tail of your sequence and put the head as the first parameter

    // don't forget to check the Seq is not empty, or head with throw exception

    val x066: List[String] = 
        if (abcdList.isEmpty) abcdList
        else abcdList.tail.scanLeft(abcdList.head)(_ + _)
    
    println(x066) // List(a, ab, abc, abcd)


    val x067: List[String] = {
        val rev = abcdList.reverse
        rev.init.scanRight(rev.last) {_ + _}
    }
    println(x067) // List(dcba, cba, ba, a)


    val x068: List[String] = {
        val rev = abcdList.reverse
        rev.tail.scanLeft(rev.head) {_ + _}
    }
    println(x068)  // List(d, dc, dcb, dcba)


    def longestRepetition(s: String): Option[(Char, Int)] =
      val xs = s.toList
      if (xs.isEmpty) None
      else 
        val res = xs.tail.scanLeft(xs.head -> 1) { case ((prevChar, count), char) =>
          val nextAcc = 
            if (char == prevChar) 
              count + 1 
            else 1
          char -> nextAcc
        }
        Some(res.maxBy(_._2))


    println(longestRepetition("aabbbabbbccddbbbb"))
    // Some((b,4))

    /////////////////////////////////////////////////////////////////////////
    //   segmentLength
        //  ** overloaded
    // def segmentLength(p: (A) => Boolean, from: Int): Int
    // def segmentLength(p: (A) => Boolean): Int

    // Computes the length of the longest segment that starts from some index 
    // and whose elements ALL satisfy some predicate.

    val x070 = List(1, 2, 3, 4, 5, 6, 7, 8, 9).segmentLength(_ * 2 >= 10, 4)
    println(x070) // 5


    /////////////////////////////////////////////////////////////////////////
    //  sizeCompare

    //

    










    




}