package foldable


sealed trait MList[+A]

object MList { self =>
    case class MCons[+A](head: A, tail: MList[A]) extends MList[A]
    case object MNil extends MList[Nothing]

    def apply[A](elems: A*): MList[A] = {
        if (elems.isEmpty) MNil
        else MCons(elems.head, apply(elems.tail: _*))
    }

    implicit class MListOps[A](mxs: MList[A]) {
        def isEmpty: Boolean = 
            // mxs match {
            //     case MNil => true
            //     case _ => false
            // }
            mxs.foldLeft(true) { (bool, i) => false }

        def ++(mxs2: MList[A]): MList[A] =
            mxs match {
                case MNil => mxs2
                case MCons(h, t) => MCons(h, t ++ mxs2)
            }
        

        def length: Int = 
            // mxs match {
            //     case MCons(_, t) => 1 + t.length
            //     case MNil => 0
            // }
            mxs.foldLeft(0) {(acc, i) => 1 + acc }


        def foldRight[B](z: B)(f: (A, B) => B): B = 
            mxs match {
                case MNil => z
                case MCons(h, t) => f(h, t.foldRight(z)(f))
            }
        

        @annotation.tailrec
        final def foldLeft[B](z: B)(f: (B, A) => B): B =
            mxs match {
                case MNil => z
                case MCons(h, t) => t.foldLeft(f(z, h))(f)
            }

        def reverse: MList[A] =
            mxs.foldLeft(MList[A]()) { (acc, i) => MCons(i, acc) }

        
    }

    implicit class MListIntOps(mxs: MList[Int]) {
        def filterPositive: MList[Int] = 
            mxs match {
                case MNil => MNil 
                case MCons(h, t) =>
                    if (h % 2 == 0) MCons(h, t.filterPositive)
                    else t.filterPositive
            }

        def sum: Int = 
            mxs match {
                case MNil => 0
                case MCons(h, t) => h + t.sum
            }
        
    }


}




object FoldableRun extends App {

    val mlist = MList(1, 2, 3, 4, 5, 6, 7)
    println(mlist.filterPositive) // MCons(2,MCons(4,MCons(6,MNil)))
    println(mlist.isEmpty) // false
    println(MList(1, 2, 3).sum) // 6
    println(mlist.length) // 7

    println(MList[Int]().filterPositive) // MNil
    println(MList[Int]().isEmpty) // true

}


