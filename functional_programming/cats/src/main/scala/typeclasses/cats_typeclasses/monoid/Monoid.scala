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


object MonoidTest extends App {

    println(Monoid[Speed].combine(Speed(100), Speed(200)))  // Speed(300.0)

    println(Monoid[Speed].empty)  // Speed(0.0)

    println(Monoid[Speed].combine(Speed(1000), Monoid[Speed].empty)) // Speed(1000.0)

    println(Speed(1000) |+| Speed(2000)) // Speed(3000.0)

    println(Monoid[Speed].combineAll(List(Speed(100d), Speed(200d), Speed(300d)))) // Speed(600.0)

    println(List(Speed(100d), Speed(200d), Speed(300d)).combineAll) // Speed(600.0)


}