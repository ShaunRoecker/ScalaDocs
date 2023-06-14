package applicative.fp

import cats._
import cats.implicits._



sealed trait Validated[+A]

object Validated { self =>

    case class Valid[+A](a: A) extends Validated[A]
    case class Invalid(errors: List[String]) extends Validated[Nothing]

    implicit val applicative: Applicative[Validated] = new Applicative[Validated] {
        override def pure[A](x: A): Validated[A] = Valid(x)

        override def ap[A, B](vf: Validated[A => B])(va: Validated[A]): Validated[B] = 
            (vf, va) match {
                case (Valid(f), Valid(a)) => Valid(f(a))
                case (Invalid(e1), Valid(a)) => Invalid(e1)
                case (Valid(f), Invalid(e2)) => Invalid(e2)
                case (Invalid(e1), Invalid(e2)) => Invalid(e1 ++ e2)
            }
            // map2(vf, va)((f, a) => f(a))


        // override def map[A, B](va: Validated[A])(f: A => B): Validated[B]

        override def map2[A, B, C](va: Validated[A], vb: Validated[B])(f: (A, B) => C): Validated[C] = 
            // (va, vb) match {
            //     case (Valid(a), Valid(b)) => Valid(f(a, b))
            //     case (Invalid(e1), Valid(b)) => Invalid(e1)
            //     case (Valid(a), Invalid(e2)) => Invalid(e2)
            //     case (Invalid(e1), Invalid(e2)) => Invalid(e1 ++ e2)
            // }
            // val g: A => B => C = f.curried
            ap(ap(pure(f.curried))(va))(vb)

        
        // override def map3[A, B, C, D](va: Validated[A], vb: Validated[B], vc: Validated[C])(f: (A, B, C) => D): Validated[D]
        
    }

    def tuple[A, B](va: Validated[A], vb: Validated[B]): Validated[(A, B)] = 
        Applicative[Validated].map2(va, vb)((a, b) => (a, b))


}

object Applicatives {
    import Validated._

    def validateName(name: String): Validated[String] = {
        if (name.forall(_.isLetter)) Valid(name)
        else Invalid(List("name can only contain letters"))
    }

    def validateAge(age: Int): Validated[Int] = {
        if (age < 18) Invalid(List("age must be at least 18"))
        else Valid(age)
    }


    final case class Person(name: String, age: Int)

    def validatePerson(person: Person): Validated[Person] = 
        (validateName(person.name), validateAge(person.age)) match {
            case (Valid(n), Valid(a)) => Valid(Person(n, a))
            case (Invalid(e1), Valid(a)) => Invalid(e1)
            case (Valid(n), Invalid(e2)) => Invalid(e2)
            case (Invalid(e1), Invalid(e2)) => Invalid(e1 ++ e2)
        }
    

    def map2[A, B, C](va: Validated[A], vb: Validated[B])(f: (A, B) => C): Validated[C] =
        (va, vb) match {
            case (Valid(a), Valid(b)) => Valid(f(a, b))
            case (Invalid(e1), Valid(b)) => Invalid(e1)
            case (Valid(a), Invalid(e2)) => Invalid(e2)
            case (Invalid(e1), Invalid(e2)) => Invalid(e1 ++ e2)
        }



    //  Using mapN

    val v1: Validated[Int] = Applicative[Validated].pure(1)
    val v2: Validated[Int] = Applicative[Validated].pure(2)
    val v3: Validated[Int] = Applicative[Validated].pure(3)

    println((v1, v2, v3).mapN((a, b, c) => a + b + c))

    println(v1)

    // val applicativeOption: Applicative[Option] = new Applicative[Option] {
    //     override def pure[A](x: A): Option[A] = Some(x)

    //     override def ap[A, B](of: Option[A => B])(oa: Option[A]): Option[B] = 
    //         (of, oa) match {
    //             case (Some(f), Some(a)) => Some(f(a))
    //             case _ => None
    //         }
            
    //     override def map2[A, B, C](oa: Option[A], ob: Option[B])(f: (A, B) => C): Option[C] = 
    //         ap(ap(pure(f.curried))(oa))(ob)

    // }



}
