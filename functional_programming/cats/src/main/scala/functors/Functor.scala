package functors


import cats._
import cats.implicits._
import java.nio.charset.StandardCharsets
import java.security.MessageDigest



class Secret[A](val value: A) {
  private def hashed: String = {  // <- hashing algorithm
    val s = value.toString
    val bytes = s.getBytes(StandardCharsets.UTF_8)
    val d = MessageDigest.getInstance("SHA-1")
    val hashBytes = d.digest(bytes)
    new String(hashBytes, StandardCharsets.UTF_8)
  }

  override def toString: String = hashed
}


object Secret {
    implicit val secretFunctor = new Functor[Secret] {
        override def map[A, B](fa: Secret[A])(f: A => B): Secret[B] = 
            new Secret(f(fa.value))
    }
}



object Functors {

    case class Person(name: Secret[String])

    val person = Person(new Secret("John"))


    val leandroSecret: Secret[String] = new Secret("leandro")
    println(leandroSecret) // ��q2d�<���Kl��o�D�@
    println(leandroSecret.value) // leandro


    val upperLeandroSecret = 
        Functor[Secret].map(leandroSecret)(_.toUpperCase)

    println(upperLeandroSecret) // 2�ů���l�D�q���r[
    println(upperLeandroSecret.value) // LEANDRO


    val optionFunctor: Functor[Option] = new Functor[Option] {
        override def map[A, B](fa: Option[A])(f: A => B): Option[B] = 
            fa match {
                case Some(value) => Some(f(value))
                case None => None
            }
    }

    val listFunctor: Functor[List] = new Functor[List] {    
        override def map[A, B](fa: List[A])(f: A => B): List[B] = 
            fa match {
                case Nil => Nil
                case x :: xs => f(x) :: map(xs)(f)
            }
    }


    val option = Some(1)
    val mappedOption = optionFunctor.map(option)(_ + 1)
    println(mappedOption) // Some(2)
    // as
    println(optionFunctor.as(mappedOption, 42))  // Some(42)


    val list = List(1, 2, 3, 4)
    val mappedList = listFunctor.map(list)(n => n * n)
    println(mappedList) // List(1, 4, 9, 16)
    // as
    println(listFunctor.as(mappedList, 10)) // List(10, 10, 10, 10)



}
