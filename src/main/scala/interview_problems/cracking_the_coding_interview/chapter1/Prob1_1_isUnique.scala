package ctci.chapter1

// Implement an algorithm to determine if a String has all unique characters or not.
object IsUnique:

    def isUnique1(s: String): Boolean =
        s match
            case t if (s.length == s.distinct.length) => true
            case _ => false

    
    def isUnique2(s: String): Boolean =
        s.length == s.distinct.length



    def isUnique3(s: String): Boolean =
        s == s.distinct

    
    def isUnique4(s: String): Boolean =
        @annotation.tailrec
        def go(str: String, set: Set[Char]): Boolean =
            if (str.isEmpty) true
            else if (set.contains(str.head)) false
            else 
                go(str.tail, set + str.head)
        go(s, Set[Char]())

    
     

    


    
        
