package algorithms

import sorting.bubble.BubbleSort._
import sorting.merge.MergeSort._
import sorting.quick.QuickSort._
import algorithms.searching.naive.NaiveSubstringSearch._
import algorithms.searching.kmp.KMPSubstringSearch._
// SORTING ALGORITHMS
/*
* ALGORITHM             Average Case      Worst Case     Memory
* 
* Bubble Sort               n^2              n^2           1
* 
* Insertion Sort            n^2              n^2           1
* 
* Quick Sort              n log n            n^2         n log n
* 
* Merge Sort              n log n          n log n         n       <- best performance in worst case
* 
*/

// SEARCHING ALGORITHMS
/*
* ALGORITHM               Average Case      Worst Case     Memory
* 
* Naive Search                                  mn            1
* 
* Rabin-Karp                   n                mn            m
* 
* Boyer-More                  n/m               mn            m
* 
* Knuth-Morris-Pratt           n                n             m    <- best performance in worst case
* 
*/
def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
}


object Sorting:
    def runSort(): Unit = 
        println("Sorting")

        val array2: Array[Int] =
            Array(4, 56, 4, 33, 58, 9, 75, 43, 28, 90, 64, 3, 53, 79, 4, 56, 8) 

        val list2: List[Int] =
            List(4, 56, 4, 33, 58, 9, 75, 43, 28, 90, 64, 3, 53, 79, 4, 56, 8) 

        println("MergeSort")
        println(mergeSort(array2))
        time { mergeSort(list2) }

        println("QuickSort")
        println(quickSort(list2))
        time { quickSort(list2) }

        println("QuickSort 2")
        println(quickSort2(list2))
        time { quickSort2(list2) }


object Searching:
    def runSearch(): Unit =
        println("Searching")
        val str = "This is an example string"
        val pattern1 = "exam"
        
        val map1 = str.map(c => if (c == 's') 'S' else c)
        println(map1)

        str.foreach(c => print(s"$c "))
        println()

        val filt1 = str.count(c => c == 's')
        println(filt1) //3

        val myList = List(2, 2, 3, 5, 6, 7)

        val optionNumber = myList.find(_ == 5)
        println(optionNumber) // Some(5)

        val n = optionNumber.getOrElse(-1)
        println(n) // 5

        val m = myList.indices.find(i => myList(i) == 5).getOrElse(-1)
        println(m) // 3

        val text = "Sally sells seashells by the seashore"
        val pattern2 = "seashore"

        val u = text.indices.find(i => text(i) == 'z').getOrElse(-1)
        println(u)

        println(naiveSubstringSearchFunctional(text, pattern2))  //29

        val stream1 = LazyList.iterate(10){i => i + i}.take(5).toList
        println(stream1) // List(10, 20, 40, 80, 160)
        // always remember to use take() for just inspecting Streams or LazyList
        // if you forget...
        //>>$ killall -9 java
        // Windows users, your on your own... 

        def someAlgo(x: Int): Int =
            x + 2 * x

        val stream2: List[Int] = LazyList.iterate(1){ i => someAlgo(i) }.take(5).toList
        println(stream2) // List(1, 3, 9, 27, 81)

        val pattern3: String = "seashells"
        val text3: String = "Sally sells seashells by the seashore."
                                       //12             
        println(text3.kMPSubstringSearch(pattern3)) // 12

        



        

              








