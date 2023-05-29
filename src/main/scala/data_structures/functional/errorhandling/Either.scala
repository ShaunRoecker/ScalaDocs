package datastructures.functional.errorhandling

import scala.{Either as _, Left as _, Right as _}
import scala.util.control.NonFatal

// sealed trait Either[+E, +A] extends Product with Serializable
// case class Left[+E](value: E) extends Either[E, Nothing]
// case class Right[+A](value: A) extends Either[Nothing, A]

// object Either {

// }

enum Either[+E, +A] extends Product with Serializable { self =>
    case Left(value: E)
    case Right(value: A)


    def map[B](f: A => B): Either[E, B] = 
        self match
            case Right(a) => Right(f(a))
            case Left(e) => Left(e)


    def map2[EE >: E, B, C](that: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
        for
            a <- self
            b <- that
        yield f(a, b)

    
    def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] =
        self match
            case Right(a) => f(a)
            case Left(e) => Left(e)

    
    def orElse[EE >: E, B >: A](b: => Either[EE, B]): Either[EE, B]  =
        self match
            case Left(_) => b
            case Right(a) => Right(a)


}

import Either._

extension(xs: IndexedSeq[Double])
    def mean: Either[String, Double] =
        if (xs.isEmpty)
            Left("mean of empty list!")
        else
            Right(xs.sum / xs.length)


extension(n: Int)
    def safeDiv(m: Int): Either[Exception, Int] =
        try Right(n / m)
        catch { case e: Exception => Left(e) }

extension[A](a: => A)
    def attempt: Either[Exception, A] =
        try Right(a)
        catch { case e: Exception => Left(e) }



extension[A](as: List[A])
    def traverse[E, B](f: A => Either[E, B]): Either[E, List[B]] =
        as match
            case Nil => Right(Nil)
            case h :: t => (f(h).map2(t.traverse(f)))(_ :: _)

    def traverse2[E, B](f: A => Either[E, B]): Either[E, List[B]] =
        as.foldRight(Right(Nil): Either[E, List[B]])((a, b) => f(a).map2(b)(_ :: _))


extension[E, A](es: List[Either[E, A]])
    def sequence: Either[E, List[A]] =
        es.traverse(identity)

