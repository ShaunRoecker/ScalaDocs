package language.features.types.hktypes

import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global



object HigherKindedTypes1:

    trait AHigherKindedType[F[_]]

    trait MyList[T]:
        def flatMap[B](f: T => B): MyList[B]


    trait MyOption[T]:
        def flatMap[B](f: T => B):  MyOption[B]


    trait MyFuture[T]:
        def flatMap[B](f: T => B):  MyOption[B]



    trait Monad[F[_], A]:
        def flatMap[B](f: A => F[B]): F[B]
        def map[B](f: A => B): F[B]


    case class MonadList[A](list: List[A]) extends Monad[List, A] { self =>
        override def flatMap[B](f: A => List[B]): List[B] = 
            list.foldLeft(List[B]()) {case (acc, i) => 
                acc ++ f(i)
            }
        override def map[B](f: A => B): List[B] = 
            list.foldLeft(List[B]()) {case (acc, i) => f(i) :: acc}
    }


    case class MonadOption[A](option: Option[A]) extends Monad[Option, A] { self =>
        override def flatMap[B](f: A => Option[B]): Option[B] = 
            option match
                case Some(a) => f(a)
                case None => None
            
        override def map[B](f: A => B): Option[B] = 
            option match
                case Some(a) => Some(f(a))
                case None => None         
    }

    
    def multiply[F[_], A, B](ma: Monad[F, A], mb: Monad[F, B]): F[(A, B)] =
        for {
            a <- ma        // ma.flatMap(a => mb.map(b => (a, b)))
            b <- mb       // <---
        } yield (a, b)

    
    