object CaseClasses extends App {
  
    def main(): Unit = {
        println("CASE CLASSES")
        
        alpha()
        
    }
    main()
    // enums work just like classes, only there are a finite set of constants
    def alpha(): Unit = {
        case class Person(name: String, age: Int)

        // 1. Class parameters are promoted to fields (without val/var in the constructor)
        val jim = Person("Jim", 34)

        // 2. case classes have a better toString method "out-of-the-box"
        print(jim.toString) //Person(Jim,34), *regular class is a mostly nonsensical string (to humans)

        // 3. equals and hashCode are implemented 'out-of-the-box'
        val jim2 = Person("Jim", 34)
        println(jim == jim2) //true
        // If these weren't case classes, they would not equate because 
        // they are 2 different instances

        // 4. Case classes have a handy copy method
        val jim3 = jim.copy(age=37)
        println(jim3) //Person(Jim,37)

        val jim4 = jim.copy(age=36, name="Jimbo")
        println(jim4) //Person(Jimbo,36)

        // 5. Case classes have companion objects pre-built
        val personA = Person
        val mary = Person("Mary", 23)

        // 6. Case classes are serializable
        // Akka

        // 7. Case classes have extractor patterns = CCs can be used in pattern matching

        case object UnitedKingdom:
            def name: String = "United Kingdom of Great Britain and Northern Ireland"

        /*
        */
    }
    
    
  
}

