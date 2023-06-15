package m.option



sealed trait MOption[+A]

object MOption { self =>
    case class MSome[+A](get: A) extends MOption[A]
    case object MNone extends MOption[Nothing]

    implicit class MOptionOps[A](val option: MOption[A]) {

        def flatMap[B](f: A => MOption[B]): MOption[B] =
            option match {
                case MSome(value) => f(value)
                case MNone => MNone
            }

        def map[B](f: A => B): MOption[B] = 
            option match {
                case MSome(value) => MSome(f(value))
                case MNone => MNone
            }

        

    }

}






