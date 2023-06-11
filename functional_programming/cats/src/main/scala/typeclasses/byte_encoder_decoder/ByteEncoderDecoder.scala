package typeclasses.byteencoderdecoder


import scala.util.Try
import java.nio.ByteBuffer


trait ByteEncoder[A] {
    def encode(a: A): Array[Byte]
}

object ByteEncoder {
    def apply[A](implicit ev: ByteEncoder[A]): ByteEncoder[A] = ev

    def instance[A](f: A => Array[Byte]): ByteEncoder[A] = new ByteEncoder[A] {
        override def encode(a: A): Array[Byte] = f(a)
    }

    implicit val stringByteEncoder: ByteEncoder[String] = 
        ByteEncoder.instance[String](_.getBytes)

}


object ByteEncoderImplicits {
    implicit object StringByteEncoder extends ByteEncoder[String] {
            override def encode(s: String): Array[Byte] = s.getBytes
        }

    implicit object IntEncoder extends ByteEncoder[Int] {
        override def encode(i: Int): Array[Byte] = {
            val bb = ByteBuffer.allocate(4)
            bb.putInt(i)
            bb.array()
        }
    }

    // To implement a ByteCodec for Option[String] the naive method would be...

    // implicit object OptionString extends ByteEncoder[Option[String]] {
    //     override def encode(a: Option[String]): Array[Byte] = {
    //         a match {
    //             case Some(value) => StringByteEncoder.encode(value)
    //             case None => Array[Byte]()
    //         }
    //     }     
    // }

    // implicit object OptionInt extends ByteEncoder[Option[Int]] {
    //     override def encode(opt: Option[Int]): Array[Byte] = {
    //         opt match {
    //             case Some(value) => IntEncoder.encode(value)
    //             case None => Array[Byte]()
    //         }
    //     }
    // }

    implicit def optionEncoder[A](implicit encA: ByteEncoder[A]): ByteEncoder[Option[A]] = new ByteEncoder[Option[A]] {
        override def encode(a: Option[A]): Array[Byte] = {
            a match {
                case Some(value) => encA.encode(value)
                case None => Array[Byte]()
            }
        }
    }
}




trait ByteDecoder[A] {
    def decode(bytes: Array[Byte]): Option[A]
}

object ByteDecoder {
    def apply[A](implicit ev: ByteDecoder[A]): ByteDecoder[A] = ev

    def instance[A](f: Array[Byte] => Option[A]): ByteDecoder[A] = new ByteDecoder[A] {
        override def decode(bytes: Array[Byte]): Option[A] = f(bytes)
    }

    implicit val stringByteDecoder: ByteDecoder[String] =
        ByteDecoder.instance[String](bytes => Some(bytes.map(_.toChar).mkString))
        
}

object ByteDecoderImplicits {
    implicit object StringDecoder extends ByteDecoder[String] {
        override def decode(bytes: Array[Byte]): Option[String] = 
            Try(new String(bytes)).toOption
    }

    implicit object IntDecoder extends ByteDecoder[Int] {
        override def decode(bytes: Array[Byte]): Option[Int] = {
            if (bytes.length != 4) None
            else {
                val bb = ByteBuffer.allocate(4)
                bb.put(bytes)
                bb.flip()
                Some(bb.getInt())
            }
        }
    }

}


import ByteDecoderImplicits._
import ByteEncoderImplicits._


object ByteEncodeDecodeRun extends App {


    implicit class ByteEncoderOps[A](val a: A) extends AnyVal {
        def encode(implicit enc: ByteEncoder[A]): Array[Byte] =
            enc.encode(a)
    }

    implicit class ByteDecoderOps[A](bytes: Array[Byte]) {
        def decode(implicit dec: ByteDecoder[A]): Option[A] = {
            dec.decode(bytes)
        }
    }

    println("hello")

    val encoded = ByteEncoder[String].encode("a string")

    println(encoded) // B@34340fab

    val decoded = ByteDecoder[String].decode(encoded)

    println(decoded) // a string

    val encodeOptionString = ByteEncoder[Option[String]].encode(Option("hello option"))
    println(encodeOptionString) // Array(104, 101, 108, 108, 111, 32, 111, 112, 116, 105, 111, 110)

    val encodeNone = ByteEncoder[Option[String]].encode(None)
    println(encodeNone) // Array()

    val intEnc = ByteEncoder[Int].encode(235325454)
    println(intEnc) // Array(14, 6, -56, 14)

    val optIntEnc = ByteEncoder[Option[Int]].encode(Option(48293))
    println(optIntEnc) // Array(0, 0, -68, -91)

    val optIntEncNone = ByteEncoder[Option[Int]].encode(None)
    println(optIntEncNone) // Array()

    println(Option(1000).encode) // Array(0, 0, 3, -24)

    val byteArray = Option(42).encode


}