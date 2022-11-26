object ScalaObjects extends App {
    def main(): Unit = 
        println("Scala Objects")
        // castingObjects()
        // passingAClassTypeWithTheClassOfMethod()
        // creatingSingletonsWithObject()
        // creatingStaticMembersWithCompanionObjects()
        // usingApplyMethodsInObjectsAsConstructors()
        // implementingAStaticFactoryWithApply()
        // reifyingTraitsAsObjects()
        implementingPatternMatchingWithUnapply()

    main()
    //CASTING OBJECTS
    def castingObjects(): Unit = {
        // Problem: You need to create an instance of a class from one type to another, 
        // such as when creating objects dynamically.

        // In this example, the object returned by the lookup method is cast to an
        // instance of a class named Recognizer:
        // val recognizer = cm.lookup("recognizer").asInstanceOf[Recognizer]

        // The asInstanceOf method is defined in the Scala Any Class, and is
        // therefore available on all objects

        // In dynamic programming, it's often necessary to cast from one type to another.
        // For instance, this approach is needed when reading a YAML config file with 
        // the SnakeYAML library 
        
        // val yaml = Yaml(new Constructor(classOf[EmailAccount]))
        // val emailAccount = yaml.load(text).asInstanceOf[EmailAccount]

        // The asInstanceOf method isn't limited to only these situations. You can also use it
        // to cast numeric types:
        val a = 10
        val b = a.asInstanceOf[Long]
        val c = a.asInstanceOf[Byte]
        val d = a.asInstanceOf[Float]

        println(a)
        println(b)
        println(c)
        println(d)

        // It can be used in more complicated code too, such as when you need to interact
        // with Java and send it an array of 'Object' instances:
        val objects = Array("a", 1)
        val arrayOfObjects = objects.asInstanceOf[Array[Object]]
        // AJavaClass.sendObjects(arrayOfObjects)

        // If you are programming with the java.net classes, you may need to use it when
        // opening an HTTP URL connection:
        import java.net.{URL, HttpURLConnection}
        val url = "https://en.wikipedia.org/wiki/YAML"
        val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]

        //  This type of coding can lead to ClassCastException, so use a try/catch 
            // block as usual
    }
    // PASSING A CLASS TYPE WITH THE classOf METHOD
    def passingAClassTypeWithTheClassOfMethod(): Unit = {
        // Problem: When an API requires that you pass in a Class type, you'd call .class
        // on an object in Java, but that doesn't work in Scala

        // Use the Scala "classOf" method.  The following example from a 
        // Java Sound API project shows how to pass a class of type TargetDataLine
        // to a method named DataLine.info: 
        // val info = DataLine.Info(classOf[TargetDataLine], null)
        // In Java:
        // val info = DataLine.Info(TargetDataLine.class, null)

        // The classOf method is defined in the Scala Predef object and is therefore available
        // without requiring an import

        // Once you have a Class Reference you can begin with simple reflection 
        // techniques. For instance, the following example demonstrates how to 
        // access the methods of the String class
        val stringClass = classOf[String]
        val stringMethods = stringClass.getMethods
        stringMethods.foreach(e => {
            val array = e.toString.split(" ").last
            println(array) //don't have to break it out like this, but an example
        })
        // See Also: https://oreil.ly/A2vWS



    }
    // CREATING SINGLETONS WITH 'object' KEYWORD
    def creatingSingletonsWithObject(): Unit = {
        // Problem: You want to create a singleton object to ensure that 
        // only one instance of a class exists

        // Create singleton objects in Scala with the 'object' keyword.
        // por ejemplo, you might create a singlton object to represent something
        // you only want one instance of, such as a keyboard, mouse, 
        // or perhaps a cash register in a pizza restaurant:
        object CashRegister:
            def open() = println("opened")
            def close() = println("closed")
        
        // With CashRegister defined as an object, there can be only one instance of it,
        // and its methods are called just like static methods on a Java class
        CashRegister.open()
        CashRegister.close()

        // A singleton object is a class that has exactly one instance.
        // Using this pattern is also a common way to create utility methods, 
        // such as this StringUtils object:

        object StringUtils:
            def isNullOrEmpty(s: String = ""): Boolean =
                if s==null || s.trim.equals("") then true else false
            def leftTrim(s: String): String = s.replaceAll("^\\s+", "")
            def rightTrim(s: String): String = s.replaceAll("\\s+$", "")
            def capitalizeAllWordsInString(s: String): String =
                s.split(" ").map(_.capitalize).mkString(" ")
        
        // Because these methods are defined in an object instead of a class, they
        // can be called directly on the object, similar to a static method in Java:
        println(StringUtils.isNullOrEmpty("Hello World")) //false
        println(StringUtils.leftTrim("   Hello World")) //"Hello World"
        println(StringUtils.capitalizeAllWordsInString("how are you?")) //How Are You?

        // Singleton case objects also make great reusable messages in certain situations,
        // such as when using Akka actors. For instance, if you have a number of actors that can all
        // receivestart and stop messages, you can create singletons like this:

        case object StartMessage
        case object StopMessage

        // You can then use those objects as messages that can be sent to actors:
        // 
        

    }
    // CREATING STATIC MEMBERS WITH COMPANION OBJECTS
    def creatingStaticMembersWithCompanionObjects(): Unit = {
        // Problem: You've come to Scala from a language like Java and want to create
        // a class that has both 'instance' and 'static' members, but Scala doesn't have
        // static keyword

        // When you want nonstatic (instance) members in a class combined with 
        // static members, define the instance members in a class and define the 
        // members that you want to appear as static members in an object that has 
        // the same name as the class, and is in the same file as the class.  
        // This object is known as the class's companion object (and the class 
        // is known as the objects's companion class)

        // Using this approach lets you create what appear to be static members,
        // on a class, as shown in this example:
        // Pizza Class
        class Pizza(var crustType: String):
            override def toString = s"Crust type is $crustType"
        
        // Companion Object
        object Pizza:
            val CRUST_TYPE_THIN = "THIN"   //static fields
            val CRUST_TYPE_THICK = "THICK"
            def getPrice = 0.0  // static method

        // With the Pizza class and Pizza object defined in the same file,
        // members (elements of the class: methods, values, variables, etc)
        // of the object can be accessed as static members of a Java class
        println(Pizza.CRUST_TYPE_THICK) //THICK
        println(Pizza.getPrice) //0.0

        // You can create a new Pizza instance and use it as usual:
        val p = Pizza(Pizza.CRUST_TYPE_THIN)
        println(p) //Crust type is THIN

        // So recap to creating static members for a class
        // 1. Define your class and object in the same file, giving them the same name
        // 2. Define members that should appear to be "static" in the object
        // 3. Define nonstatic (instance) members in the class

        // So by static we mean any member that is going to be the same across
        // all instances of the class, and by nonstatic we mean any (elements of the class: 
        // methods, values, variables, etc) that will change across different instances
        
        // ACCESSING PRIVATE MEMBERS
        // *It's important to note that a class and its companion object can access each other's
        // private members (they are "going steady" so to speak). 

        // In the following code, the static member 'double' in the object can access the 
        // private variable 'secret' of the class Foo:
        
        class Foo:
            private val secret = 42
            def printObj = println(s"I can see ${Foo.obj}")
        
        object Foo:
            private val obj = "Foo's object"
            def doubleFoo(foo: Foo) = foo.secret * 2
        
        val f = Foo()
        println(Foo.doubleFoo(f)) //84

        // Similarly, the instance member printObj can access the private field obj 
        // of the Object foo


    }
    // USING APPLY METHODS IN OBJECTS AS CONSTRUCTORS
    def usingApplyMethodsInObjectsAsConstructors(): Unit = {
        println("Check")
        // Problem: In some situations it may be better, easier, or more convenient
        // to create apply methods in a companion object to work as a class constructor,
        // and you want to understand how to write these methods.

        // A techique you can use to create constructors is to define apply methods in the classes
        // companion object. Technically, these aren't constructors, they are more like functions
        // or factory methods, but serve a similar purpose.

        // Steps to creating an apply method in a companion object:
            // 1. Define a Person class and Person object in the same file.
            // 2. Make the Person class constructor private
            // 3. Define one or more apply methods in the object to serve as builders of the class

        // For the first two steps, create the class and object in the same file,
        // and make the class constructor private
        class Person private(val name: String):
        //     // define any instance (nonstatic) members you need here:
            override def toString: String = name

        object Person:
            // define any static members you need here:
            def apply(name: String): Person = new Person(name)


        val Regina = Person("Regina")
        val ReginaConstructedWithApply = Person.apply("Regina")
        // ^^ These are doing the same thing ^^
        println(ReginaConstructedWithApply)
        println(Regina)
        val a = List(Person("Regina"), Person("Robert"))
        println(a)

        // When you want to use this technique and provide multiple ways to build a class
        // define multiple apply methods with the desired signatures
        class People private(val name: String, var age: Int):
            override def toString: String = s"${name} is ${age} years old"

        object People:
            def apply(): People = new People("", 0)
            def apply(name: String): People = new People(name, 0)
            def apply(name: String, age: Int): People = new People(name, age)
        
        // The above code demonstrates 3 ways to create a new "People" instance
        println(People()) // is 0 years old
        println(People("Fred")) //Fred is 0 years old
        println(People("Jimmy", 30)) //Jimmy is 30 years old

        // Because apply is just a function, you can implement it however you see fit.
        // For instance, you can construct a Person from a tuple, or even a variable number
        // of tuples
        class Dog private(val name: String, var age: Int):
            override def toString: String = s"${name} is ${age} years old"

        object Dog:
            def apply(t: (String, Int)) = new Dog(t(0), t(1)) //create instance from tuple
            def apply(ts: (String, Int)*) = //create a Dog instance/instances from a variable
                for t <- ts yield new Dog(t(0), t(1)) // number of tuples

        // create a person from a tuple
        val john = Dog(("John", 34))
        println(john) //John is 34 years old

        val dogs = Dog(
            ("John", 6),
            ("Hector", 3),
            ("Patches", 12)
        )
        println(dogs) //ArraySeq(John is 6 years old, Hector is 3 years old, Patches is 12 years old)

    }
    // IMPLEMENTING A STATIC FACTORY WITH apply()
    def implementingAStaticFactoryWithApply(): Unit = {
        // Problem: To keep object creation in one location, 
        // you want to implement a static factory in Scala

        //  A static factory is a simplified version of the "factory pattern." 
        // To create a static factory with an apply method in an object,
        // typically a companion object.

        // package animals
        sealed trait Animal:
            def speak(): Unit 

        class Dog extends Animal:
            override def speak() = println("woof")
        
        class Cat extends Animal:
            override def speak() = println("meow")
        
        object Animal:
            // the factory method
            def apply(s: String): Animal =
                if s == "dog" then Dog() else Cat()
        
        
        // import animals.* 

        val cat = Animal("cat")
        val dog = Animal("dog")

        cat.speak()
        dog.speak()        

        // A benefit of this method is that instances of the Dog and Cat
        // can only be created with the factory method. 
        // Attempting to create them directly will fail
        
        // See Also: https://oreil.ly/hZnnR


    }
    // REIFYING TRAITS AS OBJECTS
    def reifyingTraitsAsObjects(): Unit = {
        // Problem: You've created one or more traits that contain methods, 
        // and now you want to make them concrete.

        // When you see that an object extends one or more traits, the object 
        // is being used to reify the trait(s). The word reify means "to take " 
        // an abstract concept and making it concrete" - in this case instantiating
        // a singleton object from one or more traits

        

        //case class Dog(name: String) extends Animal
        case class Cat(name: String) extends Animal

        // In a functional programming style you might also create a set of animal Services
        // as a trait

        // assumes all animals have legs
        trait AnimalServices:
            def walk(a: Animal) = println(s"${a} is walking")
            def run(a: Animal) = println(s"$a is running")
            def stop(a: Animal) = println(s"$a is stopping")

        object AnimalServices extends AnimalServices

        // // val zeus = Dog("Zeus")
        // AnimalServices.walk(zeus)
        // AnimalServices.run(zeus)
        // AnimalServices.stop(zeus)

        // Model your data using case classes
        // Put the related functions in traits
        // Reify your traits as objects, combining multiple traits as needed for a solution

        // More real world example of this process:
        trait Animal
        trait AnimalWithLegs
        trait AnimalWithTail

        case class Dog(name: String) extends Animal, AnimalWithLegs, AnimalWithTail
        // Next, create services: i.e., sets of functions corresponding to the traits
        trait TailServices[AnimalWithTail]: //can parameterize the trait so it can only be
                                            // used with a certain trait
            def wagTail(a: AnimalWithTail) = println(s"$a is wagging tail")
            def stopTail(a: AnimalWithTail) = println(s"$a tail is stopped")
        // Bolow is the same as above, trying to visually describe what is happening
        // trait TailServices[x]: //can parameterize the trait so it can only be
        //                                     // used with a certain trait
        //     def wagTail(a: x) = println(s"$a is wagging tail")
        //     def stopTail(a: x) = println(s"$a tail is stopped")

        trait AnimalWithLegsServices[AnimalWithLegs]:
            def walk(a: AnimalWithLegs) = println(s"$a is walking")
            def run(a: AnimalWithLegs) = println(s"$a is running")
            def stop(a: AnimalWithLegs) = println(s"$a is stopped")
        
        trait DogServices[Dog]:
            def bark(d: Dog) = println(s"$d says'woof'")

        object DogServices extends DogServices[Dog], AnimalWithLegsServices[Dog], TailServices[Dog]

        // Then you can use those functions:
        import DogServices.*

        val rocky = Dog("Rocky")
        walk(rocky) //Dog(Rocky) is walking
        wagTail(rocky) //Dog(Rocky) is wagging tail
        bark(rocky) //Dog(Rocky) says'woof'

        // See Also: https://oreil.ly/fweY0



    }
    // IMPLEMENTING PATTERN MATCHING WITH unapply()
    def implementingPatternMatchingWithUnapply(): Unit = {
        // Problem: You want to write an unapply method for a class so you can extract
        //  its fields in a match expression

        // Write an unapply method in the companion object of your class with 
        // the proper return signature.

        // 1. Writing an unapply method that returns a String
        // 2. Writing an unapply method to work with a match expression

        // class PersonA(val name: String, val age: Int)
        // object PersonA:
        //     def unapply(p: PersonA): String = s"${p.name}, ${p.age}"
        
        // val p = PersonA("Lori", 33)
        

        // // The benefit of an unapply method is that it gives you a way to 'deconstruct'
        // // a Person instance:

        // val personAsString = PersonA.unapply(p) //Lori, 33

        // In Scala, when you put an unapply method in a companion object,
        // its's said that you've created an extractor method, because you've created 
        // a way to extract the fields out of the object.

        // WRITING AN UNAPPLY METHOD TO WORK WITH A MATCH EXPRESSION

        // To extract fields for implementing with a match expression,
        // unapply needs to return a specific type.

        // 1. If your class has only one parameter and it's of type A, return an Option[A],
            // i.e., the parameter value wrapped in a Some()
        // 2. When your class has multiple parameters of types A1, A2, ...An, return
            // them in an Option[(A1, A2... An)], i.e., a tuple that contains those values, 
            // wrapped in a Some

        // If for some reason your unapply method can't deconstruct its parameters into
        // proper values, return a None instead.


        class Person(val name: String, val age: Int)
        object Person:
            def unapply(p: Person): Option[(String, Int)] = Some(p.name, p.age) 
        
        val person = Person("Lori", 33)

        // You can now use Person in a match expression

        val deconstructedPerson: String = person match
            case Person(n, a) => s"name: $n, age $a"
            case null => "null!"
        
        println(deconstructedPerson) //name: Lori, age 33

        // Case classes generate this code automatically, but this is how you enable
        // an extractor in a class so it can be used in a match expression.

        // See Also: https://oreil.ly/mqDBb
        
    }
    




}
