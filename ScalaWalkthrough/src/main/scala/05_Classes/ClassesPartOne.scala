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
        // controllingTheVisibiltyOfConstructorFields()
        // definingAuxiliaryConstructorsForClasses()
        // definingAPrivatePrimaryConstructor()
        // providingDefaultValuesForConstructorParameters()
        // handlingConstructorParametersWhenExtendingAClass()
        // callingASuperclassConstructor()
        // definingAnEqualsMethod()
        // overridingDefaultAccessorAndMutators()
        // assigningABlockOrFunctionToALazyField()
        // settingUnitializedVarFieldTypes()
        // generatingBoilerplateCodeWithCaseClasses()
        definingAuxiliaryConstructorsForCaseClasses()
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
    // Controlling the Visiblity of Constructor Fields (var, val, private)
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
    // Defining Auxiliary Constructors for Classes (this())
    def definingAuxiliaryConstructorsForClasses(): Unit = {
        println("Defining Auxiliary Constructors for Classes")
        // Problem: You want to define one or more auxiliary constructors 
        // for a class so that consumers of the class can have multiple 
        // ways to create object instances

        // Auxiliary Constructors are a way for you to set up the class so that when you create
        // a new instance, you don't have to define all of the params for that class.  If you didn't
        // have auxillary Constructors, you have to input values for whatever class fields are in the class. 
        // //////////////////////////////////////////////////////////////////////////////////////////////////
        // Example 1:
        class Car (val color: String)
        //  VVVVVVVVVVVVVVVVVVVV
        // val civic = Car() //missing argument for parameter color of constructor Car in class Car: (color: String): Car
        // This results in an error however if we create an auxillary constructor for car

        object CarAC:
            val DefaultColor = "gray"
        
        class CarAC (val color: String):
            def this() = this(CarAC.DefaultColor)
        
        val civic2 = CarAC()
        println(civic2.color)

        // //////////////////////////////////////////////////////////////////////////////////////////////////
        // Example 2:
        object Cup:
            val DefaultColor = "white"
            val DefaultIsFull = false
            val DefaultLiquidType = "water"
            
        class Cup (
            val color: String,
            var isFull: Boolean, 
            var liquidType: String
        ):
            def this() = this(Cup.DefaultColor, Cup.DefaultIsFull, Cup.DefaultLiquidType)
            
            def this(color: String) = this(color, Cup.DefaultIsFull, Cup.DefaultLiquidType)
        
        val cup = Cup("red", true, "milk")
        println(cup.liquidType)

        val cup2 = Cup()
        println(cup2.liquidType)

        val cup3 = Cup("purple")
        println(cup3.liquidType)


        // //////////////////////////////////////////////////////////////////////////////////////////////////
        // Example 3:
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

        // Important notes:
            // Auxillary constructors are defined by creating methods named this
            // Each auxillary constructor must begin with a call to a previously defined constructor
            // Each constructor must have a different parameter list
            // One constructor calls another constructor using the method name this and
            //  specifies the desired parameters
        
    }
    // Defining A Private Primary Constructor
    def definingAPrivatePrimaryConstructor(): Unit = {
        println("Defining A Private Primary Constructor")
        // Problem: You want to make a primary constructor of a class private, 
        // such as to enforce the Singleton pattern

        // To make the primary constructor of a class private, insert the private keyword in between
        // the class name and any parameters the constructor accepts:
            // a private one-arg primary constructor
        class Person786 private (var name: String)
        // This keeps you from being able to create an instance of the class:
        // val person786 = new Person786("Names Nameson")   //constructor Person786 cannot be accessed as a member of Person786
        // "This is a class Person786 with a private constructor"

        // To enforce the Singleton pattern in Scala, make the primary constructor 'private'
        // and the create a getInstance method in the 'companion' object of the class

        // a private constructor that takes no parameters
        class Brain private:
            override def toString = "This is the brain"
        
        object Brain:
            val brain = Brain()
            def getInstance = brain
        
        def singletonTest =
            val brain = Brain.getInstance
            println(brain)
        singletonTest
        // you don't have to name the accessor method "getInstance", it's just a Java convention

        // Utility classes
        // depending on what you are trying to accomplish, you may not need to create a private constructor.
        // In some cases, you can do the same thing by putting methods in an object

        object FileUtils:
            def readfile(filename: String): String = ???
            def writefile(filename: String, contents: String): Unit = ???
        
        // This lets consumers of your code call those methods without
        //  needing to create an instance of the FileUtils class:
        
        // val contents = FileUtils.readFile("input.txt")
        // FileUtils.writeFile("output.txt", contents)

        // In a case like this, theres no need for a private class constructor; just don't define a class
    }
    // Providing Default Values for Constructor Parameters (instead of Auxiliary Constructors)
    def providingDefaultValuesForConstructorParameters(): Unit = {
        println("Providing Default Values for Constructor Parameters")
        // Problem: You want to provide a default value for a constructor parameter, 
        // which gives consumers of your class the option of specifying that parameter 
        // when calling the constructor or not

        class Socket (val timeout: Int = 10_000)

        // Because the parameter is defined with a default value, 
        // you can call the constructor without specifying a timeout calue,
        //  in which case you get the default value:
        val s = Socket()
        println(s.timeout)

        // This eliminates the need for auxillary constructors

        // Multiple Parameters
        class Socket2(val timeout: Int = 1000, val port: Int = 8080)
    }
    // Handling Constructor Parameters When Extending a Class
    def handlingConstructorParametersWhenExtendingAClass(): Unit = {
        println("Handling Constructor Parameters When Extending a Class")
        // Problem: You want to extend a base class that has constructor parameters,
        //  and your new subclass may take additional parameters

        // Working with val constructor parameters
        // first, define a base class
        class Person(val name: String)

        // Next, define a subclass
        class Employee(name: String, val age: Int) extends Person(name):
            override def toString = s"${name} is ${age} years old"
            def apply() = s"${name}: ${age}"
        
        val joe = Employee("Joe", 34)
        println(joe) // Joe is 34 years old
        println(joe()) // Joe: 34
        // This works because the fields are immutable

        // Extending var fields
        // 2 options:
            // Use a different name for the field in the subclass
            // Implement the subclass constructor as an apply method in a companion object
        
        class Persona(var name: String)

        // note the use of '_name' 
        class Employa(_name: String, var age: Int) extends Persona(_name)
        // ****** So, when extending a class that has a var constructor parameter,
        // use a different name for that field in the subclass

        // Solution 2 /////////////////////////////////////////////////////////////////
        // Use an apply method in a companion object

        class Person2(var name: String):
            override def toString = s"$name"
        
        // now create the employee class with a private constructor
        class Employee2 private extends Person2(""):
            var age = 0
            println("Employee Constructor Called")
            override def toString = s"$name is $age"

        // companion object Employee2
        object Employee2:
            def apply(_name: String, _age: Int) =
                val e = new Employee2()
                e.name = _name
                e.age = _age
                e
        
        val jane = Employee2("JANE", 32)

        print(jane)

    }
    // Calling a Superclass Constructor
    def callingASuperclassConstructor(): Unit = {
        println("Calling a Superclass Constructor")
        // Problem: You want to control the superclass constructor that's called
        // when you define constructors in a subclass.


    }
    // Defining an equals Method(Object Equality) in a New Class (With and Without Inheritance)
    def definingAnEqualsMethod(): Unit = {
        println("Defining an equals Method(Object Equality)")
        // Problem: You want to define an equals method for a class 
        // so you can compare object instances to each other

        // Scala uses "==" to compare instances, unlike java.
        // "==" method is defined on the "Any" class so (a) its inherited by all other 
        // classes and (b) it calls the 'equals' method that's defined for the class.
        // What happens is that when you write 1 == 2, that code is the same as writing 1.==(2), 
        // and then that == method invokes the equals method on the 1 object, 
        // which is an instance of Int in this example.

        // "foo" == "foo" //true
        // "foo" == "bar" //false
        // "foo" == null //false
        // null == "foo" //false
        // 1 == 1 //true
        // 1 == 2 //false
        
 
        // dont implement equals method unless necessary
        
        // Why is this important?
        // Because Scala doesn't recognize 2 instances of a class as equal if all their parameters are equal,
        // like a human would intuitively think.  If we want to be able to compare two instances of a class,
        // and consider them equal if their parameters are the same(for logic in our program or something else), 
        // then we need to write an equals method for the 
        // class

        // 7-Steps to implementing an equals method for a class in Scala
        // 1. Create a canEqual method with the proper signature, taking an Any parameter and returning a boolean.
        // 2. canEqual should return true if the arguement passed into it is an instance of the current class , false otherwise.
        // 3. Implement the equals method with the proper signature, taking an Any parameter and returning a Boolean.
        // 4. Write the body of equals as a single match expression.
        // 5. The match expression should have two cases, As you'll see in the following code, 
        //      the first case should be a typed pattern for the current class.
        // 6. In the body of this first case, implement a series of logical "and" tests for all the tests in this class 
        //      that must be true. If this class extends anything other than AnyRef, you'll want to invoke 
                // your superclass equals method as part of these tests. One of the "and" tests must also be a call to canEqual.
        // 7. For the second case, just specify a wildcard pattern that yields false.


        class Person1 (var name: String, var age: Int)
        val person1 = Person1(name="John", age=20)
        val person2 = Person1(name="John", age=20)
        // Human logic says person1 == person2 = true
        println(person1 == person2) //false
        // Nope, see you're not thinking like a computer.
        // That's why we have to write an equals method for 
            // any class we want to use human logic in our program.

        // here's how...
        class Person2 (var name: String, var age: Int):
            // step 1: prepare signature for 'canEqual'
            // Step 2: compare 'a' to the current class
            // (isInstanceOf returns true or false)
            def canEqual(a: Any): Boolean = a.isInstanceOf[Person2]
            // step 3: proper signature of 'equals'
            // step 4 thru 7: implement a 'match' expression
            override def equals(that: Any): Boolean = 
                that match 
                    case that: Person2 => 
                        that.canEqual(this) &&
                        this.name == that.name &&
                        this.age == that.age
                    case _ => 
                        false
            // step 8 (optional): implement a corresponding hashCode method
            override def hashCode: Int =
                val prime = 31
                var result = 1
                result = prime * result + age
                result = prime * result + (if name == null then 0 else name.hashCode)
                result
        end Person2

        val personA = Person2(name="John", age=20)
        val personB = Person2(name="John", age=20)
        println(personA == personB) // true

        // Scaladocs for equals: "https://oreil.ly/mOc4r"
        println(personA.getClass())

        // Example 2:
        // How to do the same thing when inheritance is involved
        class Employee (name: String, age: Int, var role: String) 
        extends Person2(name, age):
            override def canEqual(a: Any): Boolean = a.isInstanceOf[Employee]
            override def equals(that: Any): Boolean =
                that match
                    case that: Employee => 
                        that.canEqual(this) &&
                        this.role == that.role &&
                        super.equals(that)
                    case _ => 
                        false
            override def hashCode(): Int =
                val prime = 31
                var result = 1
                result = prime * result + (if role == null then 0 else role.hashCode)
                result + super.hashCode
        end Employee

        // Beware equals methods with var fields and mutable collections
        val eNimoy = Employee("Leonard Nimoy", 81, "Actor")
        val set = scala.collection.mutable.Set[Employee]()
        set += eNimoy
        println(set.contains(eNimoy)) // true
        eNimoy.age = 82
        println(set.contains(eNimoy)) // false
        // ^^^ In this case ^^^
        // You should override hashCode and should name your equality method 
        // something else so that you still have use of the default equality method 
        // that would work in this case

        // further reading: Programming in Scala: Martin Odersky et al.
    }
    // Preventing Accessor and Mutator Methods from Being Generated
    def preventingAccessorAndMutatorMethodsFromBeingGenerated(): Unit = {
        // Problem: When you define a class field as a var, Scala automatically generates 
        // accessor (getter) and mutator (setter) methods for it, and defining a field as a val 
        // automatically generates an accessor method, but you don't want either 
        // an accessor or a mutator

        // The solution is to either 1) add the 'private' access modifier to the val or var declaration so it 
        // can only be accessed by instances of the current class
        // 2) add the potected access modifier so it can be accessed by classes that extend the current class

        // The private modifier
        class Animal:
            private var _numLegs = 2
            def numLegs = _numLegs    // getter

            def numLegs_=(numLegs: Int): Unit =  //setter
                _numLegs = numLegs
            // Note we can access the '_numLegs' fieldof another Animal instance 'that'
            def iHaveMoreLegs(that: Animal): Boolean =
                this._numLegs > that._numLegs            
   
        // // The protected modifier
        // class Dog extends Animal:
        //     _numLegs = 4
        
        // numLegs still can't be accessed from outside the class   
    }
    // Overriding Default Accessors and Mutators
    def overridingDefaultAccessorAndMutators(): Unit = {
        // Problem: You want to override the getter or setter methods that Scala generates for you
    }
    // Assigning a Block or Function to A (Lazy) Field
    def assigningABlockOrFunctionToALazyField(): Unit = {
        // Problem: You want to initailize a field in a class using a block of code, 
        // or by calling a method or function
        import scala.io.Source
        class FileReader(filename: String):
            // assign this block of code to the 'text' field
            lazy val text = 
                // 'fileContents' will either contain the file contents,
                // or the exception message as a string
                val fileContents = 
                    try
                        Source.fromFile(filename).getLines.mkString
                    catch
                        case e: Exception => e.getMessage
                println(fileContents) //print the contents
                fileContents // return the contents
        
        val reader = FileReader("/etc/passwd")
        reader.text


    }
    // Setting Uninitialized var Field Types
    def settingUnitializedVarFieldTypes(): Unit = {
        // Problem: You want to set the type for an uninitialized 
        // var field in a class, so you begin to write code like this:
            // var x =
        // and then wonder how to finish writing the expression

        // In general, the best approach is to define the field as an Option
        // for certain types (String and numeric fields, 
        // you can specify default initial values.)

        case class Person(var username: String, var password: String):
            var age = 0
            var firstName = ""
            var lastName = ""
            var address: Option[Address] = None
        

        case class Address(city: String, state: String, zip: String)

        val p = Person("john123", "secret")
        
        p.address = Some(Address("Talo", "AR", "75098"))

        p.address.foreach( a =>
            println(s"${a.city}, ${a.state}, ${a.zip}")
        )

    }
    // 5.14 Generating Boilerplate Code with Case Classes
    def generatingBoilerplateCodeWithCaseClasses(): Unit = {
        // You're working with match expressions, Akka actors, or other situations 
        // where you want to use the case class syntax to generate boilerplate code, 
        // including accessor and mutator methods, along with apply, unapply, toString, 
        // equals, and hashCode methods, and more.

        // When you want you class to have many additional built-in features- 
        // such as creating classes in functional programming -
        //  define your class as a case class, declaring any 
        // parameters it needs in its constructor:
        
        // name and relation are "val" by default
        case class Person(name: String, relation: String)
        // case classes come with the following benefits:
            // 1- Accessor methods are generated for the constructor parameters because 
            // case class constructor parameters are val by default. Mutator methods are also 
            // generated for parameters that are declared as var.

            // 2- A good default toString method is generated

            // 3- An unapply method is generated, making it easy to use case classes in match expressions.

            // 4- equals and hashCode are generated, so instances can easily be compared and used in collections.

            // 5- A copy method is generated, which makes it easy to create new instances from existing instances 
                // (a technique used in functional programming)

        val p1 = Person("John", "Parent")
        val p2 = Person("John", "Parent")
        println(p1 == p2) // true

        println(p1.toString) //Person(John,Parent)

        // The copy method is useful in FP because its helpful when you
        // need to clone an object and change some of the fields during the cloning process.
        case class Dog(name: String, color: String)
        val violet = Dog("Violet", "chocolate")
        val brown = violet.copy(name="Brown", color="brown")
        println(violet) //Dog(Violet,chocolate)
        println(brown) //Dog(Brown,brown)
        //  ^^ this technique is commonly used in FP, "update as you copy"

        
        val red = violet.copy(name="Big Red")
        println(red.name)

        // Just playing with matches below (:\)
        violet match
            case Dog(n, c) => println("true")
        
        violet match 
            case v if violet.name == "Violet" => println(s"Hey Violet! ${v}")
            case _ => println("Everything else")
        

        // It's important to note that while case classes are very convienient, 
        // there isn't anything in them that you couldn't code yourself.

    }
    // Defining Auxiliary Constructors for Case Classes
    def definingAuxiliaryConstructorsForCaseClasses(): Unit = {
        // Problem: Similar to the previous recipe, 
        // you want to define one or more auxiliary constructors 
        // for a case class rather than a plain class

        // initial case class
        case class Person(var name: String, var age: Int)
        val p = Person("John Smith", 30)
        // this ^^ is the same as this vv
        val p2 = Person.apply("John Smith", 30)

        
        // So to create default constructor values, you can create a companion
        // object and use the apply method
    
        case class Person2(name: String, age: Int)
        object Person2:
            def apply() = new Person2("default", 0)
            def apply(name: String) = new Person2(name, 0)
        
        val a = Person2() //Person2(default,0)
        val b = Person2("John") //Person2(John,0)
        val c = Person2("John", 20) //Person2(John,20)
        println(a) 
        println(b)
        println(c)

    }






}