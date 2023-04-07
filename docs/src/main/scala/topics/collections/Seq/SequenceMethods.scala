

object SequenceMethods extends App {
    //////////////////////////////////////////////////////////////////////////////
    // andThen

    val list001 = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    def f1(xs: List[Int]): List[Int] =
        xs.map(_ + 1)

    def g1(xs: List[Int]): List[Int] =
        xs.map(_ - 1)

    val newFunction: List[Int] => List[Int] = 
        f1 _ andThen g1 _

    val newFuncOps = newFunction(list001)
    println(newFuncOps)

    /////////////////////////////////////////////////////////////////////////
    // applyOrElse

    // Sequences in Scala are actually functions- that's how this works...
    val x001 = List(1, 2, 3).apply(2)
    println(x001)  // 3

    // The applyOrElse method returns the value for the index x, if found,
    // otherwise executes a default function

    // Seqence.applyOrElse(index, default: (A) => B)

    val x002 = List(1, 2, 3).applyOrElse(5, _ => 0)
    println(x002)  // 0

    /////////////////////////////////////////////////////////////////////////
    // collect

    // The collect method builds a new sequence by applying a partial function
    // to all elements of the sequence on which the partial function is defined

    val pf001: PartialFunction[Int, Boolean] = {
        case i if i > 2 => i % 2 == 0
    }

    val x003 = list001.collect(pf001)
    println(x003) //List(false, true, false, true, false, true, false, true)

    // the partial function can also be place in the collect method...

    val x004 = list001.collect {
        case i if i > 2 => i % 2 == 0
    }
    println(x004)
    //List(false, true, false, true, false, true, false, true)

    /////////////////////////////////////////////////////////////////////////
    // collectFirst

    // finds the first element of a sequence for which the given partial function
    // is defined, and applies the partial function to it

    val x005 = Seq("a", 1, 5l).collectFirst { 
        case x: Int => x * 10
    }
    println(x005) // Some(10)

    val x006 = Seq("a", 1, 5l).collectFirst { 
        case x: Int => x * 10
    } match {
        case Some(value) => value
        case None => 0
    }
    println(x006)






}