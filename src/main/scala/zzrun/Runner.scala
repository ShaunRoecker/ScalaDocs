package documentation.zzrun

import scala.io.Source
import datastructures.Hash._
import datastructures.Tree.runTree
import algorithms.sorting.merge.MergeSort
import language.features.implicits.implicit_parameters.ImplicitParams._
import functional.typeclasses.TypeClasses.TypeClasses2._
import functional.typeclasses.json.JsonLibraryScala3._
import functional.typeclasses.json.JsonLibraryScala3.JSONWrite
import scala.reflect._
import language.features.types.classtag.ClassTagUsage.classTagRun
import language.features.implicits.contraints.ImplicitContraints
import language.features.implicits.contraints.equivalence.Cell
import algorithms.intersect.IntersectionAlgos._
import algorithms.decimalbinary.BinaryToDecimal._
import algorithms.sequences.arithprog.ArithmeticGenerator
import algorithms.sequences.geoprog.GeometricGenerator
import algorithms.sequences.generator.fib.FibonacciGenerator
import algorithms.greedy.WhichReels
import language.features.partialfunctions.PartialFunc
import algorithms.shuntingyard.ShuntingYard._
import fileio.filecontents1.FileContents1._
import java.io.File
import functional.typeclasses.TypeClasses3
import functional.typeclasses.TypeClasses3.TypeClassInterface3._
import language.features.types.hktypes.HigherKindedTypes1._
import functional.typeclasses.TypeClasses3.CombinerInstances.mapCombo
import language.features.types.hktypes.HigherKindedTypes1._
import functional.typeclasses.sorter.SorterInterface._
import functional.typeclasses.sorter.SorterInstances._
import algorithms.divideconquer.MaxSubArrayNLogN._

object Runner:

    def run1(): Unit =

        val uberMap: Map[String, List[Map[String, Int]]] = 
            Map(
                "first" -> List(
                    Map(
                        "a" -> 1,
                        "b" -> 2,
                    ),
                    Map(
                        "a" -> 1,
                        "b" -> 2,
                    )
                ),
                "second" -> List(
                    Map(
                        "b" -> 2,
                        "c" -> 3    
                    )
                )
            )

        runHash()

        runTree()
        implicit val retryProcess = RetryParams(5)

        retry[Int](checkErrorMethod())

        val sortedDist: List[Distance] =
            distances.genSort

        println(sortedDist)
        // List(Distance(20), Distance(30), Distance(90), Distance(100))

        val sortedDistNonExt: List[Distance] =
            genSortNonExt(distances)

        println(sortedDistNonExt)
        // List(Distance(20), Distance(30), Distance(90), Distance(100))


        given hoursInADayOnEarth: Int = 24


        def hoursInDay(days: Int)(using hrsInADay: Int): Int = 
            days * hrsInADay

        hoursInDay(7)

        val list = List(1, 2, 3)

        println(list ++ Some(4) ++ None)   // List(1, 2, 3, 4)
        
        
        // JSON

        println(("Hello, World!").asJson)
        println(42.asJson)
        println(42.3.asJson)


        println(List("Scala", "Python", "C++", "Rust").asJson)
        // [Scala,Python,C++,Rust]

        val map1 = Map("Scala" -> 1, "Python" -> 2, "C++" -> 3, "Rust" -> 4)

        println(map1.asJson)
        // {
        //     Scala: 1,
        //     Python: 2,
        //     C++: 3,
        //     Rust: 4
        // }

        val map2 = 
            Map(
                "Scala" -> Map("features" -> List(1, 2, 3, 4)), 
                "Python" -> Map("features" -> List(1, 2, 3, 4)), 
                "C++" -> Map("features" -> List(1, 2, 3, 4)), 
                "Rust" -> Map("features" -> List(1, 2, 3, 4)), 
            )

        
        println(map2.asJson)
        // {
        //     Scala: {
        //     features: [1,2,3,4]
        // },
        //     Python: {
        //     features: [1,2,3,4]
        // },
        //     C++: {
        //     features: [1,2,3,4]
        // },
        //     Rust: {
        //     features: [1,2,3,4]
        // }
        // }
 
        classTagRun()


        val lista = IndexedSeq(1, 6, 4, 3, 6, 8, 0,5, 6,3, 44, 6, 55, 23)
        val mergedLista = MergeSort.mergeSort(lista)

        println(mergedLista)


        ImplicitContraints.implConRun()


        val intNum = implicitly[Numeric[Int]]

        println(intNum.times(5, 8)) // 40

        println(intNum.parseString("1")) // Some(1)


        val pfc = List(1, 0, 2, 3, 4).collect {
            case x if x != 0 => 42 / x
        }

        println(pfc)

        val partialfunc: PartialFunction[Int, Int] = {
            case x if x != 0 => 42 / x
        }

    
        println(partialfunc(10))

    def run2(): Unit =
        val cellInt: Cell[Int] = Cell(42)
        val cellInt2: Cell[Int] = Cell(2)

        val cellString: Cell[String] = Cell("string")

        // print(cellString * cellInt)
        // Cannot prove that String =:= Int.

        print(cellInt * cellInt2)  // Cell(84)

    def run3(): Unit =
        val aList: List[Int] = List(1, 5, 3, 8, 9, 5, 7, 3, 6, 4)
        val bList: List[Int] = List(10, 11, 3, 12, 9, 12, 7, 10, 16, 0)

        // println(intersect_imp(aList, bList)) //  List(3, 9, 7, 3)

        // println(intersect_func(aList, bList)) //  List(3, 9, 7, 3)

        // println(intersect_func_distinct(aList, bList)) //  List(3, 9, 7)

        // println(intersection(aList, bList)) //  List(3, 9, 7)


        // Int to Binary String
        println("Imperative")
        println(decimalToBinaryImpr(183)) // 11101101
        println(decimalToBinaryImpr2(183)) // 11101101
        println(decimalToBinaryImpr2(145)) // 10010001


        println("functional")
        println(decimalToBinaryDecl(183)) // 11101101
        println(decimalToBinaryDecl2(183)) // 11101101


        // val rands = Iterator.continually(Math.random())
        // println(rands.take(5).toList)

        //  Starts at 6 and increments infinitely
        // val plusOne = Iterator.iterate(6)(n => n + 1)
        // println(plusOne.take(5).toList)

        // val divByTwo = Iterator.iterate(183)(n => n / 2)

        // println(divByTwo.takeWhile(n => n > 0).toList.map(n => n % 2).reverse.mkString)


        val arithGen = new ArithmeticGenerator(start = 0, difference = 5)
        println(arithGen.generate(10))
        // List(0, 5, 10, 15, 20, 25, 30, 35, 40, 45)

        println(arithGen.generateStr(10)) 
        // 0, 5, 10, 15, 20, 25, 30, 35, 40, 45


        val genGen = new GeometricGenerator(start = 0, ratio = 5)

        val fibGen = new FibonacciGenerator
        println(fibGen.generate(10)) // List(1, 1, 2, 3, 5, 8, 13, 21, 34, 55)


        val stream = 1 #:: 2 #:: 3 #:: Stream.empty

        val streamRand = Stream.continually(math.random())

        println(streamRand(1)) //  0.928393321058183
        println(streamRand(3)) // 0.873618326520455

        // Note below the values are the same as above despite being
        // generated randomly, this is because a LazyList memoizes values
        // previously computed
        println(streamRand(1)) //  0.928393321058183
        println(streamRand(3)) // 0.873618326520455


        val list1 = List("A", "B", "C")

        val list2 = List("a", "b", "c")

        val zips = list1.zip(list2).map(t => t._1 + t._2)
        println(zips) // List(Aa, Bb, Cc)

        val zips2 = list1.zip(list2.tail).map(t => t._1 + t._2)
        println(zips2) // List(Ab, Bc)

        println(list1.zip(list2)) // List((A,a), (B,b), (C,c))


        val listFib = List(1, 1, 2, 3, 5)
        val x2 = listFib.zip(listFib.tail).map(t => t._1 + t._2) // List(2, 3, 5, 8)
        println(x2)

        // List(1, 1, 2, 3, 5)
        // List(1, 2, 3, 5)


        val stream2 = 1 #:: 1 #:: Stream.empty
        val stream2f = stream #:: Stream.empty
        println(stream2f)


        val fib = fibGen.streamFib
        println(fib.take(10).toList)  // List(1, 1, 2, 3, 5, 8, 13, 21, 34, 55)


        val matrix = List.tabulate(5, 5){ (a, b) => (a + 1) * (b + 1) }
        println(matrix)

        // List(
        //     List(1, 2,  3,  4,  5), 
        //     List(2, 4,  6,  8,  10), 
        //     List(3, 6,  9,  12, 15), 
        //     List(4, 8,  12, 16, 20), 
        //     List(5, 10, 15, 20, 25))

    def run4(): Unit = 
        
        def fizzbuzz(n: Int): List[String] =
            val res =
                for(i <- 1 to n) yield
                    (i % 3, i % 5) match
                        case (0, 0) => "fizzbuzz"
                        case (0, _) => "fizz"
                        case (_, 0) => "buzz"
                        case _ => i.toString

            res.toList

        println(fizzbuzz(15))
                    
        import WhichReels._
        // println(imperativeWhichReels(19493))

        println(functionalWhichReels(34)) // List(3, 0, 2, 0)
        
        def compareNeighbors(xs: List[Int], compare: (Int, Int) => Int): List[Int] =
            val res =
                for (pair <- xs.sliding(2)) yield
                compare(pair(0), pair(1))
            res.toList

        println(List(1, 2, 3, 4, 5, 6, 7).sliding(2).toList)
        // List(List(1, 2), List(2, 3), List(3, 4), List(4, 5), List(5, 6), List(6, 7))

        val list1 = List(1, 2, 3, 4, 5, 6, 7, 8)
        println(compareNeighbors(list1, (a,b) => a + b))   
        // List(3, 5, 7, 9, 11, 13, 15)

        val adder = (_: Int) + (_: Int) 

        println(compareNeighbors(list1, adder))   

        def compareTriplets(xs: List[Int], compare: (Int, Int, Int) => Int): List[Int] =
            val res =
                for (triplet <- xs.sliding(3)) yield
                compare(triplet(0), triplet(1), triplet(2))
            res.toList

        PartialFunc.run()

        def gravity = 9.81

        def force(mass: Double = 1, acceleration: Double = gravity) =
            mass * acceleration


        println(force())


        @annotation.tailrec
        def factSeq(n: Int, acc: List[Long] = List(1L), ct: Int = 2): List[Long] =
            if (ct > n) acc
            else factSeq(n, acc = ct * acc.head :: acc, ct = ct + 1)


    def run5(): Unit =
        // highLow() // (List(/, *),List(+, -))

        println(toPostfix("2 + 3")) // List(2, 3, +)

        println(evaluateInfix("2 + 2 * 5")) // 20.0
        println(evaluateInfix("4 / 3 * 6")) // 8.0
        println(evaluateInfix("2 + 2 * 5 - 4 / 3 * 6 + 10 / 3 * 2")) // 28.0

    
    def run6(): Unit =
        val testFile: File = new File("./data/example.txt")
        println(testFile)

        val question = fileContainsQuestion(testFile)
        println(question) // false

        val emphasize = emphasizeFileContents(testFile)
        println(emphasize)


        val wfc1 = withFileContents(testFile, { line =>
            val letters = line.toLowerCase.filterNot(_ == ' ').toSeq
            // val grouped = letters.groupBy(identity)
            // grouped.maxBy { case (char, seq) => seq.length}._1
            val grouped = letters.groupMapReduce(identity)(_ => 1)(_ + _)
            grouped.maxBy(_._2)._1   
        }, 'e')

        println(wfc1)

        val wfc2 = withFileContentsLoan(testFile, 'e') { line =>
            line
               .toLowerCase
                .filterNot(_ == ' ')
                 .toSeq
                  .groupMapReduce(identity)(_ => 1)(_ + _)
                   .maxBy(_._2)._1
        }


    def run7(): Unit = 
        

        extension[A](list: List[A])
            def _contains(elem: A): Boolean =
                list.foldLeft(false){ case (bool, i) =>
                    bool match
                        case true => true
                        case false =>
                            if (i == elem) true else false            
                }
        

        val listInt = List(1, 2, 3, 4, 5)
        println(listInt._contains(4)) // true
        println(listInt._contains(6)) // false

        val listChar = List('a', 'b', 'c', 'd', 'e')
        println(listChar._contains('b')) // true
        println(listChar._contains('z')) // false


        println(new String("hello").eq(new String("hello"))) // false
        // will always be false, eq means the same instance in memory
        // 'eq' verifies if they point to the same memory location,
        // similar to id() in python

        println("hello" eq "hello") // true
        // so strings in scala are automatically interned, unless
        // the use the 'new' keyword on initiation



        def allPrimes2(n: Int) =
            val beforeFilter = List.tabulate(n)(n => n + 1)
            val beforeFilterZWI = beforeFilter.zipWithIndex.map((x, i) => (x, i + 1)).drop(1)
            beforeFilterZWI

        println(allPrimes2(11))


    def run8(): Unit = 
        println("run8")   

       
        case class Person(name: String)
        val p1Hash = Person("A").hashCode
        val p2Hash = Person("A").hashCode

        println(p1Hash == p2Hash)

        val anyList = List(true, 1, 1.1, None)
        


        val map1 = Map("a" -> 10, "b" -> 3, "c" -> 2, "d" -> 2)
        val map2 = Map("a" -> 1, "b" -> 1, "c" -> 1, "d" -> 2)

        val mapsJoined = join(map1, map2)
        println(mapsJoined)
        // Map(a -> 11, b -> 4, c -> 3, d -> 4)

        val joined = map1.joinWith(map2)
        println(joined)
        //  Map(a -> 11, b -> 4, c -> 3, d -> 4)

        val joined2 = map1.join(map2)
        println(joined2)
        //  Map(a -> 11, b -> 4, c -> 3, d -> 4)

        // Higher-Kinded Types

        val monadList = new MonadList(List(1, 2, 3, 4, 5))
        val monadList2 = monadList.map(x => x * 2)
        val monadList3 = monadList.flatMap(x => List(x, x + 1))

        println(monadList) // MonadList(List(1, 2, 3, 4, 5))
        println(monadList2) // List(10, 8, 6, 4, 2)
        println(monadList3) // List(1, 2, 2, 3, 3, 4, 4, 5, 5, 6)

    def run9(): Unit =
        val list = List(1, 8, 43, 6, 32, 7, 55, 3, 7)
        println(list.sortDesc)
        // List(55, 43, 32, 8, 7, 7, 6, 3, 1)

        // DivideConquer

        def factorial(n: Int): Int =
            @annotation.tailrec
            def go(n: Int, acc: Int): Int =
                if (n <= 0) acc
                else go(n-1, n * acc)
            go(n, 1)

        println(factorial(5)) //120

        def factorial2(n: Int) =
            val xs = List.tabulate(n)(_ + 1)
            xs.tail.foldLeft(xs.head){case (acc, i) => acc * i }

        println(factorial2(5))

        def factorial3(n: Int): Int =
            List.tabulate(n)(_ + 1).foldLeft(1){ case (acc, i) => acc * i }

        println(factorial3(5))


        println(List.tabulate(5)(_ + 1))

        def fib(n: Int): Int =
            @annotation.tailrec
            def go(n: Int, current: Int, next: Int): Int =
                if n <= 0 then current
                else go(n-1, next, current + next)
            go(n, 0, 1)

        println(fib(10)) // 55

        extension[A](xs: List[A])
            def listLength: Int =
                xs.foldLeft(0){(acc, i) => acc + 1}


        println(List(1, 2, 3, 4, 5, 6, 7).listLength)

        println((2 to 10 by 2).toList)

        
                
        




                



            

            




