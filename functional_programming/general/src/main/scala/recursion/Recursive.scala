package functional.recursion


object Recursion:

    def factorial(n: Int): Long =
        @annotation.tailrec
        def loop(n: Int, acc: Long): Long =
            if (n < 2) acc
            else loop(n - 1, n * acc)
        loop(n, 1)

    
    def fib(n: Int): Int =
        @annotation.tailrec
        def loop(n: Int, current: Int, next: Int): Int =
            if (n <= 0) current
            else loop(n-1, next, current + next)
        loop(n, 0, 1)

    



    

    

    


