package monad.Either

import cats._
import cats.implicits._



object EitherMonad {

    implicit def eitherMonad[E]: Monad[Either[E, *]] = new Monad[Either[E, *]] {
        override def pure[A](x: A): Either[E,A] = Right(x)

        override def flatMap[A, B](fa: Either[E,A])(f: A => Either[E,B]): Either[E,B] = 
            fa match {
                case Right(a) => f(a)
                case Left(e) => Left(e)  
            }

        override def tailRecM[A, B](a: A)(f: A => Either[E,Either[A,B]]): Either[E,B] = ???

    }


    val x = 5.asRight[String].flatMap(i => (i + 1).asRight[String])

    val xFor = 
        for {
            x <- 5.asRight[String]
        } yield x + 1

    
    val y = 5.asRight[String].flatMap(i => "boom".asLeft[Int].flatMap(j => "boom 2".asLeft[Int]))

    val yFor =
        for {
            x <- 5.asRight[String]
            y <- "boom".asLeft[Int]
            z <- "boom 2".asLeft[Int]
        } yield x

    println(x)    // Right(6)
    println(xFor) // Right(6)

    println(y) // Left(boom)
    println(yFor) // Left(boom)


}


