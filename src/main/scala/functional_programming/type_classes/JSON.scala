package functional.typeclasses.json

import topics.extensionmethods.implicitclasses.ImplicitClasses.extractedDigits




object JsonLibraryScala2:


    trait JSONWrite[T] {
        def toJsonString(item: T): String
    }


    def jsonify[T: JSONWrite](item: T): String = {
        implicitly[JSONWrite[T]].toJsonString(item)
    }


    object JSONWrite {


        implicit object StringJson extends JSONWrite[String] {
            override def toJsonString(item: String): String = 
                s"""$item"""
        }


        implicit object BooleanJson extends JSONWrite[Boolean] {
            override def toJsonString(item: Boolean): String =
                item.toString
        }

        implicit object IntJson extends JSONWrite[Int] {
            override def toJsonString(item: Int): String = 
                item.toString
        }


        implicit object DoubleJson extends JSONWrite[Double] {
            override def toJsonString(item: Double): String = 
                item.toString
        }


        implicit def listJson[T: JSONWrite]: JSONWrite[List[T]] =  {
            new JSONWrite[List[T]] {
                override def toJsonString(xs: List[T]): String = {
                    xs.map(t => jsonify(t)).mkString("[", ",", "]")
                }
            }
        }


        implicit def mapJson[T: JSONWrite]: JSONWrite[Map[String, T]] =  {
            new JSONWrite[Map[String, T]] {
                override def toJsonString(mp: Map[String, T]): String = {
                    val pairs = for ((k, v) <- mp) yield 
                        s"${jsonify(k)}: ${jsonify(v)}"
                    pairs.mkString("{\n", ",\n", "}\n")
                }
            }
        }
        
    }
    

object JsonLibraryScala3:

    // Type Class
    trait JSONWrite[T]:
        extension(item: T)
            def toJsonString: String


    // Type Class Implementation
    extension[T: JSONWrite](item: T)
        def asJson: String =
            item.toJsonString


    // Type Class Instances
    object JSONWrite:

        given JSONWrite[String] with
            extension(item: String)
                def toJsonString: String = s"""$item"""

            
        given JSONWrite[Boolean] with
            extension(bool: Boolean)
                def toJsonString: String =
                    bool.toString


        given JSONWrite[Int] with
            extension(item: Int)
                def toJsonString: String = item.toString


        given JSONWrite[Double] with
            extension(item: Double)
                def toJsonString: String = item.toString


        given listJson[T: JSONWrite]: JSONWrite[List[T]] with
            extension(xs: List[T])
                def toJsonString: String = 
                    xs.map(t => t.asJson).mkString("[", ",", "]")
         
                
        given mapJson[T: JSONWrite]: JSONWrite[Map[String, T]] with
            extension(mp: Map[String, T])
                def toJsonString: String =
                    val pairs = 
                        for (k, v) <- mp
                        yield s"\t${k.toJsonString}: ${v.toJsonString}"
                    pairs.mkString("{\n", ",\n", "\n}")

        

