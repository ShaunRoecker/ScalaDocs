package documentation

import scala.io.Source
import datastructures.Hash._
import datastructures.Tree.runTree

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
        


    

        
