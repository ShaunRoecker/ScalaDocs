package documentation

import scala.io.Source
import datastructures.Hash._
import datastructures.Tree.runTree

import language.features.implicits.implicit_parameters.ImplicitParams._
import functional.typeclasses.TypeClasses.TypeClasses2._

import functional.typeclasses.json.JsonLibraryScala3._
import functional.typeclasses.json.JsonLibraryScala3.JSONWrite

import scala.reflect._
import  language.features.types.classtag.ClassTagUsage.classTagRun
object Documentation:

    @main
    def main =
        println("Scala Documentation")
        // Working.working()
        
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
