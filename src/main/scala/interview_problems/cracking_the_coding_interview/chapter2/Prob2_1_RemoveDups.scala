package ctci.chapter2
// Write a function to remove duplicates from an unsorted linked-list

object RemoveDups:

    extension[A](xs: List[A])
        def removeDups1: List[A] =
            xs.foldLeft((List[A](), Set[A]())){ case ((list, set), i) =>
                if (!set.contains(i)) (i :: list, set + i)
                else (list, set)    
            }._1.reverse

    
