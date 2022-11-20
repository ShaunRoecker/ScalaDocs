object Objects extends App {
  println("Objects")
    // SCALA DOES NOT HAVE CLASS LEVEL FUNCTIONALITY ("static")
    object Person { // object is it's only type and it's only instance
        // "static"/"class" level functionality
        val N_EYES = 2
        def canFly: Boolean = false

        // factory method
        def apply(mother: Person, father: Person): Person = new Person("Booby")
        // 'factory method' because it's sole purpose is to build an instance of Person based of some params
        // this factory method is often called apply method
        // Often in practice we have factory methods in "sigleton objects"

    }
    class Person(val name: String) {
        // instance-level functionality
    }

    // This pattern of writing Objects and Classes with the same name
    // within the same scop is known as "COMPANIONS"

    // Companions Design Pattern

    println(Person.N_EYES)
    println(Person.canFly)

    // Scala Object == "SINGLETON INSTANCE"
    val person1 = Person
    val person2 = Person

    println(person1 == person2) //true, 
    // because there can only be one Person Object, 
    // that they are just pointing to the same place in memory 
    // and are not actually different




    // 
    val john = new Person("John")
    val mary = new Person("Mary")


    // this seems like a Constructor but it is just the apply method without the 'apply' (very common)
    val bobby = Person(john, mary)
    // same as: val bobby = Person.apply(john, mary)
    
    // Scala Applications = Scala object with this method implemented
    //          def main(args: Array[String]): Unit

    // Have to do this, or 
    // object Objects extends App {
    
    // }





}
