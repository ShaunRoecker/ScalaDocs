package datastructures.functional.containers

import scala.collection.immutable.{List => Lst}
import datastructures.functional.errorhandling.{Option, Some, None}



sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]


object List:

    def apply[A](elems: A*): List[A] =
        if (elems.isEmpty) Nil
        else Cons(elems.head, apply(elems.tail: _*))


    extension(as: List[Int])
        def sum: Int =
            @annotation.tailrec
            def loop(list: List[Int], acc: Int): Int =
                list match
                    case Nil => acc
                    case Cons(h, t) => loop(t, h + acc)
            loop(as, 0)


        def product: Int =
            @annotation.tailrec
            def loop(list: List[Int], acc: Int): Int =
                list match
                    case Nil => acc
                    case Cons(h, t) => loop(t, h * acc)
            loop(as, 1)


        def sum2: Int =
            as.foldRight(0)(_ + _)


        def product2: Int =
            as.foldRight(1)(_ * _)


        def incrementEach: List[Int] =
            as.foldRightViaFoldLeft(Nil: List[Int])((i, acc) => Cons(i + 1, acc))

        def addPairWise(as2: List[Int]): List[Int] =
            (as, as2) match
                case (Nil, _) | (_, Nil) => Nil
                case (Cons(h1, t1), Cons(h2, t2)) => Cons(h1 + h2, t1.addPairWise(t2))


    extension[A](xs: List[A])
        def headOption: Option[A] =
            xs match
                case Nil => None
                case Cons(h, _) => Some(h)

        def lastOption: Option[A] =
            xs match
                case Nil => None
                case Cons(h, Nil) => Some(h)
                case Cons(h, t) => t.lastOption

        
        def startsWith(prefix: List[A]): Boolean =
            (xs, prefix) match
                case (_, Nil) => true
                case (Cons(h, t), Cons(h2, t2)) if h == h2 => t.startsWith(t2)
                case _ => false


        def hasSubsequence(sub: List[A]): Boolean =
            xs match
                case Nil => sub == Nil
                case _ if xs.startsWith(sub) => true
                case Cons(h, t) => t.hasSubsequence(sub)

        

        def map[B](f: A => B): List[B] =
            xs.foldRight(Nil: List[B])((h, t) => Cons(f(h), t))

        def map2[B](f: A => B): List[B] = // uses foldRightViaFoldLeft
            xs.foldRightViaFoldLeft(Nil: List[B])((h, t) => Cons(f(h), t))


        def mapFast[B](f: A => B): List[B] =
            import scala.collection.mutable.ListBuffer
            val buf = new ListBuffer[B]
            @annotation.tailrec
            def loop(list: List[A]): List[B] =
                list match
                    case Nil => List(buf.toList*)
                    case Cons(h, t) => buf += f(h); loop(t)
            loop(xs)


        def mapFast2[B](f: A => B): List[B] =
            val buf = new collection.mutable.ListBuffer[B]
            def go(list: List[A]): Unit =
                list match
                    case Nil => ()
                    case Cons(h, t) => buf += f(h); go(t)
            go(xs)
            List(buf.toList*)

        
        def filter(p: A => Boolean): List[A] =
            xs.foldRightViaFoldLeft(Nil: List[A])((h, t) => if (p(h)) Cons(h, t) else t)
        
        
        def filterFaster(p: A => Boolean): List[A] =
            val buf = new collection.mutable.ListBuffer[A]
            def loop(list: List[A]): Unit =
                list match
                    case Nil => ()
                    case Cons(h, t) => if (p(h)) buf += h else loop(t)
            loop(xs)
            List(buf.toList*)
        

        def filterViaFlatMap(p: A => Boolean): List[A] =
            xs.flatMap(x => if (p(x)) List(x) else Nil)



        def filterViaFlatMap2(p: A => Boolean): List[A] =
            xs.flatMap(elem => if (p(elem)) List(elem) else Nil)



        def flatMap[B](f: A => List[B]): List[B] = 
            xs.foldLeft(List[B]())(_ ++ f(_))


        def flatMap2[B](f: A => List[B]): List[B] = 
            xs.foldLeft(List[B]())((acc, i) => acc ++ f(i))



        def zipWith[B, C](xs2: List[B])(f: (A, B) => C): List[C] =
            (xs, xs2) match
                case (Nil, _) | (_, Nil) => Nil
                case (Cons(h1, t1), Cons(h2, t2)) =>
                    Cons(f(h1, h2), t1.zipWith(t2)(f))


        def zipWithFaster[B, C](xs2: List[B])(f: (A, B) => C): List[C] =
            @annotation.tailrec
            def loop(a: List[A], b: List[B], acc: List[C]): List[C] =
                (a, b) match
                    case (Nil, _) | (_, Nil) => acc
                    case (Cons(h1, t1), Cons(h2, t2)) =>
                        loop(t1, t2, Cons(f(h1, h2), acc))
            loop(xs, xs2, Nil: List[C]).reverse

        

        def ++(xs2: List[A]): List[A] =
            xs match
                case Nil => xs2
                case Cons(h, t) => Cons(h, t ++ xs2)
        
        def append(xs2: List[A]): List[A] =
            xs ++ xs2

        def tail: List[A] =
            xs match
                case Nil => sys.error("can't call tail on empty list")
                case Cons(_, t) => t
        
        def head: A =
            xs match
                case Nil => sys.error("head on empty list")
                case Cons(h, _) => h


        def setHead(head: A): List[A] =
            xs match
                case Nil => sys.error("can't call setHead on empty list")
                case Cons(_, t) => Cons(head, t)


        def drop(n: Int): List[A] =
            if (n <= 0) xs
            else xs match
                case Nil => Nil
                case Cons(_, t) => tail.drop(n-1)


        def dropWhile(p: A => Boolean): List[A] =
            xs match
                case Cons(h, t) if p(h) => t.dropWhile(p)
                case _ => xs


        def take(n: Int): List[A] =
            if (n <= 0) Nil
            else xs match
                case Nil => Nil
                case Cons(h, t) => Cons(h, t.take(n-1))

        def takeWhile(p: A => Boolean): List[A] =
            xs match
                case Cons(h, t) if (p(h)) => Cons(h, t.takeWhile(p))
                case _ => Nil

        def isEmpty: Boolean =
            xs match
                case Nil => true
                case _ => false
        
        def forall(p: A => Boolean): Boolean =
            if (xs.isEmpty) true
            else 
                xs.foldLeft(true){ case (acc, i) => 
                     acc && p(i)
                }

        def exists(p: A => Boolean): Boolean =
            if (xs.isEmpty) true
            else
                xs.foldLeft(false){ case (acc, i) =>
                   acc || p(i) 
                }
        
        def init: List[A] =
            xs match
                case Nil => sys.error("init on empty list")
                case Cons(_, Nil) => Nil
                case Cons(h, t) => Cons(h, t.init)


        def initFaster: List[A] =
            import scala.collection.mutable.ListBuffer
            val buf = new ListBuffer[A]
            @annotation.tailrec
            def loop(curr: List[A]): List[A] =
                curr match
                    case Nil => sys.error("init on empty list")
                    case Cons(_, Nil) => List(buf.toList*)
                    case Cons(h, t) => 
                        buf += h;
                        loop(t)
            loop(xs) 


        def foldRight[B](acc: B)(f: (A, B) => B): B =
            xs match
                case Nil => acc
                case Cons(h, t) => f(h, t.foldRight(acc)(f))

        
        def length: Int =
            xs.foldRight(0)((i, acc) => acc + 1)

        
        @annotation.tailrec
        def foldLeft[B](acc: B)(f: (B, A) => B): B =
            xs match
                case Nil => acc
                case Cons(h, t) => t.foldLeft(f(acc, h))(f)


        def length2: Int =
            xs.foldLeft(0)((acc, i) => acc + 1)

        
        def reverse: List[A] =
            xs.foldLeft(Nil: List[A])((acc, i) => Cons(i, acc))
        
        
        def foldRightViaFoldLeft[B](acc: B)(f: (A, B) => B): B =
            xs.reverse.foldLeft(acc)((b, a) => f(a, b))


        def foldRightViaFoldLeft2[B](acc: B)(f: (A, B) => B): B =
            xs.foldLeft((b: B) => b)((g, a) => b => g(f(a, b)))(acc)

        
        def appendViaFoldRight(xs2: List[A]): List[A] =
            xs.foldRight(xs2)(Cons(_, _))

        
        



    def concat[A](xxs: List[List[A]]): List[A] =
        xxs.foldLeft(List[A]())((acc, i) => acc ++ i)


    extension(xs: List[Double])
        def doublesToStrings: List[String] =
            xs.foldRightViaFoldLeft(Nil: List[String])((i, acc) => Cons(i.toString, acc))
