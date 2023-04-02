package extensionmethods.scala3extmethods

object ExtensionMethods {
    // Extensions methods are the Scala 2 equivelent of implicit classes, which "extends"
    // the functionality of a type by defining one or more methods that act on that type,
    // without making changes to the original types themselves.

    // Extension methods are also known as the "Pimp my library" pattern, so if you hear 
    // that and are like "wtf?", the pimp in question is an extension method

    // Extension methods are usually defined inside an object with any other extension
    // for a type

    object StringExtensions {
        extension(s: String) {
            def highestValue = 
                s.split(" ").maxBy(_.map(_.toInt - 96).sum)
        }
    }

    import StringExtensions._

    val string = "which one of these words do you think has the highest value by ASCII code?"
    val highest = string.highestValue  
    println(highest) // words

    // You can see that by implementing an extension method for the type string, we were able to
    // extend functionality to string without having access to the implementation of the String
    // data type or class.

    // extension methods can be used anytime we think- "I wish String had a ___ method, 
    // that would be pretty neat", and you are programming in Scala3.


    // here is another example with Int:

    object IntExtensions {
        extension(n: Int) {
            def highestDigit = 
                n.toString.maxBy(_.asDigit).toInt - 48
        }
    }

    import IntExtensions._

    val number = 346949
    val highestDigit = number.highestDigit
    println(highestDigit) // 9

    
    // Beyond primitive types, you can also use extendion methods with algebraic data types.
    
    case class Person(name: String, age: Int)

    object PersonExtensions {
        extension(p: Person) {
            def addExclamationToName: Person = 
                p.copy(name = p.name + "!") 
        }
    }

    import PersonExtensions._

    val john = Person("John", 32)

    val johnExclamation = john.addExclamationToName
    println(johnExclamation) // Person(John!,32)

    // Use cases for this would be when you are using data types from a third party library,
    // or even your own data types, which you don't want to add the method to the companion 
    // object

    // Note also that you can add as many extension methods to a type within the same
    // extension keyword as you want, for example:

    object StringExtensions2 {
        extension(s: String) {
            def why: String = 
                s + " why"

            def so: String = 
                s + " so"

            def many: String = 
                s + " many"

            def methods: String = 
                s + " methods?"
        }
    }

    import StringExtensions2._

    val string2 = "dog"
    val manyMethods = string2.why.so.many.methods
    println(manyMethods) //dog why so many methods?


    // We can also extend parametric types:

    object ParametricExtensions {
        extension[T](xs: List[T]) {
            def after(n: Int): Option[List[T]] = 
                if (xs.length <= n) None
                else Some(xs.splitAt(n)._2)
        }

        extension[T](opt: Option[List[T]]) {
            def unpack: List[T] =
                opt match
                    case None => List()
                    case Some(xs) => xs
        }
    }

    import ParametricExtensions._

    val listInt = List(1, 2, 3, 4, 5, 6, 7)
    val listStr = List("a", "list", "of", "strings")

    val after4listInt = listInt.after(5)
    println(after4listInt) //Some(List(6, 7))


    val after2StrList = listStr.after(2)
    println(after2StrList) //Some(List(of, strings))

    val unpacked = listInt.after(3).unpack
    println(unpacked) // List(4, 5, 6, 7)

    val alsoUnpacked = after2StrList.unpack
    println(alsoUnpacked) // List(of, strings)


    // We can also use using clauses with extension methods

    object ConstraintExtemsion {
        extension [T](x: T)(using n: Numeric[T])
            def + (y: T): T = n.plus(x, y)

    }
}

