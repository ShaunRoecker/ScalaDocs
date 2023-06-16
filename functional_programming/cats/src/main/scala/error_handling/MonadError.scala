package errorhandling.monaderror

import java.io.IOException
import scala.util._
import cats._
import cats.implicits._



trait HttpMethod
case object GET extends HttpMethod
case class HttpRequest(method: HttpMethod, url: String)
case class HttpResponse(status: Int)


object ErrorHandling {

    def doRequest(req: HttpRequest): HttpResponse =
        if (math.random() < 0.5) throw new IOException("boom")
        else HttpResponse(200)


    def executeRequest(req: HttpRequest): Option[HttpResponse] =
        try {
            Some(doRequest(req))
        } catch {
            case _: Exception => None
        }

    def executeRequest2(req: HttpRequest): Either[String, HttpResponse] =
        try {
            Right(doRequest(req))
        } catch {
            case e: Exception => Left(e.getMessage())
        }

    def executeRequest3(req: HttpRequest): Try[HttpResponse] =
        try {
            Success(doRequest(req))
        } catch {
            case e: Exception => Failure(e)
        }

    // MonadError version
        // MonadError can lead to a more generic version of our methods
    def executeRequestME[F[_], E](req: HttpRequest)(f: Exception => E)(implicit ev: MonadError[F, E]): F[HttpResponse] = 
        try {
            ev.pure(doRequest(req))
        } catch {
            case e: Exception => ev.raiseError(f(e))
        }

}

object MonadErrorInstance {

    implicit val optionME: MonadError[Option, Unit] = new MonadError[Option, Unit] {

        def pure[A](x: A): Option[A] = Some(x)
        def raiseError[A](e: Unit): Option[A] = None

        def handleErrorWith[A](fa: Option[A])(f: Unit => Option[A]): Option[A] = 
            // fa match {
            //     case Some(a) => Some(a)
            //     case None => f(())
            // }
            fa.orElse(f(()))

        def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = ???

        def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] = ???


    }


    // Either[E, *]: * -> *  allows us to leave a hole for the type (from "kind-projector" plugin)
    implicit def eitherME[E]: MonadError[Either[E, *], E] = new MonadError[Either[E, *], E] {

        def pure[A](x: A): Either[E, A] = Right(x)
        def raiseError[A](e: E): Either[E, A] = Left(e) 

        def handleErrorWith[A](fa: Either[E, A])(f: E => Either[E, A]): Either[E, A] = 
            fa match {
                case Right(a) => Right(a)
                case Left(e) => f(e)
            }


        def flatMap[A, B](fa: Either[E, A])(f: A => Either[E, B]): Either[E, B] = ???

        def tailRecM[A, B](a: A)(f: A => Either[E, Either[A, B]]): Either[E, B] = ???
    }


    implicit val tryME: MonadError[Try, Throwable] = new MonadError[Try, Throwable] {


        def pure[A](x: A): Try[A] = Success(x)

        def raiseError[A](e: Throwable): Try[A] = Failure(e)

        def handleErrorWith[A](fa: Try[A])(f: Throwable => Try[A]): Try[A] = 
            fa match {
                case Success(a) => Success(a)
                case Failure(e) => f(e)
            }

        def flatMap[A, B](fa: Try[A])(f: A => Try[B]): Try[B] = ???

        def tailRecM[A, B](a: A)(f: A => Try[Either[A, B]]): Try[B] = ???
    }
}

object ErrorHandlingRun {
    import ErrorHandling._
    import MonadErrorInstance._

    println(executeRequest3(HttpRequest(GET, "wwf.mmrsavage.com"))) // Some(HttpResponse(200))


    type ErrorOr[A] = Either[String, A]
    println(executeRequestME[ErrorOr, String](HttpRequest(GET, "wwf.mmrsavage.com"))((e: Exception) => e.getMessage)) 
    // Left(java.io.IOException: boom) / Right(HttpResponse(200))


    println(executeRequestME[Option, Unit](HttpRequest(GET, "wwf.mmrsavage.com"))((e: Exception) => ())) 


    // With MonadError.attempt it will always return the success type for the
    // error handling monad, but it will be wrapped in an Either inside that type.
    val meS = MonadError[Option, Unit].attempt(Some(5))  // <- will always return Some()
    println(meS) // Some(Right(5))

    val meF = MonadError[Option, Unit].attempt(None)  // <- will always return Some()
    println(meF) // Some(Left(()))  


    val meT = MonadError[Try, Throwable].attempt(Failure(new Exception("boom")))
    println(meT)


    // with MonadError.ensure, you provide an error value, and a predicate,
    // if the predicate is satisfied, it provides the value, if the predicate
    // is not satisfied, it will return the error value wrapped in the 
    // failure type monad
    val ensure1 = MonadError[Option, Unit].ensure(Some(3))(())(_ % 2 == 0)
    println(ensure1) // None

    val ensure2 = MonadError[Option, Unit].ensure(Some(4))(())(_ % 2 == 0)
    println(ensure2) // Some(4)

    val ensure3 = MonadError[ErrorOr, String].ensure(Right(3))("boom")(_ % 2 == 0)
    println(ensure3) // Left(boom)

    val ensure4 = MonadError[ErrorOr, String].ensure(Right(4))("boom")(_ % 2 == 0)
    println(ensure4) // Right(4)




}


