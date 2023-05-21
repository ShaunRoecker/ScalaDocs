package ctci.chapter1

// write a method that sets the entire row and column to zero if an element is zero

//  1  2  3          0  2  3
//  0  1  1   ===>   0  0  0
//  1  2  2          0  2  2

object ZeroMatrix:

    def allZeros(m: Array[Array[Int]]): IndexedSeq[(Int, Int)] = 
        val r = m.length
        val c = m(0).length
        for 
            i <- 0 until r
            j <- 0 until c
            if m(i)(j) == 0
        yield (i, j)
    

    def setZeroes(m: Array[Array[Int]], zs: IndexedSeq[(Int, Int)]): Unit = 
        val r = m.length
        val c = m(0).length
        zs.foreach {
            case (i,j) => {
                m(i) = Array.fill(c)(0)
                (0 until r).foreach {
                    case x => m(x)(j) = 0
                }
            }
        }
    
        
    def setZeroes(matrix: Array[Array[Int]]): Unit =
        if (matrix.isEmpty) ()
        else 
            val zeros = allZeros(matrix)
            setZeroes(matrix, zeros)
        
