package typeclasses.catstypeclasses.monoid.example


import cats._
import cats.implicits._


final case class Speed(metersPerSec: Double) {
    def kilometersPerSec: Double = metersPerSec / 1000.0
    def milesPerSec: Double = metersPerSec / 1609.34
}

object Speed {
    def addSpeeds(s1: Speed, s2: Speed): Speed =
        Speed(s1.metersPerSec + s2.metersPerSec)


    implicit val monoidAddSpeed: Monoid[Speed] = 
        new Monoid[Speed] {
            override def empty: Speed = Speed(0.0)

            override def combine(x: Speed, y: Speed): Speed = 
                addSpeeds(x, y)
        }

    object Instances {
        // another way to write monoidAddSpeed:
        implicit val monoidAddSpeed2: Monoid[Speed] = 
            Monoid.instance(Speed(0d), addSpeeds)   // Monoid.instance("zero/empty", "combine function")

    }

    
}


object MonoidInstances {
    implicit val intAdditionMonoid: Monoid[Int] = 
        Monoid.instance[Int](0, _ + _)

    
    implicit val minIntMonoid: Monoid[Int] =
        new Monoid[Int] {
            override def empty: Int = Int.MaxValue

            override def combine(x: Int, y: Int): Int =  Math.min(x, y)
        }

    implicit val minIntMonoid2: Monoid[Int] =
        Monoid.instance[Int](Int.MaxValue, _ min _)  // <-- notice the zero value for Min Int is Int.MaxValue, not MinValue

    implicit def listMonoid[A]: Monoid[List[A]] = 
        new Monoid[List[A]] {
            def empty: List[A] = List[A]()

            def combine(x: List[A], y: List[A]): List[A] =
                x ++ y
        }

    implicit def listMonoid2[A]: Monoid[List[A]] = 
        Monoid.instance[List[A]](Nil, _ ++ _)



    implicit val stringConcatMonoid: Monoid[String] =
        Monoid.instance[String]("", _ ++ _)
    
}
object MonoidTest {
    import MonoidInstances.{intAdditionMonoid, minIntMonoid, listMonoid, stringConcatMonoid}

    println(Monoid[Speed].combine(Speed(100), Speed(200)))  // Speed(300.0)

    println(Monoid[Speed].empty)  // Speed(0.0)

    println(Monoid[Speed].combine(Speed(1000), Monoid[Speed].empty)) // Speed(1000.0)

    println(Speed(1000) |+| Speed(2000)) // Speed(3000.0)

    println(Monoid[Speed].combineAll(List(Speed(100d), Speed(200d), Speed(300d)))) // Speed(600.0)

    println(List(Speed(100d), Speed(200d), Speed(300d)).combineAll) // Speed(600.0)


    println(intAdditionMonoid.combine(2, 3)) // 5
    println(intAdditionMonoid.combineAll(List(1, 2, 3))) // 6

    println(minIntMonoid.combine(4, 9)) // 4
    println(minIntMonoid.combineAll(List(100, 800, 10000, 50, 600))) // 50

    println(listMonoid[String].combine(List("A"), List("B"))) // List(A, B)
    println(listMonoid[String].combineAll(List(List("A"), List("B"), List("C")))) // List(A, B, C)

    println(stringConcatMonoid.combine("Hello, ", "World!")) //  Hello, World!
    println(stringConcatMonoid.combineAll(List("Hello, ", "World", "!"))) // Hello, World!






    

    


}