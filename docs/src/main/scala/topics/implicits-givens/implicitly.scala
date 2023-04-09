package topics.implicitsgivens

object Implicitly {

    implicit val string: String = "string thingy"
    val implicitlyString = implicitly[String]
    println(implicitlyString) // string thingy 


}

