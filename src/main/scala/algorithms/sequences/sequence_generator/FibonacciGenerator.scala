package algorithms.sequences.generator.fib

import algorithms.sequences.generator.SequenceGenerator



class FibonacciGenerator extends SequenceGenerator:

    // Functional Solution with LazyList (Stream)
    val streamFib: LazyList[Int] = 
        1 #:: 1 #:: streamFib.zip(streamFib.tail).map(t => t._1 + t._2)
    

    override def generate(n: Int): List[Int] =
        streamFib.take(n).toList


        


    def imperitiveGenerate(total: Int): List[Int] = 
        val sequence = Array.fill(total)(0)
        sequence(0) = 1
        sequence(1) = 1
        for (n <- 2 until total)
            sequence(n) = sequence(n - 1) + sequence(n - 2)
        sequence.toList



