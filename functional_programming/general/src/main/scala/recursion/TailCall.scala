package functional.recursion.tailcall

import scala.util.control.TailCalls._


// Scala 2 way to create Product type ADTs

//   sealed trait Bounce[A]
//   case class Done[A](result: A) extends Bounce[A]
//   case class Call[A](nextFunc: () => Bounce[A]) extends Bounce[A]


// Scala 3 way to create Product type ADTs

enum Bounce[A] { self =>
    case Done(result: A)
    case Call(nextFunc: () => Bounce[A])

    @annotation.tailrec
    final def trampoline: A =
        self match
            case Call(next) => next().trampoline
            case Done(a) => a

}


object SimpleMethods:
    import Bounce._

    def even(n: Int): Bounce[Boolean] =
        n match
            case 0 => Done(true)
            case x => Call(() => odd(x - 1))

    def odd(n: Int): Bounce[Boolean] =
        n match
            case 0 => Done(false)
            case x => Call(() => even(x - 1))
    



object SimpleMethodsTailCall:

    def eventc(n: Int): TailRec[Boolean] =
        n match
            case 0 => done(true)
            case x => tailcall(oddtc(x - 1))

    def oddtc(n: Int): TailRec[Boolean] =
        n match
            case 0 => done(false)
            case x => tailcall(eventc(x - 1))
    
