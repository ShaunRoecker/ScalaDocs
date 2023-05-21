package algorithms.sorting.merge

object MergeSort:
    // functional
    def mergeSort(seq: Seq[Int]): List[Int] =
        def merge(left: List[Int], right: List[Int]): List[Int] =
            val output = 
                (0 until left.length + right.length).foldLeft(List[Int](), left, right) {     
                    case (triple, _) =>
                        val (merged, leftRemaining, rightRemaining) = triple
                        (leftRemaining, rightRemaining) match
                            case (Nil, Nil) => (merged, Nil, Nil)
                            case (Nil, r :: rx) => (r :: merged, Nil, rx)
                            case (l :: lx, Nil) => (l :: merged, lx, Nil)
                            case (l :: lx, r :: rx) if l < r => (l :: merged, lx, rightRemaining)
                            case (l :: lx, r :: rx) => (r :: merged, leftRemaining, rx)
                }
            output._1.reverse
        
        if (seq.isEmpty) List()
        else if (seq.length == 1) List(seq.head)
        else
            val (left, right) = seq.splitAt(seq.length / 2)
            val sortedLeft = mergeSort(left)    
            val sortedRight = mergeSort(right)
            merge(sortedLeft, sortedRight)
        

                
   
