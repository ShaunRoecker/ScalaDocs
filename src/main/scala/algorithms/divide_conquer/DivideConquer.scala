package algorithms.divideconquer



// n log n 
object MaxSubArrayNLogN:

    def maxAcross(left: Vector[Int], right: Vector[Int]): Int =
        val allLeftSums = for (i <- 1 to left.length) yield left.takeRight(i).sum
        val allRightSums = for (i <- 1 to right.length) yield right.take(i).sum
        allLeftSums.max + allRightSums.max

    
    def maxSubArray(input: Vector[Int]): Int = 
        input match
            case Vector(x) => x
            case _ =>
                val (left, right) = input.splitAt(input.length / 2)
                val maxLeft = maxSubArray(left)
                val maxRight = maxSubArray(right)
                val maxCross = maxAcross(left, right)
                List(maxLeft, maxRight, maxCross).max

    

    