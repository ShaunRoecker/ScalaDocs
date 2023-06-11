package algorithms.closestpair

// Euclidean Distance

// sqrt((x1, y1)^2, (x2, y2)^2)

import scala.util.Random



object ClosestPairOneDimension:
    
    case class Point(x: Int, y: Int) { self =>
        def distanceTo(other: Point): Double =
            Math.sqrt(Math.pow(self.x - other.x, 2) + Math.pow(self.y - other.y, 2))
    }

    def generatePoints(i: Int): List[Point] =
        (0 until i).map(_ => Point(Random.nextInt, 0)).toSet.toList

    

    def closestDistanceBrute(pts: List[Point]): Double = 
        val distances = 
            for ((pti, i) <- pts.zipWithIndex.dropRight(1);  ptj <- pts.drop(i + 1)) 
            yield pti.distanceTo(ptj) 
        distances.min



    def closestDistance(ptsByX: List[Point]): Double =
        if (ptsByX.size <= 3) closestDistanceBrute(ptsByX)
        else
            val (left, right) = ptsByX.splitAt(ptsByX.size / 2)
            val l = right.head.x
            val delta = math.min(closestDistance(left), closestDistance(right))
            val ptsInBoundary = ptsByX.filter(p => p.x >= 1 - delta && p.x <= 1 + delta)
            val deltaInBoundary = if (ptsInBoundary.size > 1) closestDistanceBrute(ptsInBoundary) else Int.MaxValue
            Math.min(deltaInBoundary, delta)



