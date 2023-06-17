package foldable

import cats.Foldable
import cats.Eval


sealed trait MList[+A]

object MList { self =>
    case class MCons[+A](head: A, tail: MList[A]) extends MList[A]
    case object MNil extends MList[Nothing]

    // smart constructors
    def mnil[A]: MList[A] = MNil
    def mcons[A](hd: A, tl: MList[A]): MList[A] = MCons(hd, tl)

    def apply[A](elems: A*): MList[A] = {
        if (elems.isEmpty) MNil
        else MCons(elems.head, apply(elems.tail: _*)) 
        // or,
        // elems.foldRight(mnil[A])((a, b) => MCons(a, b))
    }

    // implement Foldable typeclass
    implicit val listFoldable: Foldable[MList] = new Foldable[MList] {
        override def foldLeft[A, B](fa: MList[A], b: B)(f: (B, A) => B): B = 
            fa match {
                case MNil => b
                case MCons(h, t) => foldLeft(t, f(b, h))(f)
            }

        override def foldRight[A, B](fa: MList[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] = {
            def loop(as: MList[A]): Eval[B] =    
                fa match {
                        case MNil => lb
                        case MCons(h, t) =>  f(h, Eval.defer(loop(t)))
                    }
            Eval.defer(loop(fa))
        }
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
            // or
            // mxs.foldRight(Eval.now(mnil[Int])) { (i, evalxs) => if(i > 0) mcons(i, evalxs.value) else evalxs } 


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
    println(mlist.reverse) //  MCons(7,MCons(6,MCons(5,MCons(4,MCons(3,MCons(2,MCons(1,MNil)))))))

}


