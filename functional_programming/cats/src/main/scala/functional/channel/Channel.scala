import java.io.FileOutputStream
import java.nio.ByteBuffer
import scala.util.Using


object ChannelUsingAnyNotOptimal {

    trait Channel {
        def write(obj: Any): Unit
    }

    object FileChannel extends Channel {
        override def write(obj: Any): Unit = {
            val bytes: Array[Byte] = 
                obj match {
                    case n: Int => 
                        val bb = ByteBuffer.allocate(4)  //
                        bb.putInt(n)                     //
                        bb.array()                       //
                    case s: String => s.getBytes 
                    // another way: s.map(c => c.toByte).toArray
                    case invalid => throw new Exception("unhandled")
                }

            // FileOutputStream is meant for writing streams of raw bytes such as image data. 
            // For writing streams of characters, consider using FileWriter.
            Using(new FileOutputStream("./test")) { os =>
                os.write(bytes)
                os.flush()    
            } // Using automatically closes the file output stream
              // after the end of the block
        }
    }

    // FileChannel.write(obj="Hello, World!")

    // This Method of Creating a Channel: =>
        // advantages: simple interface
        // disadvantages: 
            // 1. unhandled type throw exception
            // 2.  has 2 responsibilities

}

object ChannelWithByteEncodableInheritanceBetter {

    trait ByteEncodable {
        def encode(): Array[Byte]
    }

    trait Channel {
        def write(obj: ByteEncodable): Unit
    }


    final case class FullName(firstName: String, lastName: String) extends ByteEncodable {
        override def encode(): Array[Byte] = {
            firstName.getBytes ++ lastName.getBytes
        }
    } 

    object FileChannel extends Channel {
        override def write(obj: ByteEncodable): Unit = {
            Using(new FileOutputStream("./test2")) { os =>
                os.write(obj.encode())
                os.flush()    
            }

        }

    }

    // FileChannel.write(FullName("John", "Doe"))

    // This Method of Creating a Channel: =>
        // advantages: 
            // 1. unique responsibility
            // 2. easy to test
            // 3. unhandled type == compile error

        // disadvantages: 
            // 1. how do we extend classes we dont control
            // 2. only one implementation
            // 3. overloaded interface

}


object ChannelWithTypeClasses {

    trait Channel {
        def write[A](obj: A)(implicit enc: ByteEncoder[A]): Unit
    }


    trait ByteEncoder[A] {
        def encode(a: A): Array[Byte]
    }

    object ByteEncoder {
        def apply[A](implicit ev: ByteEncoder[A]): ByteEncoder[A] = ev

        def instance[A](f: A => Array[Byte]): ByteEncoder[A] = ???

        // By putting this type class instance in the companion object,
        // it will automatically look for this instance by default
        implicit object stringEncoder extends ByteEncoder[String] {
            def encode(s: String): Array[Byte] = 
                s.getBytes
        }

    }
    import ByteEncoder._

    final case class FullName(firstName: String, lastName: String)


    final case class Switch(isOn: Boolean)

    object Switch {
        
        implicit object switchEncoder extends ByteEncoder[Switch] {
            def encode(switch: Switch): Array[Byte] = 
                Array(if (switch.isOn) '1'.toByte else '0'.toByte)
            
        }
    }

    import Switch._

    // Common situation:
        // For each of type A
        // - 1 main instance of ByteEncoder
        // - 


    // Goal:
        // - use the main instance by default
        // - Provide a different instance for specific use cases
    
    object ByteEncImplicits {

        implicit object intEncoder extends ByteEncoder[Int] {
            def encode(i: Int): Array[Byte] = {
                val bb = ByteBuffer.allocate(4)
                bb.putInt(i)
                bb.array()
            }
        }

        implicit object rotate3StringEncoder extends ByteEncoder[String] {
            def encode(s: String): Array[Byte] =
                s.getBytes.map(b => (b + 3).toByte)
        }

        implicit object fullNameEncoder extends ByteEncoder[FullName] {
            def encode(fn: FullName): Array[Byte] =
                fn.firstName.getBytes ++ fn.lastName.getBytes
        }
    }

    // import ByteEncImplicits.rotate3StringEncoder
    import ByteEncImplicits.intEncoder
    import ByteEncImplicits._




    object FileChannel extends Channel {
        override def write[A](obj: A)(implicit enc: ByteEncoder[A]): Unit = {
            val bytes = enc.encode(obj)
            Using(new FileOutputStream("./test")) { os => 
                os.write(bytes)
                os.flush()   
            }
        }
            
    }

    
    FileChannel.write(42) //
    // Because this type's (String) implicit instance is located in the
    // companion object, we don't need the type annotation for String
    FileChannel.write("a string")(rotate3StringEncoder)

    FileChannel.write(Switch(true))
    
    FileChannel.write[FullName](FullName("Alex", "Alexander"))

    
    // This Method of Creating a Channel: =>
    // advantages: 
        // 1. can be instanced by any time, including ones we don't own
        // 2. cleaner interface
        // 3. several implementations possible

    // disadvantages: 
        // 1. how do we extend classes we dont control
        // 2. only one implementation
        // 3. overloaded interface

} //