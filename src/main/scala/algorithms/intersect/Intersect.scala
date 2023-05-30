package algorithms.intersect


object IntersectionAlgos:
    // Scala Imperative Intersection:  <-- Not distinct
    def intersect_imp(x: List[Int], y: List[Int]): List[Int] =
        val buf = collection.mutable.ListBuffer[Int]()
        x.foreach{ i => 
            y.foreach{ j =>
                if (i == j) 
                    buf += i
            }    
        }
        buf.toList
        // buf.toList.distinct  <- for distinct


    // Scala Functional Intersection:   <-- Not distinct
    def intersect_func(x: List[Int], y: List[Int]): List[Int] =
        x.filter(n => y.contains(n))


    // For only the distinct values of the intersection of two lists
    def intersect_func_distinct(x: List[Int], y: List[Int]): List[Int] =
        x.filter(n => y.contains(n)).distinct
    

    // Scala Functional Builtin:
    def intersection(x: List[Int], y: List[Int]): List[Int] =
        x.intersect(y)




