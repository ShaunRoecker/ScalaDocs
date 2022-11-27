package methods

object Methods extends App {
  def main(): Unit = {
        println("Methods")
        // introduction()
        // controllingMethodScope()
        // callingAMethodOnASuperclassOrTrait()
        // usingParameterNamesWhenCallingAMethod()
        // settingDefaultValuesForMethodParameters()
        // creatingMethodsThatTakeVariableArgumentFields()
        // forcingCallersToLeaveParenthesesOffAccessorMethods()
        // declaringThatAMethodCanThrowAnException()
        // supportingAFluentStyleOfProgramming()
        addingNewMethodsToClosedClassesWithExtensionMethods()
    }
    main()
    def introduction(): Unit = {
        // Basic method structure
        // def methodName(param1name: param1type, param2name: param2type): ReturnType =
        //     // the method body goes
        //     // there
        
        // Declaring the method ReturnType is optional, but you should do it anyway.
        // Can make the method easier to understand both for yourseld later on, and
        // for others immediately. You want to help people don't you?

        // See App: https://oreil.ly/oaXZg

        // Scala methods have many features, including these:

        //     -Generic (type) parameters
        //     -Default parameter values
        //     -Multiple parameter groups
        //     -Context-provided parameters
        //     -By-name parameters
        
        // In that syntax:
        //     -The keyword def is used to define a method
        //     -The Scala standard is to name methods using the camel case convention
        //     -Method parameters are always defined with their type
        //     -Declaring the method return type is optional (but you should)
        //     -Methods can consist of many lines, or just one line
        //     -Providing the end methodName portion after the method body is also optional, 
        //         and is only recommended for long methods

        // Keyword that can be prepended to a method
        //   final def foo(x: Int): Int = x + 1
                // 'final' to not allow a method in the parent class 
                // to be overridden in the child class
        
        //   override def foo(x: Int): Int = x + 1
            // 'override' when you want to change a method in the child class
            //  that was inherited from a superclass (parent class)
        
        // Also: protected, private
    }
    // CONTROLLING METHOD SCOPE(ACCESS MODIFIERS or Getter methods)
    def controllingMethodScope(): Unit = {
        // Problem: Scala methods are public by default, and you want to control their scope

        // In order from most restrictive to most open, Scala provides these scope options:
        // Private Scope
        // Protected Scope
        // Package Scope
        // Package-specific Scope
        // Public Scope

        // PUBLIC SCOPE:
        // The most restrictive access is to mark a method private, which makes the method 
        // available to (a) the current instance of a class and (b) other instances of the 
        // current class. This code shows how to mark a method as private and how it can 
        // be used by another instance of the same class:
        class Cat:
            private def isFriendlyCat = true
            def sampleMethod(other: Cat) = 
                if other.isFriendlyCat then
                    println("Can access other.isFriendlyCat")
                    // ...
                end if
            end sampleMethod
        end Cat

        val cat1 = Cat()
        val cat2 = Cat()
        cat1.sampleMethod(cat2) //Can access other.isFriendlyCat
        // but... 
        // cat1.isFriendlyCat
        // ^^^ method isFriendlyCat cannot be accessed as a member of (cat1 : Cat) 
        // from module class Methods$.

        // When a method is marked private it is not available to subclasses:
        class Animal:
            private def speak(): Unit = println("speak")

        class Dog extends Animal
            // speak()  // <- will result in error
        
        
        // PROCTECTED SCOPE
        // Marking a method protected modifies its scoop so it (a) can be accessed by other 
        // instances of the same class, (b) is not visible in the current package, and (c) 
        // is available to subclasses. The following code demonstrates these points:
        class Alligator:
            protected def isFriendlyGator = false
            def gatorFoo(otherGator: Alligator) =
                if otherGator.isFriendlyGator then 
                    println("Can access 'otherGator.isFriendlyGator")
                else 
                    println("Somebody's gonna fight")
            end gatorFoo
        end Alligator
            
        val g1 = Alligator()
        val g2 = Alligator()
        g1.gatorFoo(g2)
        // g1.isFriendlyGator won't compile


        // PACKAGE SCOPE
        // To make a method only available to all members of the curren package, mark the
        // method as being private to the current package with the private[packageName]
        // syntax

        // In the following example, the method privateModelMethod can be accessed by 
        // other classes in the same package - the 'model' package- but privateMethod and
        // protectedMethod can't be accessed.

        // package com.devdaily.coolapp.model:
        // class Foo:
        //     // this is in "package scope"
        //     private[model] def privateModelMethod = ??? // can be accessed by classes
        //                                                 // in com.devdaily.coolapp.model
        //     private def privateMethod = ???
        //     protected def protectedMethod = ???
        
        // class Bar:
        //     val f = Foo()
        //     f.privateModelMethod // compiles
        //     f.privateMethod // won't compile
        //     f.protectedMethod // won't compile

        // PACKAGE SPECIFIC SCOPE
        // Scala allows a fine-grained level of access control that lets you make a method
        // available at different levels in a class hierarchy.

        // package com.devdaily.coolapp.model
            // class Foo:
            //     // available under com.devdaily.coolapp.model
            //     private[model] def doUnderModel = ???

            //     // available under com.devdaily.coolapp.model
            //     private[coolApp] def doUnderCoolApp = ???

            //     // available under com.devdaily.coolapp.model
            //     private[devdaily] def doUnderAcme = ???

        // import com.devdaily.coolapp.model

        // package com.devdaily.coolapp.view
        //     class Bar:
        //         val f = Foo()
        //         // f.doUnderModel // won't compile
        //         f.doUnderCoolApp
        //         f.doUnderAcme
            
        // // package com.devdaily.common
        //      class Bar:
        //         val f = Foo()
        //         // f.doUnderModel // won't compile
        //         // f.doUnderCoolApp  // won't compile
        //         f.doUnderAcme
            
        // Again, the syntax to specify which package hierarchy should be allowed
        // by a method, and what package the user object/class/etc. has to be in 
        // in order to access the method is this:
            // private[packageName] def foo() = ???
        
        // So only objects/classes etc that are in 'packageName' will be able to 
        // access the above method


        // PUBLIC SCOPE
        // With the default public scope, any piece of code in any package
        // can access it.

    }
    // CALLING A METHOD ON A SUPERCLASS OR TRAIT
    def callingAMethodOnASuperclassOrTrait(): Unit = {
        // Problem: To keep your code DRY (don't repeat yourself), you want to invoke a method that's
        // already defined in a parent class or trait

        // Situations:
        /////////////////////////////////////////////////////////////////////////////
        // A method in a class does not have the same name as a superclass method
        // and wants to call that superclass method.
        class AnimalWithLegs:
            def walk() = println("I'm walking")
            def run() = println("I'm running")
        
        class Dog extends AnimalWithLegs:
            def walkThenRun() = 
                walk()
                run()
        
        val d = Dog()
        d.walkThenRun() 
        // I'm walking
        // I'm running

        /////////////////////////////////////////////////////////////////////////////
        // A method in a class with the same name as a superclass method and needs
        // to call that superclasse method.
        class Super:
            def walk()= println("I'm walking")

        class Child extends Super:
            override def walk() = 
                super.walk()
                println("Dog is walking")

        val child = Child()
        child.walk()
        // I'm walking
        // Dog is walking

        // In this situation, you dont have to use super.walk() in the child's walk()
        // method, you can just override it and assign it to whatever

        /////////////////////////////////////////////////////////////////////////////
        // A method in a class has the same name as multiple traits that it extends,
        // and you want to choose which trait behavior to use.
        trait Human:
            def what = "human"

        trait Mother extends Human:
            override def what = "Mother"
        
        trait Father extends Human:
            override def what = "Father"

        class Son extends Human, Mother, Father:
            def superWhat = super.what
            def motherWhat = super[Mother].what
            def fatherWhat = super[Father].what
            def humanWhat = super[Human].what

        val s = Son()
        println(s.superWhat) //Father <- only one that isn't obvious
        println(s.motherWhat)       // The super of a child class is
        println(s.fatherWhat)      // the trait furthest to the right
        println(s.humanWhat)
        /////////////////////////////////////////////////////////////////////////////
        
    }
    // USING PARAMETER NAMES WHEN CALLING A METHOD
    def usingParameterNamesWhenCallingAMethod(): Unit = {
        // Problem: You prefer a coding style where you specify the method parameter
        //  names when calling a method

        // methodName(param1=value1, param2=value2, ...)

        // This is demostrated in the following example where it shows
        // that this style of calling a method can be very beneficial
        // to the readablity of your code.

        enum CrustSize:
            case Small, Medium, Large
        
        enum CrustType:
            case Regular, Thin, Thick
        
        import CrustSize.*, CrustType.*

        class Pizza:
            var crustSize = Medium
            var crustType = Regular
            def update(crustSize: CrustSize, crustType: CrustType) =
                this.crustSize = crustSize
                this.crustType = crustType
            override def toString = s"A $crustSize sized, $crustType crust pizza"
        
        val p = Pizza()
        println(p) //A Medium sized, Regular crust pizza

        p.update(crustType=Thick, crustSize=Large) //can be in any order
        p.update(crustSize=Small, crustType=Thin)
        println(p) //A Small sized, Thin crust pizza

        // Example of hoe this style can be helpful

        // engage(true, false, true, true)

        // or

        // engage(
        //     speedIsSet=true,
        //     directionIsSet=false,
        //     picardSaidMakeItSo=true,
        //     armsReady=true
        // )
    }
    // SETTING DEFAULT VALUES FOR METHOD PARAMETERS
    def settingDefaultValuesForMethodParameters(): Unit = {
        // Problem: You want to set default values for method parameters so the
        // method can optionally be called without those parameters having to be
        // assigned

        // Specify the default value for parameters inside the method signature
        // with this syntax:
        
        // parameterName: parameterType = defaultValue

        class Connection:
            def makeConnection(timeout: Int = 5000, protocol: String = "https") =
                println(f"timeout = ${timeout}%d, protocol = ${protocol}%s")
            
        val c = Connection()
        c.makeConnection() //timeout = 5000, protocol = https
        c.makeConnection(2_000) //timeout = 2000, protocol = https
        c.makeConnection(3_000, "http") //timeout = 3000, protocol = http
        c.makeConnection(timeout=4000) //timeout = 4000, protocol = https
        c.makeConnection(protocol="http", timeout=1_000) //timeout = 1000, protocol = http

        // If you have a mix of regular params and ones with default param values added
        // list the ones without first, like this:
        // def makeConnection(timeout: Int, protocol: String = "http")


    }
    // CREATING METHODS THAT TAKE VARIABLE-ARGUMENT FIELDS
    def creatingMethodsThatTakeVariableArgumentFields(): Unit = {
        // Problem: To make a method more flexible, you want to define 
        // a method parameter that can take a variable number of arguments
        // i.e., 'varargs' fields

        // Define a varargs field in your method declaration by adding
        // a * character after the field type

        def printAll(strings: String*) = 
            strings.foreach(print)
            println()
        
        // all these work
        printAll()
        printAll("a") //a
        printAll("a", "b") //ab
        printAll("a", "b", "c") //abc

        // USE _* TO ADAPT A SEQUENCE
        // By default you can't pass a Sequence-List, Seq, Vector, etc. - into an
        // varargs parameter, but you can use Scala's _* operator to adapt a 
        // sequence so it can be used as an argument for a varargs field

        val fruits = List("apple", "grape", "orange")
        // printAll(fruits)   // fails(Found: List[String], Required: String)
        printAll(fruits: _*)  //works: applegrapeorange

        // When declaring that a method has a field that can contain a 
        // variable number of args, the varargs field must be the last field 
        // in the method signature

        // Therefore, a method can have only one varargs field

    }
    // FORCING CALLERS TO LEAVE PARENTHESES OFF ACCESSOR METHOD
    def forcingCallersToLeaveParenthesesOffAccessorMethods(): Unit = {
        // Problem: You want to enforce a coding style where accessor (getter)
        // methods can't have parentheses when they are invoked.

        // Define your accessor method without parentheses after the method name.
        class Pizza:
            def crustSize = 12

        val p = Pizza()

        // fails:
        // println(p.crustSize()) method crustSize in class Pizza does not take parameters

        // works:
        println(p.crustSize) //12

        // Why?
        // The recommended strategy for calling accessor methods 'that have no side effects'
        // is to leave the parentheses off when calling the method.
        // Docs: https://docs.scala-lang.org/style
        
        // See Also: https://oreil.ly/mx6xl  (Scala Style Guide)
    }
    // DECLARING THAT A METHOD CAN THROW AN EXCEPTION
    def declaringThatAMethodCanThrowAnException(): Unit = {
        // Problem: You want to declare that a method will throw an exception, 
        // either to alert callers to this fact or because your method will 
        // be called from Java code.

        // // Use the @throws annotation
        // @throws(classOf[Exception])
        // def play = ??? //exception throwing code here

        // // for multiple exceptions:
        // @throws(classOf[IOException])
        // @throws(classOf[FileNotFoundException])
        // def readFile(filename: String) = ??? //exception throwing code here

        // Scala doesn't require that methods declare that exceptions can be thrown,
        // and it also doesn't require calling methods to catch them (unlike Java)

        // Basically, in Scala this annotation is a courtesy, but doing it 
        // informs other developers to take cautionary action in their code
        // with a try/catch block, so use it if you know the method throws
        // an exception.  Thank you.
        


    }
    // SUPPORTING A FLUENT STYLE OF PROGRAMMING
    def supportingAFluentStyleOfProgramming(): Unit = {
        // Problem: While creating classes in an OOP style, 
        // you want to design an API so developers can write code 
        // in a fluent programming style, also known as method chaining

        // method chaining:
        // person.setFirstName("Frank")
        //       .setLastName("Jordan")
        //       .setAge(85)
        //       .setCity("Manassas")
        //       .setState("Virginia")

        // How to facilitate this:
            // If your class can be extended, specify this.type as a return type 
            // of fluent style methods

            // If your class can't be extended, you can return this or this.type
            // from your fluent-style methods

        // The following code demonstrates how to specify this.type as a return type
        // of the set* methods shown:
        class Person:
            protected var _firstname = ""
            protected var _lastname = ""

            def setFirstName(firstname: String): this.type =
                _firstname = firstname
                this
            
            def setLastName(lastname: String): this.type =
                _lastname = lastname
                this
        end Person

        class Employee extends Person:
            protected var employeeNumber = 0

            def setEmployeeNumber(num: Int): this.type = 
                this.employeeNumber = num
                this
            
            override def toString(): String = s"${_firstname}, ${_lastname}, ${employeeNumber}"
        end Employee

        // The following shows how these methods can be chained together
        val employee = Employee()
        // Use the fluent methods
        employee.setFirstName("Buck")
                .setLastName("Nekd")
                .setEmployeeNumber(2)
        println(employee) // Buck, Nekd, 2

        // If you are sure that your class will not be extended, you can leave out
        // the return type of this.type in your methods, but set the class to final
        // to be sure it can't be extended.
        // example
        enum CrustSize:
            case Small, Medium, Large

        enum CrustType:
            case Regular, Thick, Thin

        enum Topping:
            case Cheese, Pepperoni, Mushrooms
        
        import CrustSize.*, CrustType.*, Topping.*

        final class Pizza:
            import scala.collection.mutable.ArrayBuffer

            private val toppings = ArrayBuffer[Topping]()
            private var crustSize = Medium
            private var crustType = Thick

            def addTopping(topping: Topping) = //notice no this.type return type here
                toppings += topping
                this

            def setCrustSize(crustSize: CrustSize) = //notice no this.type return type here
                this.crustSize = crustSize
                this
            
            def setCrustType(crustType: CrustType) = //notice no this.type return type here
                this.crustType = crustType
                this
            
            def print() =
                println(s"crust size: $crustSize")
                println(s"crust type: $crustType")
                println(s"toppings: $toppings")
        end Pizza

        val pizza = Pizza()
        pizza.print()
            // crust size: Medium
            // crust type: Thick
            // toppings: ArrayBuffer()
        pizza.setCrustSize(Large).setCrustType(Thin).addTopping(Pepperoni)
        pizza.print()
            // crust size: Large
            // crust type: Thin
            // toppings: ArrayBuffer(Pepperoni)
        
        //Docs on extending classes and class modifiers( open and final ) https://oreil.ly/4lk7Q
        // wikipedia on fluent interface: http://oreil.ly/Ul5bT
        // fluent interfaces discussion: http://oreil.ly/ukJi7
        
    }
    // ADDING NEW METHODS TO CLOSED CLASSES WITH EXTENSION METHODS
    def addingNewMethodsToClosedClassesWithExtensionMethods(): Unit = {
        // Problem: You want to add new methods to closed classes, 
        // such as adding methods to String, Int, and other classes where,
        //  you don't have access to their source code.

        // In Scala 3 you define extension methods to create a new behavior you want

        // example: suppose you want to write a method named hello to the String
        // class so you can write code that adds functionality like this:
        // println("joe".hello)  // prints "Hello, Joe"

        // To create this functionality, define hello as an extension method 
        // with the extension keyword:
        extension (s: String)
            def hello: String = s"Hello, ${s.capitalize}"
        
        println("shaun".hello)  //Hello, Shaun

        // Can define multiple extension methods:
        extension (s: String)
            def increment: String = s.map(c => (c + 1).toChar)
            def hideAll: String = s.replaceAll(".", "*")
            def addCommas: String = 
                s.split(" ").map(w => "," + w ).mkString.substring(1)

        println("joe".increment) //kpf
        println("joe".hideAll) //***
        println("we need to turn this into a csv someday".addCommas) 
        //we,need,to,turn,this,into,a,csv,someday

        // EXTENSION METHODS THAT TAKE A PARAMETER
        extension (s: String)
            def makeInt(radix: Int): Int = Integer.parseInt(s, radix)
        
        println("1".makeInt(2)) //1
        println("10".makeInt(2)) //2
        println("100".makeInt(2)) //4

        println("1".makeInt(8)) //1
        println("10".makeInt(8)) //8
        println("100".makeInt(8)) //64

        // "foo".makeInt(2) => java.lang.NumberFormatException
        
        // A simplified version of how extension methods work in scala 3,
        //using the hello example:
            // The compiler sees a String literal
            // The compiler sees that you're attempting to invoke a method 
                // named hello on a String.
            // Because the String class has no method named hello, the compiler 
                // starts looking around the known scope for methods named hello 
                // that take a single string parameter and return a string.
            // The compiler finds the extension method
        
        // extension method documentation: https://oreil.ly/rzzZ0
        
    }
    



}
