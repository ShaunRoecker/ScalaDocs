

object PatternMatching extends App {
    def main(): Unit = {
        patternMatching()
    }
    main()
    // Some Pattern Matching Use Cases
    def patternMatching(): Unit = {
        println("Pattern Matching Use Cases")
        // 1 - A switch statement on steroids
        val aNumber = 42
        val ordinal = aNumber match
            case 1 => "first"
            case 2 => "second"
            case 3 => "third"
            case _ => aNumber + "th"
        println(ordinal)

        // 2 - case class deconstruction
        case class Person(name: String, age: Int)
        val bob = Person("Bob", 34)
        val bobGreeting = bob match 
            case Person(name, age) => s"Hello, my name is ${name} and my age is ${age}"

        println(bobGreeting)

        // 3 - list extractors
        val numberList = List(1,2,3,42)
        val mustHave3 = numberList match
            case List(_, _, 3, somethingElse) => s"thats a match, so the fourth element is ${somethingElse}"
            case _ => "default, to cover our ass"
            
        println(mustHave3)

        // 4 - Haskell-like prepending
        val startsWithOne = numberList match
            case 1 :: tail => s"List starts with one, and ends with ${tail}"
            case _ => "default, to cover our ass"

        println(startsWithOne)

        def process(aList: List[Int]) = aList match
            case Nil => "list is empty"
            case head :: tail => s"list starts with ${head} and ends with ${tail}" 
            // might also do recursive processing with the head and tail, 
            // it was mentioned in the video and autocomplete brought it up, 
            // so look into what we can do with that.
        
        // 5 - vararg pattern
        val dontCareAboutTheRest = numberList match
            case list @ List(_, 2, _*) => s"list: ${list.mkString}"
            case _ => "default, to cover our ass"

        println(dontCareAboutTheRest)

        // 6 - other infix patterns
        val mustEndWith42 = numberList match
            case List(1, 2, _) :+ 42 => "that's right, I have 42"
            case _ => "default, to cover our ass"
        
        println(mustEndWith42)

        val mustEndWith42part2 = numberList match
            case List(1, 2, _*) :+ 42 => "I dont care how long the list is, I end with 42"
            case _ => "default, to cover our ass"

        println(mustEndWith42part2)

        // 7 - Type Specifiers
        def gimmeAValue(): Any = 45

        val gimmeTheType = gimmeAValue() match 
            case _: String => "I have a String"
            case _: Int => "I have a Int"
            case _ => "Something else"
        println(gimmeTheType)

        // 8 - Name Binging
        def requestMoreInfo(p: Person): String = s"The person ${p.name} is a good person"

        val bobsInfo = bob match
            case p @ Person(name, age) => s"${name}'s info: ${requestMoreInfo(p)}"
        
        // 9 - Conditional Clauses (Guards)
        val ordinal2 = aNumber match
            case 1 => "first"
            case 2 => "second"
            case 3 => "third"
            case n if n % 10 == 1 => n + "st"
            case n if n % 10 == 2 => n + "nd"
            case n if n % 10 == 3 => n + "rd"
            case _ => aNumber + "th"
        
        println(ordinal2)
        
        // 10 - Alternative Patterns
        //val numberList = List(1,2,3,42)
        val myOptimalList = numberList match
            case List(1, _*) => "I like this list"
            case List(_, _, 3, _*) => "I like this list"
            case _ => "I hate this list"
        // don't do that ^^^^^^^ do this VVVVV
        val myOptimalList2 = numberList match
            case List(1, _*) | List(_, _, 3, _*) => "I like this list"
            case _ => "I hate this list"
        println(myOptimalList)
        println(myOptimalList2)

    }
  
}
