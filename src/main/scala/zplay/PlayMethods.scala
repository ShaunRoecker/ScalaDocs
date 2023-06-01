package documentation.zplay

import scala.io.Source
import datastructures.Hash._
import datastructures.Tree.runTree
import algorithms.sorting.merge.MergeSort
import language.features.implicits.implicit_parameters.ImplicitParams._
import functional.typeclasses.TypeClasses.TypeClasses2._
import functional.typeclasses.json.JsonLibraryScala3._
import functional.typeclasses.json.JsonLibraryScala3.JSONWrite
import scala.reflect._
import  language.features.types.classtag.ClassTagUsage.classTagRun
import language.features.implicits.contraints.ImplicitContraints
import language.features.implicits.contraints.equivalence.Cell
import algorithms.intersect.IntersectionAlgos._
import algorithms.decimalbinary.BinaryToDecimal._
import algorithms.sequences.arithprog.ArithmeticGenerator
import algorithms.sequences.geoprog.GeometricGenerator
import algorithms.sequences.generator.fib.FibonacciGenerator
import algorithms.greedy.WhichReels

object PlayMethods1:

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
        
