package monad

import cats._
import cats.implicits._
import scala.util._

object TryMonad {
    implicit val tryMonad: Monad[Try] = new Monad[Try] {
        override def pure[A](x: A): Try[A] = Success(x)

        override def flatMap[A, B](fa: Try[A])(f: A => Try[B]): Try[B] =
            fa match {
                case Success(a) => f(a)
                case Failure(e) => Failure(e)
            }

        override def tailRecM[A, B](a: A)(f: A => Try[Either[A, B]]): Try[B] = ???
    }

}



object TryMonadRun {
    import TryMonad._

    val tm1 = tryMonad.pure(5)
    println(tm1) // Success(5)
    val tm2 = tryMonad.pure(5).flatMap(i => tryMonad.pure(i + 5))
    println(tm2) // Success(10)

    val tm3 = tryMonad.pure(5).flatMap(i => Failure(new Exception("boom")))
    println(tm3) // Failure(java.lang.Exception: boom)

    val tm4 = 
        tryMonad.pure(5)
            .flatMap((i: Int) => Failure(new Exception("boom")))
            .flatMap((j: Int) => Failure(new Exception("boom2")))

    println(tm4) // Failure(java.lang.Exception: boom)
    // We lose data with the Try Monad, as you can see we only preserve
    // the first failure in 'fail fast' fashion

    
    

}