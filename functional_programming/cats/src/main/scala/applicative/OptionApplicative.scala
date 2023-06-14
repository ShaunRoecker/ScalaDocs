package applicative2.option

import cats._
import cats.implicits._



object OptionApplicative {

    val applicativeOption: Applicative[Option] = new Applicative[Option] {
        override def pure[A](x: A): Option[A] = Some(x)

        override def ap[A, B](of: Option[A => B])(oa: Option[A]): Option[B] = 
            (of, oa) match {
                case (Some(f), Some(a)) => Some(f(a))
                case _ => None
            }
            
        // map2 implementation not required, only pure and ap
        override def map2[A, B, C](oa: Option[A], ob: Option[B])(f: (A, B) => C): Option[C] = 
            ap(ap(pure(f.curried))(oa))(ob)

    }
    
    println(applicativeOption.map2(Some(3), Some(4))(_ + _)) // Some(7)



}


