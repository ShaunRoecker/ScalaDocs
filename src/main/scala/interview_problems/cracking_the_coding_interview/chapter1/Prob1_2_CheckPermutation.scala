package ctci.chapter1


// Given two strings, write a method to decide if one is a permutation of the other

object CheckPermutation:
    def checkPermutation1(a: String, b: String): Boolean = 
        if (a.length != b.length) false
        else if (a.sorted == b.sorted) true
        else false


    def checkPermutation2(a: String, b: String): Boolean =
        def toMap(s: String): Map[Char, Int] =
            s.groupBy(identity).transform((k, v) => v.length)

        toMap(a) == toMap(b)

    def  checkPermutation3(a: String, b: String): Boolean = {
        def sortMkString(s: String) = s.sorted.mkString
        (a == b) ||
        (a.length == b.length) && (sortMkString(a) == sortMkString(b))
    }
    
    
    

    


    