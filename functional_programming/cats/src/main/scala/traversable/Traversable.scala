package traversable

import foldable.MList
import foldable.MList._
import cats._
import cats.implicits._


object TraversableRun extends App {
    case class Person(name: String)

    def findPersonByName(name: String): Option[Person] = {
        // dummy function
        val person = Person("John")
        if (person.name == name) Some(person) else None
    }

    def findPeopleByNames(names: MList[String]): Option[MList[Person]] = 
        names match {
            case MNil => Some(MNil)
            case MCons(h, t) => 
                (findPersonByName(h), findPeopleByNames(t)).mapN(MCons.apply)
        }

    // List[Option] => Option[List]
    def traverse[F[_]: Applicative, A, B](as: MList[A])(f: A => F[B]): F[MList[B]] = 
        as match {
            case MNil => Applicative[F].pure(MNil)
            case MCons(h, t) => (f(h), traverse(t)(f)).mapN(MCons.apply)
        }


}


