object MethodNotations extends App{
  
    class Person(val name: String, val favoriteMovie: String, val age: Int){
        def likes(movie: String): Boolean = movie == favoriteMovie

        def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"
        
        def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
        def +(nickname: String): Person = new Person(s"${name} (the ${nickname})", favoriteMovie, age)

        def urnary_! : String = s"${name} bang"

        def unary_+ : Person = new Person(name, favoriteMovie, age + 1)

        def isAlive = true

        def apply(): String = s"Hi, my name is $name and i like $favoriteMovie" 

        def +(amount: Int) = new Person(name, favoriteMovie, age = (age + amount))

        def learns(what: String) = s"${name} learns ${what}"
        def learnsScala = this.learns("Scala")


    }

    val mary = Person("Mary", "Inception", 20)
    val joe = Person("Joe", "Cars", 21)

    println(mary.likes("Inception")) //true

    ///////////////////////////////////////////////////////////////
    // INFIX NOTATION
    // infix notation = operator notation
    // Works with methods that only have one parameter
    println(mary likes "Inception") //true

    println(mary hangOutWith joe) //Mary is hanging out with Joe 


    println(mary.urnary_!) // Mary bang
    

    // in scala, we can create methods with symbols, unlike other languages
    class Book(val name: String, var pages: Int = 10) {
        
        def +(amount: Int)= pages = pages + amount
        
    }
    val book = new Book("Book")
    println(book.pages) // 10
    book + 5
    println(book.pages) // 15
    book.+(5)
    println(book.pages) // 20

    
    ///////////////////////////////////////////////////////////////
    // PREFIX NOTATION
    val x = -1
    // the same as
    val y = 1.unary_-
    println(x) // -1
    println(y) // -1

    ///////////////////////////////////////////////////////////////
    // POSTFIX NOTATION (only available to methods without parameters)
    // don't use it, it can be confusing but good to know exists
    println(mary.isAlive)
    // println(mary isAlive)

    // apply method
    println(mary.apply()) //Hi, my name is Mary and i like Inception

    println(mary()) ////Hi, my name is Mary and i like Inception


    ////////////////////////////////////////////////////////////////////////
    // METHOD NOTATIONS EXERCISES
    // 1. overload the + operator
        // mary + "the rockstar" => new Person "Mary the rockstar"

    // 2. Add an age to the Person class
        // add a unary + operator -> new Person with the age + 1
        // +mary => mary with the age + 1
    
    // 3. Add a "learns" method in the Person Class => "Mary learns Scala"
        // Add a learnsScala method, calls learns method with "Scala"
        // Use it in postfix notation

    // 4. Overload the apply method 
        // mary.apply(2) => "Mary watched Inception 2 times"
    

    // 1.
    println(mary.age) // 20
    println((+mary).age) // 21

    println(mary + joe) // Mary is hanging out with Joe

    println((mary + "Rockstar")()) // Hi, my name is Mary (the Rockstar) and i like Inception
    // This ^ is using the "+" method spec that takes a string and creates a new Person instance
    // with the arg "nickname" added to the name.  Then we are calling the apply method

    println((mary + "Rockstar").apply()) //Hi, my name is Mary (the Rockstar) and i like Inception

    // 3
    println(mary.learns("Scala")) // Mary learns Scala
    println(mary.learnsScala) // Mary learns Scala
    




}
