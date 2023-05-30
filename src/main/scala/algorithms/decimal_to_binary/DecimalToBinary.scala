package algorithms.decimalbinary

// algorithm to get binary from Int
// | - 183: Int to convert
// V              remainder
// 183 / 2 = 91       1
// 91  / 2 = 45       1
// 45  / 2 = 22       1
// 22  / 2 = 11       0
// 11  / 2 =  5       1
// 5   / 2 =  2       1
// 2   / 2 =  2       0
// 1   / 2 =  0       1
// 

object BinaryToDecimal:
    //////////////////////////
    // Functional 
    def decimalToBinaryDecl(n: Int): String =
        val binary = (0 to 7).foldLeft(List[Int](), n) { case (acc, _) =>
            val (state, num) = acc
            val nextAcc = (num % 2) +: state
            val nextNum = num / 2
            (nextAcc, nextNum)
        }._1.mkString
        binary

    
    def decimalToBinaryDecl2(n: Int): String = // Lazy Evaluation
        Iterator
            .iterate(n)(n => n / 2)
             .takeWhile(_ > 0)
              .toList
               .map(n => n % 2)
                .mkString
                 .reverse
                



    //////////////////////////
    // Mutable- Imperative
    def decimalToBinaryImpr(n: Int): String =
        var x = n
        var binaryStr = ""
        while(x > 0) 
            binaryStr += (x % 2)
            x /= 2
        binaryStr.reverse

    def decimalToBinaryImpr2(n: Int): String =
        var x = n
        var stack = List[Int]()
        while(x > 0) 
            stack =  (x % 2) +: stack
            x /= 2
        stack.mkString



