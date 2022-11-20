object ClassesPartOne extends App {
    //   Domain modeling is how you use a programming language 
    // to model the world around you, i.e., how you model concepts 
    // like people, cars, financial transactions, etc.
    // Whether you're writing code in a functional programming or 
    // object oriented style, this means that you model the attributes 
    // and behaviors of these things
    def chapter5PartOne(): Unit = {
        println("Classes")
        // choosingFromDomainModelingOptions()
        // creatingAPrimaryConstructor()
        controllingTheVisibiltyOfConstructorFields()
    }
    chapter5PartOne()
    // To provide flexibility to model the world around you, 
    // Scala 3 offers the following language constructs:
    // /////////////////////////////////////////////////////////////
    // CLASSES
    // CASE CLASSES
    // TRAITS
    // ENUMS
    // OBJECTS AND CASE OBJECTS
    // ABSTRACT CLASSES
    // METHODS, which can be defined within all of those constructs
    // /////////////////////////////////////////////////////////////

    // This one line of Scala code takes 29 lines to write in Java!
    class EmpTrad(var name: String, var age: Int, var role: String)

    // This FP style case class is equivelent to well over 100 lines of Java code:
    case class EmpCase(name: String, age: Int, role: String)

    // Using classes in match expressions:
        // when you want to use a class in a match expression,
        // implement an 'unapply' method inside the companion object of the class.
        // Because this is something you do in an 'object', that topic is covered in Ch. 7

    // Choosing from Domain Modeling Options
    def choosingFromDomainModelingOptions(): Unit = {
        println("Choosing from Domain Modeling Options")
        // Problem: Because Scala offers traits, enums, classes, case classes, objects, 
        // and abstract classes, you want to understand how to choose frome these 
        // domain modeling options when designing your own code.

        // Functional Programming options:
        // When programming in a FP style, you'll primarily use these constructs:
            // Traits 
            // Enums
            // Case Classes
            // Objects

        // In the FP style you'll use these constructs as follows:
            // TRAITS:
                // Traits are used to create small, logically grouped units of behavior. 
                // They're typically written as def methods but can also written as val functions 
                // if you prefer. Either way, they're written as pure functions(pg. 272). 
                // These traits will later be compined into concrete objects.
            
            // ENUMS:
                // Use enums to create 'algebraic data types' (ADTs) as well as Generalized ADTs (GADTs)
            
            // CASE CLASSES:
                // Use case classes to create objects with immutable fields 
                // (known as 'immutable records' is some languages)
                // Case classes were created for the FP style, and they have several 
                // specialized methods that help in this style, including: parameters 
                // that are val fields by default, 'copy' methods for when you want 
                // to simulate mutating values, built-in unapply methods for pattern matching, 
                // good default equals and hashCode methodes, and more.

            // OBJECTS
                // In FP you'll typically use objects as a way to make one or more traits "real",
                // in a proccess thats technically known as reification.

        // OBJECT ORIENTED PROGRAMMING OPTIONS
        // TRAITS:
                // Traits are primarily used as interfaces. 
                // Classes will later be used to implement these interfaces
               
            // ENUMS:
                // You'll primarily use enums to create simple sets of constants, 
                // like the positions of a display( top, bottom, left, right) 
            
            // CASE CLASSES:
                // In OOP you'll primarily use plain classes- not case classes. 
                // You'll also define their constructor parameters as val fields 
                // so they can be mutated. They'll cantain methods based on those 
                // mutable fields. You'll override the default accessor and mutator 
                // methods (getters and setters) as needed.

            // OBJECTS
                // You'll primarily use the object construct as a way to create the 
                // equivalent of static methods in Java, like a StringUtils object that 
                // contains static methods that operate on strings.
        
        enum Topping:
            case Cheese, Pepperoni, Sausage, Mushrooms, Onions
        
        enum CrustSize:
            case Small, Medium, Large
        
        enum CrustType:
            case Regular, Thin, Thick

        // An FP style example:

        case class Pizza(
            crustSize: CrustSize,
            crustType: CrustType,
            toppings: Seq[Topping]
        )

        case class Customer(
            name: String,
            phone: String,
            address: Address
        )

        case class Address(
            street1: String,
            street2: String,
            city: String,
            state: String,
            postalCode: String,
        )

        case class Order(
            pizzas: Seq[Pizza],
            customer: Customer
        )

        // Case classes are preferred in FP because all of the params are immutable
        // Also note these classes contain no methods, they are just simple data structures

        // Next we write the methods that operate on those data structures as pure functions,
        // and group the methods into small, logically organized 'traits', 
        // or just one trait in this case:
        
        trait PizzaServiceInterface:
            def addTopping(p: Pizza, t: Topping): Pizza
            def removeTopping(p: Pizza, t: Topping): Pizza
            def removeAllToppings(p: Pizza): Pizza
            def udpateCrustSize(p: Pizza, cs: CrustSize): Pizza
            def updateCrustType(p: Pizza, cs: CrustType): Pizza

        // Then we can implement those methods into other traits:
        trait PizzaService extends PizzaServiceInterface:
            def addTopping(p: Pizza, t: Topping): Pizza =
                //The 'copy' method comes with a case class
                val newToppings = p.toppings :+ t   // :+ appends and element to a copy of a collection, +: prepends
                p.copy(toppings = newToppings) // creates a copy of the Pizza, and assigns the toppings param to newToppings
            def removeTopping(p: Pizza, t: Topping): Pizza = ??? // ??? is the same as pass in Python so we can set up a method without logic yet
            def removeAllToppings(p: Pizza): Pizza = ??? 
            def udpateCrustSize(p: Pizza, cs: CrustSize): Pizza = ???
            def updateCrustType(p: Pizza, cs: CrustType): Pizza = ???
        end PizzaService

        // OOP Solution:
        import scala.collection.mutable.ArrayBuffer
        class PizzaOOP(
            var crustSize: CrustSize,
            var crustType: CrustType,
            val toppings: ArrayBuffer[Topping]
        ): 
            def addTopping(t: Topping): Unit = 
                toppings += t
            def removeTopping(t: Topping): Unit = 
                toppings -= t
            def removeAllTopping(t: Topping): Unit = 
                toppings.clear()
        end PizzaOOP
        // Notice how the FP class contains attrs but no behaviors, with OOP the pizza class contains both
        // class Order:
        //     private lineItems = ArrayBuffer[Product]()

        //     def addItem(p: Product): Unit = ??? 
        //     def revomeItem(p: Product): Unit = ??? 
        //     def getItems(): Seq[Product] = ??? 

        //     def getPrintableReceipt(): String = ???
        //     def getTotalPrice(): Money = ???
        // end Order

        // // usage
        // val o = Order()
        // o.addItem(Pizza(Small, Thin, ArrayBuffer(Cheese, Pepperoni)))
        // o.addItem(CheeseSticks)

        // This example assumes that you have a Product class hierarchy
        // that looks like this:
        // a Product may have methods to determine its cost, sales price, and other details

        // sealed trait Product

        // each class may have additional attributes and methods
        // class Pizza extends Product
        // class Beverage extends Product
        // class CheeseSticks extends Product
        
        // Note: Scala doesn't allow multiple inheritance (extend more than one class) 
        // Note: An interface describes a set of meathods and properties that an implementing class must have
        // 

        // WHEN TO USE ABSTRACT CLASSES
        // dont. probably.

    }
    // Creating a Primary constructor
    def creatingAPrimaryConstructor(): Unit = {
        println("Creating a Primary constructor")
        // Problem: You want to create a primary constructor for a Scala class, 
        // and you quickly find that the approach is different than Java(and other languages)

        // The primary constructor of a Scala class is a combonation of:
            // The constructor parameters
            // Fields (variable assignments) in the body of the class
            // Statements and expressions that are executed in the body of the class

        // The following class demonstrates constructor parameters, class fields, 
        // and statements in the body of a class:
        class Employee(var firstName: String, var lastName: String): // <- constructor arguments
            // a statement
            println("the constructor begins...")

            // some class fields (variable assignments)
            var age = 0
            private var salary = 0d

            // a method call
            printEmployeeInfo()

            // methods defined in the class
            override def toString = s"${firstName} ${lastName} is ${age} years old"
            def printEmployeeInfo() = println(this)  //uses toString

            // any statement or field prior to the end of the class
            // definition is part of the class constructor
            println("the constructor ends")
        // optional 'end' statement
        end Employee

        // Because the the constructor args are defined with var and are mutable,
        // Scala generates both accessor and mutator methods for them
        val e = Employee("John", "Doe")

        // you can get and set these constructor parameters like this
        e.firstName = "Jane"
        e.lastName = "Eod"
        // get like this
        println(e.firstName)
        println(e.lastName)

        // like constructor parameters, class fields are public by default
        // because the age field is var, it's also visible and can be mutated
        e.age = 30

        // conversely. the salary field is declared to be private, 
        // so it can't be accessed from outside the class
        // e.salary  //<Exception>
    }  
    // Controlling the Visiblity of Constructor Fields
    def controllingTheVisibiltyOfConstructorFields(): Unit = {
        println("Controlling the Visiblity of Constructor Fields")
        // Problem: You want to control the visibility of fields that are 
        // used as constructor parameters in a Scala class

        // The visibility of a constructor field in a Scala class is conrolled 
        // by whether the field is declared as val or var, without either val or var, 
        // and whether private is added to the fields

        // - if a field is declared as a var, Scala generates both getter and setter methods for that field
        // - If the field is val, Scala generates only a getter method for it.
        // - If a field doesn't have a var or val modifier, Scala doesn't generate a getter or a 
        // setter method for the field; it becomes private to the class.

        // Additionally, var and val fields can be modified with the private keyword 
        // which prevents public getters and setters from being generated.

        // var fields:
        class Person(var name: String)
        val p = Person("Mark Sinclair Vincent")
        // getter
        p.name // Mark Sinclair Vincent
        // setter
        p.name = "Wilma Brie"
        // 
        println(p.name) // "Wilma Brie"


        // val fields
        val p2 = Person("Jane Doe")
        // getter
        p2.name // "Jane Doe"
        // attempt to use setter
        // p.name = "Joey John J Doe"
        // this example ^^ fails because Scala doesn't generate a mutator(setter) method for val fields

        // private fields
        // fields with the private keyword do not generate a getter or setter method and can only be accessed
        // from within members of the class

        enum Role:
            case HumanResources, WorkerBee

        import Role.*

        class Employee(var name: String, private var salary: Double):
            def getSalary(r: Role): Option[Double] = r match
                case HumanResources => Some(salary)
                case _ => None
        
        val e = Employee("Steve Jobs", 1)

        // to access the salary field you have to use getSalary
        println(e.name) //Steve Jobs
        println(e.getSalary(WorkerBee)) //None
        println(e.getSalary(HumanResources)) //Some(1)
            
        // fields without var or val
        class SuperEncryptor(password: String):
            // encrypt increments each char in String by 1
            private def encrypt(s: String): String = s.map(c => (c + 1).toChar)
            def getEncryptedPassword = encrypt(password)
        
        val password = SuperEncryptor("password")
        //println(password.password) //value password cannot be accessed as a member of (password : SuperEncryptor)
        println(password.getEncryptedPassword) //qbttxpse

        // Case classes
        // Parameters in the constructor of a case class differ from these rules in one way:
            // case class constructor parameters are val by default.
        // So if you define a case class field without adding cal or var, like this:
        case class PersonC(name: String)
        // you can still access the field, just as if it were defined as a val:
        val pC = PersonC("Dale Cooper")
        println(p.name)


    }
    // Defining Auxiliary Constructors for Classes
    def definingAuxiliaryConstructorsForClasses(): Unit = {
        println("Defining Auxiliary Constructors for Classes")
        // Problem: You want to define one or more auxiliary constructors 
        // for a class so that consumers of the class can have multiple 
        // ways to create object instances
        enum CrustSize:
            case Small, Medium, Large
        
        enum CrustType:
            case Thin, Regular, Thick
        

        import CrustSize.*, CrustType.*
        // Primary Constructor
        class Pizza (var crustSize: CrustSize, var crustType: CrustType):
            // one-arg auxillary constructor
            def this(crustSize: CrustSize) = this(crustSize, Pizza.DefaultCrustType)

            // one-arg auxillary constructor
            def this(crustType: CrustType) = this(Pizza.DefaultCrustSize, crustType)

            // zero-arg auxillary constructor
            def this() = this(Pizza.DefaultCrustSize, Pizza.DefaultCrustType)

            override def toString = s"A $crustSize pizza with a $crustType crust"
        
        object Pizza:
            val DefaultCrustSize = Medium
            val DefaultCrustType = Regular
        

        import Pizza.{DefaultCrustType, DefaultCrustSize}
        // use the different constructors
        val p10 = Pizza(DefaultCrustSize, DefaultCrustType)
        val p20 = Pizza(DefaultCrustSize)
        val p30 = Pizza(DefaultCrustType)
        val p40 = Pizza

        println(p10)
        println(p20)
        println(p30)
        println(p40)


    }









}