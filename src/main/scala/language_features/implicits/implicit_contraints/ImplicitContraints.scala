package language.features.implicits.contraints


import scala.annotation.implicitNotFound



trait Food { def name: String }

case class Fruit(name: String) extends Food
case class Cereal(name: String) extends Food
case class Meat(name: String) extends Food

trait Eater { def name: String }

case class Vegan(name: String) extends Eater
case class Vegetarian(name: String) extends Eater
case class Paleo(name: String) extends Eater




@implicitNotFound(msg="Illegal Feeding: No Eats rule from ${E} to ${F}")
trait Eats[E <: Eater, F <: Food] {
    def feed(eater: E, food: F): String =
        s"${eater.name} eats ${food.name}"
}


object Eats {
    
    implicit object fruitVegan extends Eats[Vegan, Fruit] {
        override def feed(eater: Vegan, food: Fruit): String = 
            s"This vegan ${eater.name} only eats ${food.name}s"
    }

}


trait Eats3[E <: Eater, F <: Food]:
    extension(eater: E)
        def feed3(food: F): String = 
            s"${eater.name} eats ${food.name} while programming in Scala3"


object Eats3:

    given Eats3[Vegan, Fruit] with
        extension(vegan: Vegan)
            override def feed3(fruit: Fruit): String = 
                s"\nThis vegan ${vegan.name}," +
                  s"\nonly eats ${fruit.name}," +
                    "\nwhile programming in Scala3" 




// restrictive feedTo method that requires implicit (ev)idence this rule exists
def feedTo[E <: Eater, F <: Food](eater: E, food: F)(implicit ev: Eats[E, F]): String =
    ev.feed(eater, food)


def feedTo3[E <: Eater, F <: Food](eater: E, food: F)(using Eats3[E, F]): String =
    eater.feed3(food)




val apple = Fruit("Apple")
val oatmeal = Cereal("Oatmeal")
val beef = Meat("Beef")


val alice = Vegan("Alice")
val bob = Vegetarian("Bob")
val charlie = Paleo("Charlie")


object ImplicitContraints:
    // import Eats.fruitVegan
    def implConRun(): Unit =
        println("Implicit Constraints")

        // If ran without creating an implimentation of Eats[Vegan]...
        // feedTo[Fruit, Vegan](apple, alice)
        // 
        // Illegal Feeding: No Eats rule from language.features.implicits.contraints.Vegan to... 
        //    language.features.implicits.contraints.Fruit

        // once implimented...
        println(feedTo(alice, apple))
        //This vegan Alice only eats Apples


        // Scala3 version
        println(feedTo3(alice, apple))

