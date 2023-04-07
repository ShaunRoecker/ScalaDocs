
// //////////////////////////////////////////////////////////////////
// ORDERED COUNT OF CHARACTERS
// Count the number of occurrences of each character 
// in a String and return it as a (list of tuples).
// For empty lists, return an empty list

// example:
    // "abracadabra" => List((a, 5), (b, 2), (r, 2), (c, 1), (d, 1))

object SolutionOrderedCount {
    def orderedCount(str: String): List[(String, Int)] =
        str.distinct.map( char => (char, str.count(_ == char))).toList
}


// //////////////////////////////////////////////////////////////////
// FIND PIVOT INDEX

// Given an array of integers, calculate the pivot-index of this array
// - The pivot index is the index where the sum of the elements strictly
//  to the left of the index is equal to the sum of all the elements
//  strictly to the right

// EX:
    // INPUT = [1, 7, 3, 6, 5, 6]
    // OUTPUT = 3

object SolutionPivotIndex {
    import scala.annotation.tailrec
    def pivotIndex(nums: Array[Int]): Int = {
        @tailrec
        def pivotAcc(idx: Int, leftSum: Int, rightSum: Int): Int = {
            if (idx == nums.length)  -1
            else if (leftSum == rightSum - nums(idx))  idx
            else pivotAcc(idx + 1, leftSum + nums(idx), rightSum - nums(idx)) 
         }
         pivotAcc(0, 0, nums.sum)
    }
}

// //////////////////////////////////////////////////////////////////
// RUNNING SUM OF 1D ARRAY

// Given an array, we define a running sum of an array as:
    //  runningSum[i] = sum(array[0] ... array[i])

// ... return the running sum of a array of Ints

object SolutionRunningSum {
    def runningSum(xs: Array[Int]): Int = {
        if (xs.isEmpty)  xs.empty
        else xs.tail.scanLeft(xs.head)( _ + _ )
    }
}

