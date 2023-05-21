package ctci.chapter1

// Write a method that compresses a string "aabcccccaaa" to "a2b1c5a3" if the compressed
// string is shorter than the input string, or else return the input string.


object StringCompression:
    def stringCompression(s: String): String =
        @annotation.tailrec
        def go(s: String, acc: List[(Char, Int)]): List[(Char, Int)] =
            s match
                case x if x.isEmpty => acc
                case x if acc.isEmpty || x.head != acc.head._1 => go(s.tail, (s.head, 1) :: acc)
                case _ => go(s.tail, (acc.head._1, acc.head._2 + 1) :: acc)
        go(s, List()).reverse.map((c, n) => c.toString + n.toString).mkString

    
// println(stringCompression("aabcccccaaa"))
// a1a2b1c1c2c3c4c5a1a2a3

// TODO

    def stringCompression2(s: String) =
        val xs = s.toList
        if (xs.isEmpty) s
        else
            xs.tail.scanLeft(xs.head -> 1) { case ((prev, count), curr) =>
                val nextCount = 
                    if (prev == curr) count + 1
                    else 1
                curr -> nextCount
            }


// println(stringCompression2("aabcccccaaa"))
// List((a,1), (a,2), (b,1), (c,1), (c,2), (c,3), (c,4), (c,5), (a,1), (a,2), (a,3))
// Need to figure out how to finish these
    
    def stringCompression3(s: String) =
        val xs = s.toList
        if (xs.isEmpty) s
        else
            val res = xs.tail.scanLeft((xs.head, 1)) { case ((prev, count), curr) =>
                val nextCount = 
                    if (prev == curr) count + 1
                    else 1
                (curr, nextCount)
            }
            for 
                i <- res.zip(res.tail)
                if i._1._1 != i._2._1
            yield i._1

    // List((a,2), (b,1), (c,5))
    // Can't get the last a 


    def compress(chars: Array[Char]) = 
        val compressed = (chars :+ 'A').zipWithIndex.foldLeft(("", 0)) { // add an invalid 'A' as end of the array
            case ((result, lp), (char, rp)) =>                           // loop through from left, keep partial result, left pointer and right pointer
                if (chars(lp) == char) (result, lp)                      // just continue if the current (right pointer) char is the same as the left pointer char
                else {
                    val str = if (rp-lp == 1) "" else (rp-lp).toString   //  handle single occurence case
                    (s"$result${chars(lp).toString}$str", rp)            //  update the partial result and reset the left pointer
                }
        }._1  
        compressed                                                           //  the compressed string
    // Doesn't set a 1 if its the only char:          v 
        // compress("aabcccccaaa".toCharArray) ==> "a2bc5a3"
        

    def compress2(chars: Array[Char]): Int = 
        val (s, c , n) = chars.foldRight[(Vector[Char], Char, Int)]((Vector.empty, 'x', 0)) {
            case (char, (_, 'x', 0)) => (Vector.empty, char, 1)
            case (char, (s, c, n)) =>
                if (char == c) (s, c, n + 1)
                else if (n == 1) (s.prepended(c), char, 1)
                else (s.prependedAll(n.toString).prepended(c), char, 1)
        }
        (if (n == 1) s.prepended(c) else s.prependedAll(n.toString).prepended(c) ).copyToArray(chars)
    

            

