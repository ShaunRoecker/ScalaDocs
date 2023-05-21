package ctci.chapter1

// Given an NxN matrix, rotate by 90degress
object RotateMatrix:

    def rotateMatrix[A](matrix: Array[Array[A]]): Boolean =
        @annotation.tailrec
        def rotate(shift: Int): Unit = 
            val size = matrix.length - (2 * shift)
            if(size > 1) {
                val start = shift
                val end = shift + size - 1
                
                for(i <- 0 until size - 1) {
                    val tmp1 = matrix(start)(start + i)
                    val tmp2 = matrix(start + i)(end)
                    val tmp3 = matrix(end)(end - i)
                    val tmp4 = matrix(end  - i)(start)

                    matrix(start + i)(end) = tmp1
                    matrix(end)(end - i) = tmp2
                    matrix(end  - i)(start) = tmp3
                    matrix(start)(start + i) = tmp4
                }
                rotate(shift + 1)
            }
        
        if (matrix.length == 0 || matrix.length != matrix(0).length) false
        else rotate(0); true


object RotateMatrix2:
    // The idea with this solution is that a 90 degree rotation clockwise 
    // is flipped on the horizontal axis and then flipped along diagonal axis

    //    X X X                        O O O                         O X X
    //   -X-X-X-  =flipOnHorizonAxis=> X X X  =  flipDiagonalAxis => O X X
    //    O O O                        X X X                         O X X

    def rotate(matrix: Array[Array[Int]]): Unit = 
        reverseUpDown(matrix)
        transposeMatrix(matrix)
    

    def reverseUpDown(matrix: Array[Array[Int]]): Unit =
        var top = 0
        var down = matrix.length-1
        while(top < down)
            val tmp = matrix(top)
            matrix(top) = matrix(down)
            matrix(down) = tmp
            top += 1
            down -= 1

    def transposeMatrix(matrix: Array[Array[Int]]): Unit =
        for (r <- 0 until matrix.length)
            for (c <- 0 until r)
                var temp = matrix(r)(c)
                matrix(r)(c) = matrix(c)(r)
                matrix(c)(r) = temp

    

    

  




