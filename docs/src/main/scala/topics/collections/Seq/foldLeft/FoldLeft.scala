import scala.annotation.tailrec


object FoldLeft {
    // Some examples of how to use foldLeft, how powerful it can be,
    // and how it can replace most of the use cases for creating 
    // recursive methods in a more concise manner.


    val words = List("thick", "quick", "brown", "stick")
    val nums = List(5, 9, 1, 4, 9, 5, 3, 2, 7)
    val tups = List(("Apples", 2), ("Oranges", 12),("Bananas", 1),("Grapes", 85))
    val ordNums = List(1, 2, 3, 4, 5)
    val simpleStr = "camel caser"


    // List reversal using fold
    val reversed = ordNums.foldLeft(List[Int]()) { (acc, x) => x :: acc }
    println(reversed) // List(5, 4, 3, 2, 1)

    // Reverse a String
    def reverseString(s: String): String = 
        s.foldLeft("")((acc, x) => x +: acc)

    // Without foldLeft (assuming that the 'reverse' method wasn't already defined)
    // we would need to use tail recursion to do this, at least to stay in the
    // functional paradigm.

    def reversedTailrec(list: List[Int]): List[Int] =
        @tailrec
        def loop(xs: List[Int], acc: List[Int]): List[Int] =
            if (xs.isEmpty) acc 
            else loop(xs.tail, xs.head :: acc) // for each iteration, we append the next value to the acc
        loop(list, List[Int]())
    
    println(reversedTailrec(ordNums)) // List(5, 4, 3, 2, 1)


    // Using foldLeft to create a method to convert a string to CamelCase
    def camelCase(s: String): String =
        if (s == "") s
        else s.trim.split(" ").foldLeft("")((a, b) => a + b.capitalize).mkString

    println(camelCase(simpleStr)) // CamelCaser


    // Using foldLeft to combine two Maps...
    def addMaps(map1: Map[String, Int], map2: Map[String, Int]): Map[String, Int] =
      map1.foldLeft(map2) { case (map, (k, v)) =>
         map.get(k) match
            case Some(newV) => map + (k -> (newV + v))
            case None => map + (k -> v)   
      }
        
    println(addMaps(Map("a" -> 2, "b" -> 3), Map("b" -> 4, "c" -> 5))) //Map(b -> 7, c -> 5, a -> 2)


    // One thing that really helped me in understanding how to use foldLeft
    // is that you can put any value in the accumulator parameter, be it a
    // Map, Tuple, or something else. These values can be used in each iteration
    // to perform logic, but they dont have to be in the final result. The 
    // final result just needs to be of the same type as the accumulator

    // Using foldLeft to find the longest palindrome
    def longestPalindrome(s: String): Int = { // comments are for "abssbccbbbss"
        val freq = s.groupMapReduce(identity)(k => 1)(_ + _).values 
        println(freq) // Iterable(1, 5, 2, 4)
        val (ans, odd) = freq.foldLeft(0, false) {
            case ((sum, odd), n) => 
                if (n % 2 == 0)
                   (sum + n, odd)
                else (sum + n - 1, true)
        }
        println((ans, odd)) // (10,true)
        val res = if (odd) ans + 1 else ans  
        println(res) // 11
        res
    }

    longestPalindrome("abssbccbbbss") // 11



    def maxProfit(prices: List[Int]) = 
        prices.foldLeft((Int.MaxValue, 0)) { case ((minPrice, maxSell), price) => 
            (Math.min(price, minPrice), Math.max(maxSell, price - minPrice))
        }._2
        
    

    
    
    

    




    


}
