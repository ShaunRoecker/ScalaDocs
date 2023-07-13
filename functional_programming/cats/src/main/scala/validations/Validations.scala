package validations


import cats._
import cats.implicits._
import cats.data._


object ValidatedRun extends App {

    class Validated[+E, +A]

    case class Valid[+A](a: A) extends Validated[Nothing, A]
    case class Invalid[+E](e: E) extends Validated[E, Nothing]

    case class Person(name: String, age: Int)

    type IsValid[A] = Validated[List[String], A]

    def validateAge(age: Int): IsValid[Int] = ???

    def validateName(name: String): IsValid[String] = ???

    // def validatePerson(person: Person): IsValid[Person] =
    //     (validateName(person.name), validateAge(person.age)).mapN((n, a) => Person(n, a))

    println(5.valid[String]) // Valid(5)

    println(5.valid[NonEmptyList[String]])
    println(5.validNel[String])

    println("error".invalid[Int]) // Invalid(error)


    def concat[A](as: List[A], as2: List[A]): List[A] = {
        // as match {
        //     case Nil => as2
        //     case x :: xs => x :: concat(xs, as2)
        // }
        as.foldRight(as2) { case (a, b) => a :: b}
    }

    println(concat(List(1, 2, 3), List(4)))
 

}

