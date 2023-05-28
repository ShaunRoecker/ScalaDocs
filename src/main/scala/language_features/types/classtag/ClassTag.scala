package language.features.types.classtag
import scala.reflect._


object ClassTagUsage:

    def classTagRun(): Unit =
        val s: String = "string"

        def getClassTag[T: ClassTag](x: T): ClassTag[T] = 
            classTag[T]

        
        // Int
        val intCT = getClassTag(10)

        println(intCT.runtimeClass) // int

        // Int
        val sCT = getClassTag(s)

        println(sCT) // java.lang.String

        println(sCT.runtimeClass) // class java.lang.String


        // Since types in Scala are erased at runtime,
        // this method appears to run but always returns
        // true incorrectly
        def isAErasure[T](x: Any): Boolean =
            x match
                case _: T => true
                case _ => false


        println(isAErasure[Int](10)) // true
        println(isAErasure[String](10)) //true
        println(isAErasure[String]("string")) //true


        // We can use ClassTag to get a reliable answer 
        def isA[T: ClassTag](x: Any): Boolean =
            x match
                case _: T => true
                case _ => false

        println(isA[Int](10)) // true
        println(isA[String](10)) //false
        println(isA[String]("string")) //true


