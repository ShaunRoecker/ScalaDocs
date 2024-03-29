package testing.box

import cats._
import cats.implicits._


case class Box[A](value: A)


object Box {
    implicit def eqBox[A](implicit eqA: Eq[A]): Eq[Box[A]] = Eq.by(_.value)

    implicit val monadBox: Monad[Box] = new Monad[Box] {
        override def flatMap[A, B](fa: Box[A])(f: A => Box[B]): Box[B] = f(fa.value)

        override def pure[A](x: A): Box[A] = Box(x)

        override def tailRecM[A, B](a: A)(f: A => Box[Either[A,B]]): Box[B] = 
            f(a).value match {
                case Right(r) => Box(r)
                case Left(l) => tailRecM(a)(f)
            }

    }
}



