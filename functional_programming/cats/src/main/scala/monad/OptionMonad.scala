package monad.option

import cats._
import cats.implicits._


sealed trait MOption[+A]

object MOption { self =>
    case class MSome[+A](get: A) extends MOption[A]
    case object MNone extends MOption[Nothing]

    implicit val monadOption: Monad[MOption] = new Monad[MOption] {
        override def pure[A](x: A): MOption[A] = // similar to lift
            MSome(x)
            

        override def flatMap[A, B](fa: MOption[A])(f: A => MOption[B]): MOption[B] =
            fa match {
                case MSome(value) => f(value)
                case MNone => MNone
            }

        override def tailRecM[A, B](a: A)(f: A => MOption[Either[A,B]]): MOption[B] = ??? 


        override def map[A, B](fa: MOption[A])(f: A => B): MOption[B] = 
            // fa match {
            //     case MSome(value) => MSome(f(value))
            //     case MNone => MNone
            // }
            flatMap(fa)(a => pure(f(a)))


        override def flatten[A](ffa: MOption[MOption[A]]): MOption[A] = 
                flatMap(ffa)(identity)

    }

}


object MonadRun {
    import MOption._

    val x: MOption[Int] = Monad[MOption].pure(5)
    println(x) // MSome(5)

    val y: MOption[Int] = Monad[MOption].pure(6).flatMap(i => Monad[MOption].pure(i + 1))
    println(y) // MSome(7)


    val z: MOption[Int] = 
        for {
            a <- Monad[MOption].pure(5)
            b <- Monad[MOption].pure(5)
        } yield a + b

    println(z) // MSome(10)

    val none = MNone
    println(none) // MNone


    val ffa: MOption[MOption[Int]] = Monad[MOption].pure(Monad[MOption].pure(5))
    println(ffa) //  MSome(MSome(5))

    val flattenedFfa = ffa.flatten
    println(flattenedFfa) // MSome(5)




}