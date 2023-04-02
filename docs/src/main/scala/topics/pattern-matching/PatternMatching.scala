package langfeat.`pattern-matching`

object PatternMatching {
    // Some examples of pattern matching and some of the operators used
    // in pattern matching.

    // Pattern matching is very commonly used to extract values from case classes
    case class Person(name: Name, age: Int)
    case class Name(first: String, last: String)

    val person = Person(Name("John", "Doe"), 33)
    println(person) //Person(Name(John,Doe),33)

    // use the "p @ ThingToMatch" syntax to use the entire object as a
    // variable on the right side of the pattern match
    val personMatch1 = person match {
        case p @ Person(n, a) => s"Person: ${p}, Name: ${n}, Age: ${a}"
    }
    println(personMatch1)
    //Person: Person(Name(John,Doe),33), Name: Name(John,Doe), Age: 33

    val personMatch2 = person match {
        case p @ Person(n, a) => s"Person: ${p}, firstname: ${n.first}, lastname: ${n.last} Age: ${a}"
    }
    println(personMatch2)
    //Person: Person(Name(John,Doe),33), firstname: John, lastname: Doe Age: 33


    val personMatch3 = person match {
        case p @ Person(Name(_, "Doe"), a) => "You found a Doe"
        case Person(_, _) => "Just a regular person"
    }
    println(personMatch3)
    // You found a Doe

    // Pattern Matching can also be useful for matching on lists
    val list = List(1, 2, 3, 4, 5, 99)

    val matching1 = list match {
        case Nil => None
        case x :: xs => Some(x)
    }
    println(matching1) // Some(1)

    val mustHaveThree = list match {
        case List(_, _, 3, _*) => "list has the value 3 in the index of 2 place"
        case x :: xs => "can even use expressions that equate to what you want to match" 
        case Nil => "empty list"
    }

    // _* means that there can be as many and whatever value elements in the rest of the list

    val matches2 = list match {
        case List(_*) :+ 99 => "matched" // matches a list greater than one element, with value 99 at end
        case _ => "not matched"
    }
    println(matches2) //matched

    // the above is an example of using the infix pattern to match, and you can use any method that
    // allows infix notation in a match expression on the left or right side

    // Matching on types

    val any: Any = 45

    val matchAny = any match {
        case _: String => "matched String"
        case _: Int => "matched Int"
        case _ => "something else"
    }
    println(matchAny) // matched Int

    // Adding guards to the left side

    val personMatch4 = person match {
        case p if p.name.last == "Doe" => "Person is a Doe"
        case _ => "someone else"
    }
    println(personMatch3) // You found a Doe


    // catches are actually matches

    try {
        val bomb = 42 / 0
    } catch {
        case dbz: ArithmeticException => println("think about it, can't happen")
        case _ => println("something else")
    }
    
    // Structure available for pattern matching:
        // constants
        // wildcards
        // case classes
        // tuples
    
    // We can pattern match out-of-the-box on case classes, however
    // for normal classes, we need to create a an unapply method
    // in order to pattern match against out class
    class Person2(val name: String, val age: Int)

    object Person2 {
        def unapply(person: Person2): Option[(String, Int)] = 
            Some(person.name, person.age)
    }

    val person2 = new Person2("John", 22)

    val person2Match = person2 match {
        case Person2(n, a) => s"Name: ${n}, Age: ${a}"
        case _ => "not person2 class"
    }
    println(person2Match) //Name: John, Age: 22

    



}


