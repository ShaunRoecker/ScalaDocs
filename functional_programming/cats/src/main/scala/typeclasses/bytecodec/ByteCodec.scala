package typeclasses.bytecodec

import typeclasses.byteencoderdecoder.{ByteEncoder, ByteDecoder}
import java.nio.ByteBuffer
import scala.util.Try


// The ByteCodec typeclasses is a combination of the
// ByteEncoder and ByteDecoder typeclasses

trait ByteCodec[A] extends ByteEncoder[A] with ByteDecoder[A]

// Laws for ByteCodec
    // Property: encoding and then decoding a value should
    //  return the original value unchanged

object ByteCodec {
    def apply[A](implicit ev: ByteCodec[A]): ByteCodec[A] = ev
}

trait ByteCodecLaws[A] {
    def codec: ByteCodec[A]

    def isomorphism[A](a: A)(implicit codec: ByteCodec[A]): Boolean = {
        codec.decode(codec.encode(a)) == Some(a)
    }
}

object ByteCodecLaws {
    def apply[A](implicit ev: ByteCodec[A]): ByteCodecLaws[A] = new ByteCodecLaws[A] {
        override def codec: ByteCodec[A] = ev
    }
}


object ByteCodecImplicits {
    implicit object IntByteCodec extends ByteCodec[Int] {
        override def decode(bytes: Array[Byte]): Option[Int] = {
            if(bytes.length != 4) None
            else {
                val bb = ByteBuffer.allocate(4)
                bb.put(bytes)
                bb.flip()
                Some(bb.getInt)
            }
        }

        override def encode(o: Int): Array[Byte] = {
            val bb = ByteBuffer.allocate(4)
            bb.putInt(o)
            bb.array()
        }
    }

    implicit object StringByteCodec extends ByteCodec[String] {
        override def decode(bytes: Array[Byte]): Option[String] = {
            // Some(bytes.map(_.toChar).mkString)
            Try(new String(bytes)).toOption
        }

        override def encode(s: String): Array[Byte] = 
            s.getBytes

    }

}
import ByteCodecImplicits._


object ByteCodecRun {
    val encodeString = ByteCodec[String].encode("hello")

    val encodeInt = ByteCodec[Int].encode(1000)

    // val encodeOptionString = ByteCodec[Option[String]].encode(Option("world"))


}


