package ctci.chapter1

// Considering 3 types of edits on strings: insert char, remove char, and replace char.
// Given two strings, write a method to check if they are one edit (or zero edits) away

// pale, ple => true
// pales, pale => true
// pale, bale => true
// pale, bae = false

object OneAway:

    def oneAway1(s1: String, s2: String): Boolean = 
        @annotation.tailrec
        def go(a: String, b: String): Boolean =
            (a, b) match
                case (x, y) if x == y => true
                case (x, y) if x.headOption == y.headOption => go(x.tail, y.tail)
                case (x, y) if x.length == y.length => x.tail == y.tail
                case (x, y) if x.length < y.length => x == y.tail
                case (x, y) if x.length > y.length => x.tail == y
                case _ => false
        go(s1, s2)

    
