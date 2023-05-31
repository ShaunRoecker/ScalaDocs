package algorithms.sequences.generator.fib

import algorithms.sequences.generator.SequenceGenerator



class FibonacciGenerator extends SequenceGenerator:
    override def generate(total: Int): List[Int] = 
        val sequence = Array.fill(total)(0)
        sequence(0) = 1
        sequence(1) = 1
        for (n <- 2 until total)
            sequence(n) = sequence(n - 1) + sequence(n - 2)
        sequence.toList




    // Functional Solution with LazyList (Stream)
    