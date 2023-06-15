package monad.list


import cats._
import cats.implicits._



object ListMonad {
    val result =
        for {
            a <- List(1, 2, 3)
            b <- List(4, 5, 6)
        } yield a + b


    println(result) // List(5, 6, 7, 6, 7, 8, 7, 8, 9)


    val listMonad: Monad[List] = new Monad[List] {

        override def pure[A](x: A): List[A] = List(x)


        override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] =
            fa match {
                case Nil => Nil
                case hd :: tl => f(hd) ::: flatMap(tl)(f)
            }


        override def tailRecM[A, B](a: A)(f: A => List[Either[A,B]]): List[B] = ???


        override def map[A, B](fa: List[A])(f: A => B): List[B] =
            flatMap(fa)(a => pure(f(a)))

    }

}
