package topics.implicitsgivens

object Implicitly {
    // implicitly

    implicit val string: String = "string thingy"
    val implicitlyString = implicitly[String]
    println(implicitlyString) // string thingy 


}

