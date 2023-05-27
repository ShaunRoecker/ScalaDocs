package topics.implicitsgivens

object Implicitly {

    // Implicitly can be used to grab any implicit values defined in scope
    implicit val string: String = "string thingy"
    val implicitlyString = implicitly[String]
    println(implicitlyString) // string thingy 

    // val implicitlyInt = implicitly[Int]   // <-- doesn't compile

    class Animal
    class Dog extends Animal

    // We can also use implicitly to determine if one type is a subtype
    // of another, which if the hierachy stated is true, the program
    // will compile as normal and if false, the program will not compile.

    implicitly[Dog <:< Animal]  // <- compiles

    // implicitly[Animal <:< Dog]  // <- doesn't compile



}

