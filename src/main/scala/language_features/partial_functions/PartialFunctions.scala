package language.features.partialfunctions

import math._


object PartialFunc:


    val pf1: PartialFunction[Int, Int] = {
        case x: Int if x > 0 => x + x
        case x => x * -1
    }

    // Upcasting

    val fn1: Int => Int = pf1

    

    def run(): Unit =
        val nums = (-5 to 5).toList
        println(nums.collect(pf1))
        println(nums.map(fn1))




