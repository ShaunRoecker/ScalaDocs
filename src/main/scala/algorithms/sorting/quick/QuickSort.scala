package algorithms.sorting.quick


object QuickSort:
    // functional
    def quickSort(list: List[Int]): List[Int] =
        list match 
            case Nil => Nil
            case pivot :: tail => 
                val (less, greater) = tail.partition(_ < pivot)
                quickSort(less) ::: pivot :: quickSort(greater)
            
        
    def quickSort2[A : Ordering](ls: List[A]) = 
        import Ordered._
        def sort(ls: List[A])(parent: List[A]): List[A] = 
            if (ls.size <= 1) ls ::: parent 
            else 
                val pivot = ls.head
                val (less, equal, greater) = ls.foldLeft((List[A](), List[A](), List[A]())) {
                    case ((less, equal, greater), e) => 
                    if (e < pivot)
                        (e :: less, equal, greater)
                    else if (e == pivot)
                        (less, e :: equal, greater)
                    else
                        (less, equal, e :: greater)
                }   
                sort(less)(equal ::: sort(greater)(parent))
            
        
        sort(ls)(Nil)
        
