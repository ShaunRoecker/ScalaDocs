package algorithms.sequences.arithprog

import algorithms.sequences.generator.SequenceGenerator




class ArithmeticGenerator(start: Int, difference: Int) extends SequenceGenerator:
    override def generate(total: Int): List[Int] = 
        (0 until total)
            .map(n => start + n * difference)
             .toList

        





