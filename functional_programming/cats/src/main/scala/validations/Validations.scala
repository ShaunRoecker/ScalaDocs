package validations


import cats._
import cats.implicits._
import cats.data._


object ValidatedRun {

    class Validated[+E, +A]

    case class Valid[+A](a: A) extends Validated[Nothing, A]
    case class Invalid[+E](e: E) extends Validated[E, Nothing]

    case class Person(name: String, age: Int)

    type IsValid[A] = Validated[List[String], A]

    def validateAge(age: Int): IsValid[Int] = ???

    def validateName(name: String): IsValid[String] = ???

    // def validatePerson(person: Person): IsValid[Person] =
    //     (validateName(person.name), validateAge(person.age)).mapN((n, a) => Person(n, a))

    // How .valid works is you have your value - 5 and you provide the error type
    println(5.valid[String]) // Valid(5)

    // However if you accumulate errors it will concat the strings, and that would
    // be awkward, so you can use the Cats NoneEmptyList type so if errors are 
    // accumulated you have them in a list
    println(5.valid[NonEmptyList[String]]) // Valid(5)

    // .validNel is short for .valid[NonEmptyList[String]]
    println(5.validNel[String])


    println("error".invalid[Int]) // Invalid(error)
    println("error".invalidNel[Int]) // Invalid(NonEmptyList(error))


    // The problem with using lists to accumulate errors is that concatenation is
    // a bit slow
    def concat[A](as: List[A], as2: List[A]): List[A] = {
        // as match {
        //     case Nil => as2
        //     case x :: xs => x :: concat(xs, as2)
        // }
        as.foldRight(as2) { case (a, b) => a :: b} // <- linear
    }

    // In cases such as this we pay a big price just to add one element
    println(concat(List(1, 2, 3, 4, 5, 6, 7, 8), List(9)))

    // .validNec is like .validNel but it has a faster concatenation
    val validNec1 = 5.validNec[String]
    println(validNec1) // Valid(5)

    val invalidNec = "error".invalidNec[Int]
    println(invalidNec) // Invalid(Chain(error))


    println(5.validNec[String].ensure(NonEmptyChain("number is not even"))(_ % 2 == 0))
    // Invalid(Chain(number is not even))

    println(6.validNec[String].ensure(NonEmptyChain("number is not even"))(_ % 2 == 0))
    // Valid(6)

    println(Validated.cond(test=true, 5, "error")) // Invalid(error)
    println(Validated.condNec(test=false, 5, "error")) // Invalid(Chain(error))


    println(5.validNec[String].getOrElse(10))  // 5

    "error".invalidNec[Int].getOrElse(10) // 10

    println(5.validNec[String].orElse(10.validNec[String])) // Valid(5)

    println("error".invalidNec[Int].orElse(10.validNec[String])) // Valid(10)

    println(5.validNec[String].toEither) // Right(5)

    println(Validated.fromEither[NonEmptyChain[String], Int](Right(5)))


}

