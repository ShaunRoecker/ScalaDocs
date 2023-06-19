package foldable


import cats._
import cats.implicits._
import org.scalacheck.Prop


sealed trait MList[+A]

object MList { self =>
    case class MCons[+A](head: A, tail: MList[A]) extends MList[A]
    case object MNil extends MList[Nothing]

    // smart constructors
    def mnil[A]: MList[A] = MNil
    def mcons[A](hd: A, tl: MList[A]): MList[A] = MCons(hd, tl)

    def apply[A](elems: A*): MList[A] = {
        // if (elems.isEmpty) MNil
        // else MCons(elems.head, apply(elems.tail: _*)) 
        // or,
        elems.foldRight(mnil[A])((a, b) => MCons(a, b))
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

        // many methods come with the Foldable typeclass once we create an instance
        // 
        // find is not required to override
        override def find[A](fa: MList[A])(p: A => Boolean): Option[A] = 
            // fa match {
            //     case MNil => None
            //     case MCons(h, _) if p(h) => Some(h)
            //     case MCons(h, t) => find(t)(p)  
            // }
            fa.foldLeft[Option[A]](None) { (acc, i) => if (p(i)) Some(i) else acc }


    }

    implicit class MListOps[A](mxs: MList[A]) {
        def isEmpty: Boolean = 
            // mxs match {
            //     case MNil => true
            //     case _ => false
            // }
            mxs.foldLeft(true) { (bool, i) => false }

        def ++(mxs2: MList[A])(implicit foldable: Foldable[MList]): MList[A] =
            // mxs match {
            //     case MNil => mxs2
            //     case MCons(h, t) => MCons(h, t ++ mxs2)
            // }
            // ..or..
            mxs.foldRight(mxs2) { (i, acc) => MCons(i, acc) }

        

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


        def map[B](f: A => B): MList[B] =
            mxs match {
                case MNil => MNil
                case MCons(h, t) => MCons(f(h), t.map(f))
            }


        def exists2(elem: A): Boolean =
            mxs.foldLeft(false) { case (bool, i) => 
                if (bool != true && elem == i) true else bool
            }

        def toList: List[A] = {
            mxs match {
                case MNil => Nil
                case MCons(h, t) => List(h) ++ t.toList 
            }
        }

        def forAll(p: A => Boolean): Boolean = {
            mxs.foldLeft(true) { case (bool, i) =>
                bool && p(i)
            }
        }
        

        
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
            mxs.foldRight(Eval.now(mnil[Int])) { (i, acc) => if (i > 0) Eval.now(mcons(i, acc.value)) else acc } 
            // or
            mxs.foldLeft(MList[Int]()) { (acc, i) => if (i % 2 == 0) MCons(i, acc) else acc }


        def sum(implicit foldable: Foldable[MList]): Int = 
            // mxs match {
            //     case MNil => 0
            //     case MCons(h, t) => h + t.sum
            // }
            // or
            // mxs.foldLeft(0)((acc, i) => acc + i )
            // or
            foldable.foldLeft(mxs, 0)((acc, i) => acc + i)
        
    }


}




object FoldableRun  {

    val mlist = MList(1, 2, 3, 4, 5, 6, 7)
    println(mlist.filterPositive) // MCons(2,MCons(4,MCons(6,MNil)))
    println(mlist.isEmpty) // false
    println(MList(1, 2, 3).sum) // 6
    println(mlist.length) // 7

    println(MList[Int]().filterPositive) // MNil
    println(MList[Int]().isEmpty) // true
    println(mlist.reverse) //  MCons(7,MCons(6,MCons(5,MCons(4,MCons(3,MCons(2,MCons(1,MNil)))))))

    println(MList(1, 2, 3) ++ MList(4, 5, 6)) // MCons(1,MCons(2,MCons(3,MCons(4,MCons(5,MCons(6,MNil))))))
    println((MList(1, 2, 3) ++ MList(4, 5, 6)).sum) // 21

    println(MList(1, 2, 3).map(_.toString + "!"))  // MCons(1!,MCons(2!,MCons(3!,MNil)))

    val fm = MList(1, 2, 3).foldMap(_.show);  println(fm) // 123

    val fm2 = MList(1, 2, 3).foldMap(i => i * 2)  
    println(fm2)  // 12 <- applies func to each elem then combined with implicit Monoid[Int] in this case

    val multMonoid: Monoid[Int] = new Monoid[Int] {
        override def empty: Int = 1

        override def combine(x: Int, y: Int): Int = x * y
    }

    // try again explicitly supplying our multmonoid instance
    val fm3 = MList(1, 2, 3).foldMap(_ * 2)(multMonoid)
    println(fm3)  // (1 * 2) * (2 * 2) * (3 * 2)  =>  2 * 4 * 6 =>  48


    println(Foldable[MList].find(MList(1, 2, 3))(_ == 2)) // Some(2)
    println(MList(1, 2, 3).find(_ == 2)) // Some(2)


    val list = List(1, 2, 3, 4, 5, 6, 6, 5, 9)


    def hasDups[A](xs: List[A]): Boolean = {
        @annotation.tailrec
        def loop(list: List[A], set: Set[A]): Boolean = {
            if (list.isEmpty) false
            else if (set.contains(list.head)) true
            else loop(list.tail, set + list.head)
        }
        loop(xs, Set[A]())
    }

    println(hasDups(list))

    def hasDups2[A](xs: List[A]): Boolean =
        xs.foldLeft(false -> Set[A]()) { case ((bool, set), i) =>
            (if (set.contains(i)) true else bool) ->
            (if (xs.contains(i)) set + i else set)
        }._1
    

    println(hasDups2(list))

    val mlist2 = MList(1, 2, 3, 4, 5, 6, 5, 5)

    println(mlist2.exists2(10))

    def exists[F[_]: Foldable, A](fa: F[A])(p: A => Boolean): Boolean = 
        implicitly[Foldable[F]].find(fa)(p).nonEmpty
    

    println(exists[MList, Int](mlist2)(_ == 5)) // true
    println(exists[MList, Int](mlist2)(_ == 10)) // false

    val newList = MList(1, 2, 3, 4, 5).toList
    println(newList) // List(1, 2, 3, 4, 5)

    import MList._

    def toList[F[_]: Foldable, A](fa: F[A]): MList[A] =
        fa.foldRight[MList[A]](Eval.now(MNil))((a, eb) => Eval.now(MCons(a, eb.value))).value

    

    // val mlist3 = MList(5, 5, 5, 5, 5, 5, 5)
    // println(mlist3.forAll(_ == 5)) // true

    // val mlist4 = MList(5, 5, 5, 5, 4, 5, 5)
    // println(mlist4.forAll(_ == 5)) // false

    def forall2[F[_]: Foldable, A](fa: MList[A])(p: A => Boolean): Boolean = 
        fa.foldLeft(true)((acc, i) => acc && p(i))

    
        


}


