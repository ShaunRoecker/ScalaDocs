object Methods extends App {
  def main(): Unit = {
        println("Methods")
        introduction()
        controllingMethodScope()
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
                    print("Somebody's gonna fight")
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
            class Bar:
                val f = Foo()
                // f.doUnderModel // won't compile
                f.doUnderCoolApp
                f.doUnderAcme
            
        // package com.devdaily.common
             class Bar:
                val f = Foo()
                // f.doUnderModel // won't compile
                // f.doUnderCoolApp  // won't compile
                f.doUnderAcme
            
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
}
