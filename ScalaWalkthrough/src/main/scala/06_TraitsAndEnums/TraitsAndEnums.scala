object TraitsAndEnums extends App {
    def main(): Unit = {
        println("Traits And Enums")
        // introduction()
        // traitConstructionOrder()
        // usingATraitAsAnInterface()
        // definingAbstractFieldsInTraits()
        // usingATraitLikeAnAbstractClass()
        // usingTraitsAsMixins()
        // resolvingMethodNameConflictsAndUnderstandingSuper()
        // markingTraitsSoTheyOnlyCanBeUsedBySubclassesOfACertainType()
        // ensuringATraitCanOnlyBeAddedToATypeThatHasASpecificMethod()
        // limitingWhichClassesCanUseATraitByInheritance()
        // workingWithParameterizedTraits()
        // usingTraitParameters()
        // usingTraitsToCreateModules()
        // howToCreateSetsOfNamedValuesWithEnums()
        modelingAlgebraicDataTypesWithEnums()


    }
    main()
    // Introduction
    // A look at Traits as Interfaces from Java
    def introduction(): Unit = {
        // A Brief Introduction to Traits
        println("Introduction: Traits and Enums")

        trait Pet:
            def speak() = println("yo") // concrete implementation
            def comeToMaster(): Unit // abstract method
        
        trait HasLegs:
            def run() = println("I'm running!") //concrete method
        
        class Dog extends Pet, HasLegs:
            def comeToMaster(): Unit = println("I'm coming!")

        // Now, when you create a new dog instance and call its methods,
        // you'll see output like this:
        val d = new Dog()
        d.speak()
        d.comeToMaster()
        d.run()
        
        // That's a small glimpse of some basic trait as an interface features. 
        // This is one way of mixing in multiple traits to create a class
    }
    // Trait Construction Order
    def traitConstructionOrder(): Unit = {
        // Order of trait construction when mixed to create a class:
        trait First:
            println("First is constructed")
        trait Second:
            println("Second is constructed")
        trait Third:
            println("Third is constructed")
        
        class MyClass extends First, Second, Third:
            println("MyClass is Constructed")
        val c = new MyClass

        // OUT: 
            // First is constructed
            // Second is constructed
            // Third is constructed
            // MyClass is Constructed

        // Traits are constructed in order from left to right
        // and then the class itself is constructed

    }
    // Using a Trait as an Interface
    def usingATraitAsAnInterface(): Unit = {
        // Problem: You're used to creating pure interfaces in other languages- 
        // declaring method signatures without implementation- and want to create 
        // something like that in Scala and then use those interfaces with concrete classes.

        // At their most basic level, Scala traits can be used like pre Java 8 interfaces, 
        // where you define method signatures but don't provide an implementation for them

        trait HasTail:
            def startTail(): Unit
            def stopTail(): Unit
        
        // These methods dont take parameters, 
        // if the methods you want to define will take parameters, declare them as usual.
        trait HasLegs:
            def startRunning(speed: Double): Unit
            def runForNSeconds(speed: Double, numSeconds: Int): Unit
        
        // Extending Traits
        // When you want to create a class that extends a trait use the extends keyword
        abstract class DogSingleTrait extends HasTail
        // To extend multiple traits, use commas in between traits
        abstract class DogMultipleTraits extends HasTail, HasLegs
        // If a class extends a trait but doesnt implement all of its abstract methods,
        // the class must be declared abstract
        abstract class DogAbstract extends HasTail, HasLegs
            // Does not implement methods from HasTail or HasLegs so it must be declared abstract
        // But if the class provides an implementation for all the abstract methods of the trait
        // it extends, it can be declared as a normal class:
        class Dog extends HasTail, HasLegs:
            def startTail(): Unit = println("Tail is wagging")
            def stopTail(): Unit = println("Tail is Stopped")
            def startRunning(speed: Double): Unit = 
                println("Running at ${speed} miles/hour")
            def runForNSeconds(speed: Double, numSeconds: Int): Unit = 
                println("Running at ${speed} miles/hour for ${numSeconds} seconds")
        
        // At its most basic implementation, traits can act as interfaces. 
        // Classes then extend traits following these rules:
        // -if a class extends one trait, use the extends keyword.
        // -if a class extends multiple traits, use the extends keyword for the first trait,
        //      and separate the rest with commas
        // -If a class extends a class (or abstract class) AND a trait, 
            // always list the class name first- using extends before the class name- 
            // and then use commas before the additional trait names.
        
        // Traits can also extend other traits:
        trait SentientBeing:
            def imAlive_!(): Unit = println("I'm alive!")
        trait Furry
        trait Dog2 extends SentientBeing, Furry

    }
    // Defining Abstract Fields in Traits
    def definingAbstractFieldsInTraits(): Unit = {
        // Problem: You want to declare that a trait should have a field, 
        // but you don't want to give the field an initial value, i.e., 
        // you want it to be abstract

        // The simplest and most flexible way to define abstract fields 
        // in traits is to use a 'def':
        trait PizzaTrait:
            def maxNumToppings: Int
        // This lets you override the field in the classes (and traits) that extend 
        // your trait in a variety of ways, including as a  val:
        class SmallPizza extends PizzaTrait:
            val maxNumToppings = 4
        // As a lazy val:
        class SmallPizza2 extends PizzaTrait:
            lazy val maxNumToppings = 
                // Some long-running operation
                Thread.sleep(1_000)
                4
        // As a var:
        class MediumPizza extends PizzaTrait:
            var maxNumToppings = 6
        // Or as a def:
        class LargePizza extends PizzaTrait:
            def maxNumToppings: Int =
                // Some algorithm here
                42
        
        // CONCRETE FIELDS in traits
        trait SentientBeing:
            var uuid = 0 // concrete
        
        class Person extends SentientBeing:
            uuid = 1
        
        // Similarly, if you define a trait field as a concrete val, 
        // you'll need to use the override modifier to change that 
        // value in an extending class:
        trait Cat:
            val numLives: Int = 9
        
        class BetterCat extends Cat:
            override val numLives = 10
        
        // In both cases, you can't implement those fields 
        // as def or lazy val values in your classes

        // further reading, If you dare: https://oreil.ly/ID6C3 

    }
    // Using a Trait like an Abstract Class
    def usingATraitLikeAnAbstractClass(): Unit = {
        // Problem: You want to use a trait like an abstract class in Java, 
        // defining both abstract and concrete methods.
        trait Pet:
            def speak() = println("Yo") // concrete implementation
            def comeToMaster(): Unit  //abstract method
        
        class Dog extends Pet:
            // no need to implement 'speak' if you don't want to
            def comeToMaster(): Unit = println("I'm coming!")
        
        class Cat extends Pet:
            override def speak() = println("meow")
            def comeToMaster() = println("That's not gonna happen")
        
        // If a class extends a trait without implementing the trait's abstract methods,
        // it must be declared as abstract. Because FlyingPet doesn't implement Pet's
        // comeToMaster method, it must be declared as an abstract class
        abstract class FlyingPet extends Pet:
            def fly() = println("Woo-hoo, I'm flying!")
        // Although Scala has abstract classes, its much more common to use traits than abstract
        // classes to implement base behavior. A class can only extend one abstract class, but
        // it can implement multiple traits, so using traits is more flexible
        val d = Dog()
        d.speak() //"Yo"
        val c = Cat()
        c.speak() //"meow"
        // Pg 136 for when you "should" use an abstract class instead of a trait
    }
    // Using Traits as Mixins
    def usingTraitsAsMixins(): Unit = {
        // You want to design a solution where one or more traits 
        // can be mixed into a class to provide a robust design.

        // can be done in at least two ways:
            // Constructing a class with traits
            // Mix in traits during variable construction
        // A first approach is to create a class while extending one or more traits
        trait HasTail:
            def wagTail() = println("Tail is swagging")
            def stopTail() = println("Tail is stopped")
        
        trait Pet:
            def speak() = println("Yo") //
            def comeToMaster(): Unit  //abstract
        
        class Dog(val name: String) extends Pet, HasTail:
            def comeToMaster() = println("Woo-hoo, I'm coming!")
        
        val d = Dog("Zeus")

        class Cat(val name: String) extends Pet:
            def comeToMaster() = println("That's not gonna happen.")
            override def speak() = println("meow")
        
        val c = Cat("Morris")
        // Second mixin approach is to add traits to a class at the same time 
        // as you create a variable. Imagine that you now have these three 
        // traits (which have no methods) and a Pet class:
        trait HasLegs2:
            def speak(): Unit = println("HasLegs2")
        trait HasTail2
        trait MansBestFriend2
        class Pet2(val name: String)

        val zeus = new Pet2("zeus") with MansBestFriend2 with HasTail2 with HasLegs2
        // you can create other variables by mixing in the traits that make sense:
        val cat = new Pet2("Morris") with HasTail2 with HasLegs2

        zeus.speak() //HasLegs2



    }
    // Resolving Method Name Conflicts and Understanding Super With Mixin Traits
    def resolvingMethodNameConflictsAndUnderstandingSuper(): Unit = {
        // You attempt to create a class that mixes in multiple traits, 
        // but those traits have identical method names and parameter lists, 
        // resulting in a compiler error.

        trait Hello:
            def greet = "hello"

        trait Hi:
            def greet = "hi"
        // now if you try to create a Greeter trait that mixes in both of these traits-

        //class Greeter extends Hello, Hi //
        //error overriding method greet in trait Hello of type => String;
        //(Note: this can be resolved by declaring an override in class Greeter.)
        // 3 solutions:
            
        //1) override greet with custom behavior /////////////////
        // resolve the conflict by overriding 'greet' in the class
        class Greeter extends Hello, Hi:
            override def greet = "I greet thee!"
        
        // the 'greet' method override works as expected
        val g = Greeter() //true
        
        // 2) Invoke greet using super /////////////////////////////
        trait Parent:
            def speak = "make your bed"
        
        trait Granddad:
            def speak = "get off my lawn"
        // resolve the conflict by calling super.speak
        class Speaker extends Parent, Granddad:
            override def speak = super.speak
        // But what does super.speak() print?
        println(Speaker().speak) //get off my lawn
        //  --> super will always refer to the last trait that is mixed in.
        // (if no mixed in relationship between the traits themselves)
        // This is known as back-to-front linearization order
        
        // 3) Control Which Super you Call /////////////////////////////
        trait Bing:
            def x = "bing"
        trait Bang:
            def x = "bang"
        trait Bong:
            def x = "bong"

        class Bazoom extends Bing, Bang, Bong:
            override def x = super.x
            def xBing = super[Bing].x
            def xBang = super[Bang].x
            def xBong = super[Bong].x
        end Bazoom

        val bb = Bazoom()
        println(bb.xBing) //bing
        println(bb.xBang) //bang
        println(bb.xBong) //bong
        
        // Naming conflicts occur when the method names are the same and the parameter lists
        // are identical
        trait A:
            def function(a: Int, b: Int): Int = 1
        
        trait B:
            def function(): Int = 2
        
        class C extends A, B

        val u = C()
        println(u.function(1, 1)) //1
        println(u.function()) //2

        // traits can also be combined with a technique known as stackable modifications
    }
    // Marking Traits So They Can Only Be Used By Subclasses of A Certain Type *(class, abstract class, or trait)*
    def markingTraitsSoTheyOnlyCanBeUsedBySubclassesOfACertainType(): Unit = {
        // Problem: You want to mark your trait so it can only be 
        // used by types that extend a given base type

        // A self type of a trait is the assumed type of "this"

        // To make sure a trait named MyTrait can only be mixed into a class 
        // that is a subclass of a type named BaseType,
        // begin your trait with this syntax:
        // trait MyTrait:
        //     this: BaseType =>
        
        // for example to make sure a StarFleetWarpCore can only be mixed into 
        // a class that also mixes in FederationStarship, begin the StarFleetWarpCore 
        // like this:
        trait StarFleetWarpCore:
            this: FederationStarship =>
        
        trait FederationStarship
        class Enterprise extends FederationStarship, StarFleetWarpCore //compiles

        // These are called self-types

        // Example 2:
        trait HasLegs

        trait CanRun:
            this: HasLegs => // this is how you use dependency injection (CanRun requires HasLegs in order to work)
                def run(): Unit = println("running")
    

        class Dog extends HasLegs, CanRun:
            def whatAmI(): Unit =
                if this.isInstanceOf[Dog] then println("Dog")
                if this.isInstanceOf[HasLegs] then println("HasLegs")
                if this.isInstanceOf[CanRun] then println("CanRun")
        val dogg = Dog()
        dogg.whatAmI()
        dogg.run()
        // https://oreil.ly/v7FEp

        // Takeaway:
            // The important part to remember is that when you define a self-type like this:
                // trait CanRun:
                    // this: HasLegs =>
            // the key is that CanRun knows that when a concrete instance of it is eventually created,
            // 'this' in that concrete instance can respond "Yes, I am also an instance of HasLegs"
        
        // A trait can call methods on the required type
        // A great feature of this approach is that because the trait knows that the other type must 
        // be present, it can call methods that are defined in that other type. For instance,
        // if you have a type named HasLegs with a method named numLegs:
        trait HasLegs2:
            def numLegs = 0

        trait CanRun2:
            this: HasLegs2 =>
                def run() = println(s"I have ${numLegs} legs and I'm running")

        // Because CanRun known that HasLegs must be present when CanRun is mixed in,
        // it can safely call the numLegs method( or any other method) of HasLegs

        // Now when we create a Dog class with HasLegs and CanRun
        class Dog2 extends HasLegs2, CanRun2:
            override val numLegs = 4
        
        val d = Dog2() 
        d.run() //I have 4 legs and I'm running

        // REQUIRING MULTIPLE OTHER TYPES BE PRESENT
        trait WarpCore:
            this: FederationStarship & WarpCoreEjector & FireExtinguisher =>
                def eject() = println(s"Ejecting in ${secondsToEject} seconds")
        
        trait WarpCoreEjector:
            val secondsToEject = 10
        trait FireExtinguisher

        class Enterprise2 extends FederationStarship, FireExtinguisher, WarpCoreEjector, WarpCore

        val e = Enterprise2()
        e.eject() //Ejecting in 10 seconds

    }
    // Ensuring A Trait Can Only Be Added to A Type *(class, abstract class, or trait)* That Has A Specific Method
    def ensuringATraitCanOnlyBeAddedToATypeThatHasASpecificMethod(): Unit = {
        // Problem: You only want to allow a trait to be mixed into a type (class, abstract class, or trait) 
        // that has a specific method with a given signature

        // Solution: use a variation of the self-type syntax that lets you declare that any class 
        // that attempts to mix in the trait must implement the method you describe.

        // Example:
        trait WarpCore:
            this: { 
                // an implementing class must have methods with
                // these names and input parameters...
                def ejectWarpCore(password: String): Boolean 
                def startWarpCore(): Unit
            } => 
                // more trait code here
        

        class Starship

        class Enterprise extends Starship, WarpCore:
            def ejectWarpCore(password: String): Boolean = 
                if password == "password" then
                    println("core ejected")
                    true
                else
                    false
                end if
            end ejectWarpCore

            def startWarpCore() = println("core started")
        
        // This approach is known as a structural type., because you are limiting what classes the trait 
        // can be mixed into by stating that the class must have a certain structure.
        val enterprise = Enterprise()
        enterprise.ejectWarpCore("password") //true

    }
    // Limiting Which Classes Can Use a Trait By Inheritance
    def limitingWhichClassesCanUseATraitByInheritance(): Unit = {
        // Problem: You want to limit a trait so it can only be added to classes that extend a specific superclass

        // Use the following syntax to declare a trait named TraitName, where TraitName can only be mixed
        //  into classes that extend a type named SuperClass, where SuperClass may be a class or an abstract class:
            // trait TraitName extends SuperClass

        // For example, in modeling a large pizza store chain that has a corporate 
        // office and many small retail stores, the legal department creates a rule that 
        // people who deliver pizzas to customers must be a subclass of StoreEmployee and 
        // cannot be a subclass of CorporateEmployee. To enforce this, begin by 
        // defining your base classes:

        trait Employee
        class CorporateEmployee extends Employee
        class StoreEmployee extends Employee

        // because someone who delivers food can only be a StoreEmployee, you enforce this
        // requirement in the DeliversFood trait:
        trait DeliversFood extends StoreEmployee

        // Now you can define a DeliveryPerson class
        // class DeliveryPerson extends StoreEmployee, DeliversFood

        // But because the DeliversFood trait can only be mixed into classes that extend StoreEmployee,
        // the above line of code won't compile, which is what we want.


    }
    // Working With Parameterized Traits
    def workingWithParameterizedTraits(): Unit = {
        // Problem: As you become more advanced in working with types *(classes, abstract classes, or traits)*, 
        // you want to write a trait whose methods can be applied to generic types,
        // or limited to other specific types

        // Depending on your needs you can use "type parameters" or "type members" with traits 
        // whose methods can be applied to generic types, or limited to other specific types

        // trait Stringify[A]:
        //     def string(a: A): String
        
        // This example shows what a type member looks like:
        // trait Stringify:
        //     type A
        //     def string(a: A): String

        // Complete "type parameter" example:  **************
        trait Stringify[A]:
            def string(a: A): String = s"value: ${a.toString}"
            
        object StringifyInt extends Stringify[Int]
        println(StringifyInt.string(100))  //value: 100


        // Same example using a "type member":
        trait Stringify2:
            type A
            def string(a: A): String
        
        object StringifyInt2 extends Stringify2:
            type A = Int
            def string(i: Int): String = s"value: ${i.toString}"
        println(StringifyInt2.string(42))  //value: 42

        trait Pair[A, B]:
            def getKey: A
            def getValue: B
        // That demonstrates the use of two generic parameters in a small trait example.


        // An advantage of parameterizing traits is that you can prevent 
        // things from happening that should never happen. 
        // For instance,
        // given this trait and class hierarchy:
        sealed trait Dog
        class LittleDog extends Dog
        class BigDog extends Dog

        // you can define another trait with a type member like this:
        trait Barker:
            type D <: Dog  //type member
            def bark(d: D): Unit
        
        object LittleBarker extends Barker:
            type D = LittleDog
            def bark(d: D) = println("wuf")
        
        object BigBarker extends Barker:
            type D = BigDog
            def bark(d: D) = println("WUF")
        
        // Now when you create these instances:
        val terrier = LittleDog()
        val husky = BigDog()

        LittleBarker.bark(terrier) //wuf
        BigBarker.bark(husky) //WUF

        // and this wont compile, as expected:
        // BigBarker.bark(terrier)

        // This demonstrates how a type member can declare a base type in the initial trait, 
        // and how more specific types can be applied in the traits, classes, 
        // and objects that extend that base type

        
    }
    // Using Trait Parameters
    def usingTraitParameters(): Unit = {
        // Problem: In Scala 3, you want to create a trait that takes one or more parameters, 
        // in the same way that a class or abstract class takes constructor parameters.
        // Scala 3 Docs: https://oreil.ly/loZU3

            //1     If a class C extends a parameterized trait T, 
            // and its superclass does not, C must pass arguments to T.

            //2     If a class C extends a parameterized trait T, 
            // and its superclass does as well, C must not pass arguments to T.

            //3      Traits must never pass arguments to parent traits.

        trait Pet(val name: String):
            def msg = s"How are you, Miss ${name}?"
        
        class A extends Pet("Violet")

        val violet = A()
        println(violet.msg)

        
        trait Greeting(val name: String):
            def msg = s"How are you, $name"

        class C extends Greeting("Bob"):
            println(msg)

        // class D extends C, Pet("Bill") // error: parameter passed twice

        trait FormalGreeting extends Greeting:
            override def msg = s"How do you do, $name"

        // class E extends FormalGreeting // error: missing arguments for `Greeting`.

        class E extends Greeting("Bob"), FormalGreeting

        // Traits With Context Parameters
        case class ImpliedName(name: String):
            override def toString = name

        trait ImpliedGreeting(using val iname: ImpliedName):
            def msg = s"How are you, $iname"

        trait ImpliedFormalGreeting extends ImpliedGreeting:
            override def msg = s"How do you do, $iname"

        
        trait Pet2(val name: String)

        // a class can extend a trait with a parameter
        class Dog(override val name: String) extends Pet2(name):
            override def toString(): String = s"dog name: $name"
        
        // use the Dog class
        val d = Dog("Fido")

        // Later in your code, another class can also extend the Dog class
        class SiberianHusky(override val name: String) extends Dog(name)

        // In a world where all cats are named "Morris," a class can extend
        // a trait with parameters like this:
        class Cat extends Pet2("Morris"):
            override def toString = s"Cat: $name"
            
        
        // use the Cat class
        val c = Cat()
        println(c.toString)

        // ONE TRAIT CAN EXTEND ANOTHER, WITH LIMITS:
        // trait Pet(val name: String):
        //     def msg = s"How are you, Miss ${name}?"
        // Dont pass the parameter when extending a trait with parameters
        trait FeatheredPet extends Pet

        // Then, when a class later extends FeatheredPet, the correct approach is this:
        class Bird(override val name: String) extends Pet(name), FeatheredPet:
            override def toString = s"bird name: $name"
        
        // create a new Bird"
        val b = Bird("Tweety")

    }
    // Using Traits to Create Modules
    def usingTraitsToCreateModules(): Unit = {
        // Problem: You've heard that traits are "the way" to implement modules in Scala,
        // and you want to understand how to use them in this manner.

        // Imagine that you've defined a trait to implement a method that adds two integers
        trait AddService:
            def add(a: Int, b: Int) = a + b
        
        // The basic technique to create a module is to create a singleton object from that trait.
        // The syntax for doing this:
        object AddService extends AddService

        // You can do this without implementing methods in the object because the add method
        // in the trait is concrete
        // Note: the process of implementing an abstract method in an object that extends the trait
        // is known as "reifying the trait", think of it at "real-ifying the trait".

        // The way you use the AddService module- a singleton object- in the rest of your code looks like this:
        // import AddService.*
        // println(add(1, 1)) //2

        // Another example:
        trait MultiplyService:
            def multiply(a: Int, b: Int) = a * b
        
        object MathService extends AddService, MultiplyService

        // The rest of your application uses this module in the same way

        import MathService.*
        println(add(2, 2)) //4
        println(multiply(3, 2)) //6

        // While these examples are simple, they demonstrate the essence of the technique:
            // 1. Create traits to model small, logically grouped areas of the business domain.
            // 2. The public interface of those traits contains only pure functions
            // 3. When it makes sense, mix those traits together into larger logical groups,
                // such as MathService
            // 4. Build Singleton objects from those traits (reify them)
            // 5. Use the pure functions from those objects to solve problems
        
        
        

        // Code for it
        // First, start by creating some pizaa-related ADTs using the Scala 3 enum construct:
        enum CrustSize:
            case Small, Medium, Large
        
        enum CrustType:
            case Thin, Thick, Regular
        
        enum Topping:
            case Cheese, Pepperoni, Olives
        
        // Next, create a Pizza class in a functional style - meaning that its a case class
        // with immutable fields
        case class Pizza(
            crustSize: CrustSize,
            crustType: CrustType,
            toppings: Seq[Topping]
        )

        // This approach is similar to using a 'struct' in other 
        // programming languages like C, Rust, Go

        // create a class for Order:
        case class Order(items: Seq[Pizza])

        // This example also handles the concept of a database, so I create
        // a database 'interface' (Scala trait in this case) that looks like this
        trait OrderDao:  //interface
            def addItem(p: Pizza): Unit
            def getItems: Seq[Pizza]

        // create a mock data access object (DAO) for testing purposes that
        // simply stores items in an ArrayBuffer in memory:
        
        trait MockOrderDao extends OrderDao:   // mock database implementation from OrderDao interface
            import scala.collection.mutable.ArrayBuffer
            private val items = ArrayBuffer[Pizza]()

            def addItem(p: Pizza) = items += p
            def getItems: Seq[Pizza] = items.toSeq

        // to make things a little more complex lets create a separate log every time we 
        // create a receipt. 
        // Same Pattern - first create an interface, then the implementation of that interface
        trait Logger:
            def log(s: String): Unit
        
        // implementation of the above interface
        trait ConsoleLogger extends Logger:
            def log(s:String) = println(s"LOG: ${s}")

        // Other implementations might include a FileLogger, DataBaseLogger, etc.

        // only thing left in this example is to create an OrderController
        trait OrderController:
            this: Logger =>         // declares a self-type
            def orderDao: OrderDao  // abstract

            def addItemToOrder(p: Pizza) = orderDao.addItem(p)
            def printReceipt(): Unit =
                val receipt = generateReceipt
                println(receipt)
                log(receipt)  // from logger
            
            // this is an example of a private method in a trait
            private def generateReceipt: String = 
                val items: Seq[Pizza] = for p <- orderDao.getItems yield p
                s"""
                YOUR ORDER
                ----------
                ${items.mkString("\n")}""".stripMargin
        

        // A LARGER EXAMPLE:
        import CrustSize.*
        import CrustType.*
        import Topping.*

        // Create some mock objects for testing
        object MockOrderDao extends MockOrderDao
        object MockOrderController extends OrderController, ConsoleLogger:
            // specifiy a concrete instance of an OrderDoa
            val orderDao = MockOrderDao
        
        val smallThinCheesePizza = Pizza(
            Small, Thin, Seq(Cheese)
        )

        val largeThickWorks = Pizza(
            Large, Thick, Seq(Cheese, Pepperoni, Olives)
        )

        MockOrderController.addItemToOrder(smallThinCheesePizza)
        MockOrderController.addItemToOrder(largeThickWorks)
        MockOrderController.printReceipt()

    }
    // How to Create Sets of Named Values with Enums
    def howToCreateSetsOfNamedValuesWithEnums(): Unit = {
        // Problem: You want to create a set of constants to model something in the world,
        // such as directions (north, south, west, east), positions on a display (top, bottom, left , right)
        // toppings on a pizza, and other finite sets of values.

        enum CrustSize:
            case Small, Medium, Large
        
        enum CrustType:
            case Thin, Thick, Regular
        
        enum Topping:
            case Cheese, Pepperoni, Mushrooms, GreenPeppers, Olives

        enum Direction:
            case North, South, East, West
        
        // Once you have created an Enum, first import it's instances, and then use them in expressions
        // and parameters, just like a class, trait, or other type:
        import CrustSize.*

        // if currentCrustSize == Small then ...

        // currentCrustSize match
        //     case Small => ...
        //     case Medium => ...
        //     case Large => ...
        import scala.collection.mutable.ArrayBuffer
        case class Pizza(
            crustSize: CrustSize, 
            crustType: CrustType, 
            toppings: ArrayBuffer[Topping]
        )

        // like traits and classes, enums can take parameters and have members, 
        // such as fields and methods. This example shows how a parameter named 
        // code is used in an enum:

        enum HttpResponse(val code: Int): 
            case Ok extends HttpResponse(200)
            case MovedPermanently extends HttpResponse(301)
            case InternalServerError extends HttpResponse(500)
        
        // Instances of enums are similar to case objects, so just like anyother
        // object, you can access the 'code' field directly on the object
        // (like a static member in Java)

        import HttpResponse.*
        println(Ok.code) //200
        println(MovedPermanently.code) //301
        println(InternalServerError.code) //500

        // Enums, like the class Set, all the values must be unique

        // An enum is a shortcut for defining 
        // a) a sealed class or trait along with
        // b) values defined as members of the class's companion object.
        // For example this enum:
        enum Display:
            case Top, Bottom, Left, Right
        
        // is a shortcut for writing this more verbose code
        // sealed class Display 
        // object Display:
        //     case object Top extends Display
        //     case object Bottom extends Display
        //     case object Left extends Display
        //     case object Right extends Display

        // Enums can have members, i.e. fields and methods
        // https://docs.scala-lang.org/scala3/reference/enums/enums.html
        enum Planet(mass: Double, radius: Double):
            private final val G = 6.67300E-11
            def surfaceGravity = G * mass / (radius * radius)
            def surfaceWeight(otherMass: Double) = otherMass * surfaceGravity

            case Mercury extends Planet(3.303e+23, 2.4397e6)
            case Venus   extends Planet(4.869e+24, 6.0518e6)
            case Earth   extends Planet(5.976e+24, 6.37814e6)
            case Mars    extends Planet(6.421e+23, 3.3972e6)
            case Jupiter extends Planet(1.9e+27,   7.1492e7)
            case Saturn  extends Planet(5.688e+26, 6.0268e7)
            case Uranus  extends Planet(8.686e+25, 2.5559e7)
            case Neptune extends Planet(1.024e+26, 2.4746e7)
        end Planet

        println(Planet.Mercury.surfaceGravity) //3.7030267229659395
        println(Planet.Earth.surfaceWeight(10)) //98.02652743337129

        // It is also possible to define an explicit companion object for an enum:

        object Planet:
            def main(args: Array[String]) =
                val earthWeight = args(0).toDouble
                val mass = earthWeight / Earth.surfaceGravity
                for p <- values do
                println(s"Your weight on $p is ${p.surfaceWeight(mass)}")
        end Planet




        // When to use enums:
        // It can seem like the line is blurry about when to use traits, classes, and enums, 
        // but a thing to remember about enums is that they're typically used to model a small, 
        // finite set of possible values. For instance, in the planet example, 
        // there are only eight planets in the solar system. Becausethis is a small, 
        // finite set of constant values, using an enum is a good choice to model the planets.
        




    }
    // Modeling Algebraic Data Types with Enums
    def modelingAlgebraicDataTypesWithEnums(): Unit = {
        // Problem: When programming in a functional style, 
        // you want to model an algebraic data type using Scala 3
        
        // There are two main types of ADTs:
            // Sum Types
            // Product Types
        
        // Sum Types:
        // A sum type is also referred to as an enumerated type because you simply
        // enumerate all the possible instances of that type.  In Scala 3, this is 
        // done with the enum constuct. For instance, to create your own boolean data type,
        // start by defining a Sum type like this:
        enum Bool:
            case True, False

        // This can be read as "Bool is a type that has two possible values, True and False" 
        // Similarly, Position is a type with four possible values:
        enum Position:
            case Top, Bottom, Left, Right
        
        // Product Types:
        // A Product type is created with a class constructor. The Product name comes from
        //  the fact that the number of possible concrete instances of the class is determined 
        // by multiplying the number of possibilities of all of its constructor fields.

        // For example, this class named DoubleBoo has two Bool constructor parameters:
        case class DoubleBoo(b1: Bool, b2: Bool)

        // in a small example like this, you can enumerate the possible values that can be creates 
        // from this constructor:
        // DoubleBoo(True, True)
        // DoubleBoo(True, False)
        // DoubleBoo(False, True)
        // DoubleBoo(False, False)

        // As shown, there are four possible values. As implied by the name product, you can
        // also derive this answer mathematically. This is covered in the Discussion.

        // Informally, an Algebra can be thought of as consisting of two things:
        // 1. A set of objects
        // 2. The operations that can be applied to those objects to create new objects

        // In the bool example the set of objects is True and False. The 'operations' consist
        // of the methods you define for those objects.  For instance, you can define 'and'
        // and 'or' operations to work with Bool like this:
        import Bool.*

        def and(a: Bool, b: Bool): Bool = (a, b) match
            case(True, False) => False
            case(False, False) => False
            case(False, True) => False
            case(True, True) => True
        
        def or(a: Bool, b: Bool): Bool = (a, b) match
            case (True, _) => True
            case (_, True) => True
            case (_, _)    => False
        
        print(and(True, True)) //True
        print(and(True, False)) //False
        print(or(True, False)) //True
        print(or(False, False)) //False

        // The Sum Type
        // A few important points about Sum Types:
        // 1. In Scala 3 they're created as cases of the enum construct
        // 2. The number of enumerated types you list are the only possible
        //   instances of the type.  In the previous example, Bool is the type
        //   and it has two possible values, True and False.
        // 3. The phrases 'is a' and 'or a' are used when talking about Sum Types.
        //   for example, True 'is' a Bool, and Bool is a True 'or' a False

        // Note: people use differenct names for the concrete instances in a 
        //  Sum Type, including 'value constructors', 'alternates', and 'cases'

        // The Product Type
        // Again, The Product name comes from
        //  the fact that the number of possible concrete instances of the class is determined 
        // by multiplying the number of possibilities of all of its constructor fields.

        case class TripleBoo(b1: Bool, b2: Bool, b3: Bool)
        // There are 8 possible instances for the above example

        
        










    }
  
}

