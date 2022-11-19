

object ScalaOOP extends App {

    def main(): Unit = {
        println("Scala OOP Essentials")
        classesPartOne()
    }
    main()

    def classesPartOne(): Unit = {
        println("Classes Part One")

        // class Person(val name: String, val age: Int, val email: String) // <- Constructor
        // Note for classes you need val because class parameters are not fields
        // This differs from a constructor for a "case class" which we will get into later

        class Person(val name: String, val age: Int, val email: String) {
            // body- for every instantiation of a class, everything in this code block
            // will be executed
        
            // functions within the body of a class definition are called "methods"
            // and they act on the instance of the class
            def greet(name: String): Unit = println(s"${this.name} says: hi ${name}")
            // the this keyword means that the method is refering to 'this' instance of the class
            // in Python it it 'self', meaning the method is refering to its own "name" property.

            // overloading: means supplying methods with the same name but different signatures
            def greet(): Unit = println(s"Hi, I am ${name}")

            // multiple constructors

        }

        val person = Person("John Doe", 34, "jd@gmail.com")
        // person is an 'instance' of thge class Person
        println(person.name)
        person.greet("Daniel") //John Doe says: hi Daniel
        person.greet() //Hi, I am John Doe

        // Novel and Writer classes
        class Novel(val title: String, year: Int, author: Writer) {
            def authorAge(): Int = year - author.birthYear

            def isWrittenBy() = author.fullName()
            def isWrittenBy(author: Writer) = author == this.author
            
            def copy(newYear: Int): Novel = new Novel(title, newYear, author) 

        }

        class Writer(val firstName: String, val lastName: String, val birthYear: Int) {
            def fullName(): String = 
                firstName + " " + lastName
            
            
        }
        
        val writer = Writer("Ernest", "Hemingway", 1900)
        println(writer.fullName()) //Ernest Hemingway

        val novel = Novel("The Old Man And The Sea", 1950, writer)
        println(novel.title) //The Old Man And The Sea

        println(novel.authorAge()) //50
        println(novel.isWrittenBy()) //Ernest Hemingway


        // Counter Class
        class Counter(val count: Int = 0) {

            // For increment/decrememnt we want to return a new counter for immutability
            def inc = 
                println("incrementing")
                new Counter(count + 1)

            def dec = 
                println("decrementing")
                new Counter(count - 1)
            
            def inc(n: Int): Counter = 
                if (n <= 0) this 
                else inc.inc(n - 1)
            
            def dec(n: Int): Counter = 
                if (n <= 0) this 
                else dec.dec(n - 1)
            
            def print = println(count)
        
        }
        
        val counter = new Counter
        counter.print
        counter.inc.print
        counter.print
        
        




    }

}