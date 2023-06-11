package typeclasses.byteencoderdecoder


object HelperMethods {

    trait ByteEncoder[A] {
        def encode(a: A): Array[Byte]
    }

    object ByteEncoder {
        implicit val stringByteEncoder: ByteEncoder[String] =
            instance[String](_.getBytes) 


        def apply[A](implicit ev: ByteEncoder[A]): ByteEncoder[A] = ev


        def instance[A](f: A => Array[Byte]): ByteEncoder[A] = new ByteEncoder[A] {
            override def encode(a: A): Array[Byte] = 
                f(a)
        }

    }


    implicit val rotate3StringEncoder: ByteEncoder[String] = 
        ByteEncoder.instance[String](_.getBytes.map(b => (b + 3).toByte))



    val res1 = ByteEncoder.stringByteEncoder.encode("hello")
    println(res1) // Array(104, 101, 108, 108, 111)


    val res2 = implicitly[ByteEncoder[String]].encode("hello")
    println(res2) // Array(107, 104, 111, 111, 114)

    val res3 = ByteEncoder[String].encode("hello")
    print(res3.toList) // Array(107, 104, 111, 111, 114)


}
