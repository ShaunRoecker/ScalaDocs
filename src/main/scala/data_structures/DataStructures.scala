package datastructures

import datastructures.immutable.hashtables.{HashTableLive => ImmutableMap}
import datastructures.mutable.hashtables.{HashTableLive => MutableMap}
import datastructures.immutable.binarytree.{UnBalancedBinarySearch, BinaryNode}


object Hash:

    def runHash(): Unit =

        val str: String = "Hello"

        println(str.hashCode) // 69609650

        println(str.##) // 69609650

        val str1 = "Hello this is a longer string"

        println(str1.hashCode) // 1388119751

        println(str1.##) // 1388119751


        val m = 13

        def hash[K](key: K) = 
            val h = key.## % m
            if (h < 0) h + m else h

        println(hash("Hello")) // 6

        println(hash(1000)) // 12

        val immutableMap = ImmutableMap[String, Int](6)
        println(immutableMap)
        // HashTableLive(Vector(List(), List(), List(), List(), List(), List()))
        val newTable = immutableMap.insert("key1", 42)
        println(newTable)
        // HashTableLive(Vector(List((key1,42)), List(), List(), List(), List(), List()))

        
object Tree:

    def runTree(): Unit =
        println("Binary Search Tree")
        
        val i = 1
        val j = 2

        val ord = Ordering.Int

        println{ ord.gt(i, j) }  // false

        val ordStr = Ordering.String

        val x = "ABC"
        val y = "XYZ"

        println{ ordStr.lt(x, y) }  // true

        println { 
            Option("Hello").map(n => Some(n + " there"))
        } // Some(Some(Hello there))

        println {
            Option("Hello").flatMap(n => Some(n + " there"))
        } // Some(Hello there)


        val tree = UnBalancedBinarySearch(10, "James")(Ordering.Int)
            .insert(5, "Isabel")
            .insert(5, "Isabel")
            .insert(5, "Isabel")

        println(tree)

    


