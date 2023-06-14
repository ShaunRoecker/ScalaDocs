
package applicative2.list

import cats._
import cats.implicits._



object ListApplicative {

    val applicativeList: Applicative[List] = new Applicative[List] {
        override def pure[A](x: A): List[A] = List(x)

        override def ap[A, B](lf: List[A => B])(la: List[A]): List[B] = 
            (lf, la) match {
                case (f :: fs, a :: as) => (a :: as).fmap(f) ++ ap(fs)(a :: as)
                case _ => Nil
            }
            
        // map2 implementation not required, only pure and ap
        override def map2[A, B, C](la: List[A], lb: List[B])(f: (A, B) => C): List[C] = 
            ap(ap(pure(f.curried))(la))(lb)

    }
    
    // cartesian, which is List(1 + 4, 1 + 5, 2 + 4, 2 + 5, 3 + 4, 3 + 5) 
    println(applicativeList.map2(List(1, 2, 3), List(4, 5))(_ + _)) // List(5, 6, 6, 7, 7, 8)


    println(applicativeList.map2(List("a", "b", "c"), List("d", "e"))(_ + _)) // List(ad, ae, bd, be, cd, ce)



}



