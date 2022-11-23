object Inheritance extends App {
  println("Inheritance and Traits")

  def partOne(): Unit = {
    // single class inheritance
      class Animal {
        val creatureType = "wild"
        protected def eat = println("nomnom") //if private, eat can't be extended, 
                                            // protected can be extended, but not used outside of child class
      }

      class Cat extends Animal {
          def crunch =
            eat   // <- calls the eat method inherited from the parent class Animal
            println("crunch crunch")     
      }

      val cat = new Cat
      // cat.eat // <- can't use eat in the wild, just in the class definition
      cat.crunch

      class Person(name: String, age: Int){

      }

      class Adult(name: String, age: Int, idCard: String) extends Person(name, age) {

      }
      // Note: you have to call the Person constructor before the Adult constructor

      class Dog (override val creatureType: String = "K9") extends Animal {
        // override val creatureType = "domestic"
        override def eat = println("dog-nomnom")
      }
      // with override, you can override fields from the SuperClass directly in the constructor

      val violet = new Dog()
      violet.eat //dog-nomnom
      println(violet.creatureType) //K9
  }

  def partTwo(): Unit = {
    


  }






  
}
