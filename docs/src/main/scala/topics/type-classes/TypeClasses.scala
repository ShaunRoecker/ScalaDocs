package langfeat.`type-classes`


object TypeClasses {
  

    // Type classes are a way to extend the functionality of a type (trait) 
    // without actually extending the type itself. With type classes we can 
    // create an implicit singleton object for a type, and then use that trait's
    // functionality indefinitely, without inheriting the trait.

    // First example:

    trait Stringify {
        def turnToString: String
    }

    // Say we want to create a new type UserOOP, that can use the functionality of this trait
    // Well, in order to do so, we need to inherit the trait...
                                                                //    V-- Inheritance
    final case class UserOOP(name: String, email: String) extends Stringify {
        override def turnToString: String = s"name: $name, email: $email"
    }

    // This example is trivial obviously, however it demonstrates how OOP approaches
    // the issue of extending functionality through inheritance

    // In functional programming, we use type classes to extend the functionality of
    // some trait (or Type), without adding anything to the original data type.

    // Lets create a User2 Algebraic Data Type from a case class, which will give us a product type...

    case class User(name: String, email: String)

    // Now let's create a Type Class, so that we can extends its functionality to our User ADT,
    // without using inheritance.

    // The type class consists of these things:
        // 1. A parameterized type (or trait) - this is called the "type class"
        // 2. A "type class instance" or implementation of this trait
        // 3. An interface that uses an implicit parameter

    // the type class...
    trait Equal[T] {
        def apply(a: T, b: T): Boolean
    }

    // The type class instance(s) for different types
    object TCInstances {
        // The TC instances are usually created as singleton implicit objects
        // so that they can be used as implicit parameters in the #3- interface

        // a User type class instance
        implicit object NameEquality extends Equal[User] {
            override def apply(a: User, b: User): Boolean = 
                a.name == b.name
        }

        implicit object EmailEquality extends Equal[User] {
            override def apply(a: User, b: User): Boolean = 
                a.email == b.email
        }

        implicit object FullEquality extends Equal[User] {
            override def apply(a: User, b: User): Boolean = 
                a.name == b.name && a.email == b.email
        }
        
        // a String type instance instance
        implicit object StringEquality extends Equal[String] {
            override def apply(a: String, b: String): Boolean = 
                a == b
        }

        // We can have as many type class instances as we need or want here,
        // for any type we have.  The String example is trivial, but you can
        // see that we can create a TC instance for a type that we made up - User
    }
    import TCInstances._ // I'm importing
    // specific instances here so the compiler is not confused as to which
    // instance to use on a User.  With type class instances, there is usually
    // a default impicit instance for a given type (if there is one),
    // and if you want to use a different instance, you need to use it
    // explicitly.  --One example of this in the wild, is an implcit Ordering
    // for int for example.  The default implicit instance of Ordering for 
    // Int is from "Large to Small" and if you want a different comparison,
    // you need to explicitly use it as a parameter or create a new instance
    // and import it into scope.

    // 3- The Type Class Interface
    // The interface is how we actually use the type class functionality on
    // our types.  We use an implicit "equilizer" that is our type class,
    // then when we apply this method equalityCheck on a type that we created
    // a type class instance for, this parameter will be picked up by the
    // compiler and will execute the type class functionality on our type.

    object EqualityChecker {
        def equalityCheck[A](a: A, b: A)(implicit equilizer: Equal[A]): Boolean =
            equilizer.apply(a, b)
    }
    import EqualityChecker._

    val user1 = User("John", "John@jmail.com")
    val user2 = User("John", "Jonathon@jmail.com")

    println(equalityCheck(user1, user2)(NameEquality)) //true
    println(equalityCheck(user1, user2)(EmailEquality)) //false
    println(equalityCheck(user1, user2)(FullEquality)) //false
        // If we didn't have several implicit instances for User
        // we could ommit the second parameter like this for 
        // StringEquality:

    println(equalityCheck("Is this string", "equal?")) // false
    println(equalityCheck("This one is", "This one is")) //true


    // Note, equilizer is implicit, but we can also pass in an explicit type class
    // instance if we want a specific instance.

    // This is the type class "interface" because as you can see, it is simply
    // a generic way to interact with the type class instances. It's little more
    // than a middle-man-method so that we are not repeating ourselves. Stay DRY 
    // in a rainy OOP world with the FP umbrella.  (will keep day job)

    // Example 2
    case class Person(name: String, age: Int)

    trait HtmlWriter[T] {
        def convertToHtml(value: T): String
    }

    object HtmlWriter {
        implicit object PersonHtmlWriter extends HtmlWriter[Person] {
            override def convertToHtml(p: Person) = s"<h1>Name:${p.name}</h1><h2>Age:${p.age}</h2>"
        }

        implicit object StringHtmlWriter extends HtmlWriter[String] {
            override def convertToHtml(value: String) = s"<h1>${value}</h1>"
        }

        implicit object IntHtmlWriter extends HtmlWriter[Int] {
            override def convertToHtml(value: Int) = s"<h1>${value.toString}</h1>"
        }
    }
    import HtmlWriter._
    
    def toHtml[T](item: T)(implicit htmlWriter: HtmlWriter[T]): String = {
        htmlWriter.convertToHtml(item)
    }

    val string = "This is a String"
    val integer = 14563
    val person = Person("John", 45)

    println(toHtml(string)) // <h1>This is a String</h1>
    println(toHtml(integer)) // <h1>14563</h1>
    println(toHtml(person)) // <h1>Name:John</h1><h2>Age:45</h2>



    import java.util.Date
    // Json Serializer example:
    object JSONSerialization{

        case class User2(name: String, age: Int, email: String)
        case class Post(content: String, createdAt: Date)
        case class Feed(user: User2, posts: List[Post])

        // Steps:
            // 1. Create an itermediary data types
            // 2. Create type class instances for conversion from wild types to
                    // intermediary type
            // 3. then serialize the intermediate data type to JSON
        
        sealed trait JSONValue{ //intermediate data type
            def stringify: String 
        }

    
        final case class JSONString(value: String) extends JSONValue{
            def stringify: String =
                "\"" + value + "\""
        }

        final case class JSONNumber(value: Int) extends JSONValue{
            def stringify: String = value.toString
        }

        final case class JSONArray(values: List[JSONValue]) extends JSONValue{
            def stringify: String = values.map(_.stringify).mkString("[", ",", "]")
        }

        final case class JSONObject(values: Map[String, JSONValue]) extends JSONValue{
            // This will be the intermediate object that looks like JSON
            def stringify: String = values.map {
                case (key, value) => "\"" + key + "\":" + value.stringify
            }.mkString("{",",","}")
        }


        val data = JSONObject(Map(
            "user" -> JSONString("myfirstUser"),
            "posts" -> JSONArray(List(
                JSONString("Scala saved my life yesterday"),
                JSONNumber(123)
            ))
        ))

        println(data.stringify)

        // type class

        trait JSONConverter[T]{
            def convert(value: T): JSONValue
        }
        // conversion class (enrichment)
        implicit class JSONOps[T](value: T){
            def toJSON(implicit converter: JSONConverter[T]): JSONValue =
                converter.convert(value)
        }
        // Type Class Instances
        // existing data types
        implicit object StringConverter extends JSONConverter[String]{
            def convert(value: String): JSONValue = JSONString(value)
        }

        implicit object NumberConverter extends JSONConverter[Int]{
            def convert(value: Int): JSONValue = JSONNumber(value)
        }

        // custom data types
        implicit object UserConverter extends JSONConverter[User2]{
            def convert(user: User2): JSONValue = JSONObject(Map(
                "name" -> JSONString(user.name),
                "age" -> JSONNumber(user.age),
                "email" -> JSONString(user.email)
            ))
        }
        implicit object PostConverter extends JSONConverter[Post]{
            def convert(post: Post): JSONValue = JSONObject(Map(
                "content" -> JSONString(post.content),
                "created" -> JSONString(post.createdAt.toString)
            ))
        }

        implicit object FeedConverter extends JSONConverter[Feed]{
            def convert(feed: Feed): JSONValue = JSONObject(Map(
                "user" -> feed.user.toJSON, 
                "posts" -> JSONArray(feed.posts.map(_.toJSON))
            ))
        }
        
        
        val now = new Date(System.currentTimeMillis())
        val john = User2("John", 34, "john@example.com")
        val feed = Feed(john, List(
            Post("hello", now),
            Post("look at this cute puppy", now)
        ))

        println(feed.toJSON.stringify)

}


}






    

