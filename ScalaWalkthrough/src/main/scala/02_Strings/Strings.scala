import scala.util.matching.Regex
import scala.util.Random

object Strings extends App {
    def scalaStrings(): Unit = {
    //    stringsAsSequences() 
    //    replacingPatternsInString()
    //    extractingPartsOfStringThatMatchesPattern()
    //    accessingACharInAString()
    //    creatingYourOwnStringInterpotators()
    //    creatingRandomStrings()
       splittingStrings()
    }
    scalaStrings()
    
    def testingStringEquality(): Unit = {
        val s1 = "Hello"
        val s2 = "Hello"
        val s3 = "H" + "ello"
        s1 == s2 // true
        s1 == s3 // true

        // To compare 2 strings in a case-insensitive manner
        val stringA = "Hello"
        val stringB = "hello"
        // [1]
        stringA.toUpperCase == stringB.toUpperCase // true
        // Doesn't throw a NullPointerException when a string is null

        // [2][JAVA]
        stringA.equalsIgnoreCase(stringB)


    }
    // Methods for Transforming Strings
    def stringsAsSequences(): Unit = {
        // Strings in Scala are treated as a Sequence of Char, therefore sequence methods can be used on them
        val str: String = "Big Belly Burger"

        // Some common methods
        str.count(_ == ' ')     // 3
        str.dropRight(3)    // "Big Belly Bur"
        str.dropWhile(_ != ' ')     // " Belly Burger
        def dropWhileExample2() = 
            val iter = List(2,3,4,6,7)
            val dropWhileMethod = iter.dropWhile(char => (char < 5))
            println(dropWhileMethod) 
        
        dropWhileExample2()      // List(6, 7)

        str.filter(_ != ' ')    //"BigBellyBurger"
        def anotherFilterExample() = 
            val iter = List(2,3,4,6,7)
            val filterMethod = iter.filter(x => (x == 3))
            println(filterMethod)

        anotherFilterExample()

        str.sortWith(_ < _) // "BBBeeggillrruy"
        str.take(3)     // "Big"
        str.takeRight(3)    // "ger"
        str.takeWhile(_ != 'r') //"Big Belly Bu"

        // Chaining methods together
        val scala: String = "Scala"
        scala.drop(2).take(2).capitalize // "AL"
        scala.drop(2)   // "ala"
        
        // Testing string equality
        val s1 = "Hello"
        val s2 = "Hello"
        val s3 = "H" + "ello"

        // To compare strings
        s1 == s2  // true
        s1 == s3 // true

        // 2 ways to compare in a case sensitive manner
        s1.toUpperCase == s2.toUpperCase // true
        // ^^^ doesn't throw a NullPointerException error when a string is null
        s1.equalsIgnoreCase(s2)    // true
        // ^^^ Java style, those guys objectify things

        // *Note: In idiomatic Scala, you never use null values.  Whenever you want to use a null value,
        // use Scala's Error Handling Type (Option, Try, and Either) (more on those later)

        // *Imagine Scala doesn't even have a null keyword.  That was in the past, we are a different person now.
        // No more using null values while doing keg stands in our chonies. 

        //String Problem 2.2: Creating a multi-line string
        //Problem: You want create multiline Strings within your scala source code.

        val foo = """This is 
                  a multiline string""" 

    }
    // Splitting Strings
    def splittingStrings(): Unit = {
        // Problem: You want to split a sting into parts based on a field separator, 
        // such as what is found in a csv
        println("SPLITTING STRINGS")
        val string1 = "This is a string to split"
        val splitString = string1.split(" ")
        val s = "Milk, Eggs, Butter"
        val groceryList = s.split(",")  // Returns Array(Milk, Eggs, Butter)
        // When doing this its best to trim after splitting because
        // there are likely spaces we don't want
        val groceryListTrimmed = s.split(",").map(_.trim) 
        //* You can also Split a string based on a regular expression
        val splitS1 = "Relax, nothing is under control".split("\\s+")
        println(splitS1)
        // "https://oreil.ly/Feugs"
        


    }
    // Replacing Patterns in Strings Using Regex
    def replacingPatternsInString(): Unit = {
        // 2.8 Replacing Patterns in Strings
        // Problem: You want to search for regular-expression patterns in a string and then replace them.
        val address = "123 Main Street".replaceAll("[0-9]", "x")
        println(address) // xxx Main Street  (get your mind out of the gutter)

        // You can creat a Regex variable and use that with .replaceAllIn
        val regex = "[0-9]".r
        val newAddress = regex.replaceAllIn("123 Main Street", "x")
        println(newAddress)

        val result = "123".replaceFirst("[0-9]", "x")
        println(result) //x23

        val regex2 = "H".r
        val result2 = regex2.replaceFirstIn("Hello world", "J")
        println(result2) // Jello world  (Now you're talkin')

        // See also: https://oreil.ly/fZFEM
        val date = raw"(\d{4})-(\d{2})-(\d{2})".r
    }
    // Extract The Parts of String that Matches a Specified Pattern
    def extractingPartsOfStringThatMatchesPattern(): Unit = {
        // 2.9 Extracting Parts of String that Matches Pattern
        // Problem: You want to extract one or more parts fo a string that metch the regex patterns
        // you specify

        //  vvv The parathesis in the regex designate where a match variable will be created
        val pattern = "([0-9]+) ([A-Za-z]+)".r  // this is an instance of the scala.util.matching.Regex class
        // This can be read as "One or more numbers, followed by a space, followed by one or more alphanumeric Chars"

        // This is how you extract the regex pattern from the target string
        val pattern(count, fruit) = "100 Bananas" : @unchecked
        // *think    val pattern(([0-9]+), ([A-Za-z]+)) = "String to be searched"
        // count: String = 100
        // fruit: String = "Bananas"

        println(count)
        println(fruit)

        // Imagine we want to be able to match all of these patterns:
            // "movies near 80301"
            // "movies 80301"
            // "80301 movies"
            // "movies: 80301"
            // "movies near boulder, colorado"
            // "movies near boulder, co"
        
        // you can define a series of regex patterns to match against them

        // match "movies 80301"
        val MoviesZipRE = "movies (\\d{5})".r

        // match "movies near boulder, co"
        val MoviesNearCityStateRE = "movies near ([a-zA-Z]+), ([a-zA-Z]{2})".r

        // getSearchResult is a fictional method that returns an Option[List[String]]
        def getSearchResult(strings: String*) =
            strings.foreach(println)
        
        val textUserTyped: String = "movies near Black, CA"

        val results = textUserTyped match 
            case MoviesZipRE(zip) => getSearchResult(zip)
            case MoviesNearCityStateRE(city, state) => getSearchResult(city, state)
            case _ => None
    }
    // Accessing A Character in a String
    def accessingACharInAString(): Unit = {
        // Problem: You want to access a character in a specific position in a string
        val firstChar = "hello"(0) // Char = h
        val secondChar = "hello"(1) // Char = e
        // Under the hood -> "hello".apply(1) // Char = "e"
        println(firstChar)
        println(secondChar)
    }
    // Building Your Own String Interpolators 
    def creatingYourOwnStringInterpotators(): Unit = {
        // Problem: You want to create your own string interpolator,
        // like the s, f, and raw interpolators that come with scala

        // create a caps string interpolator 
        // caps"john c doe"  //"John C Doe"
        extension(sc: StringContext)
            def caps(args: Any*): String = 
                // [1] Create variables for the iterators. Note that for an
                // input string "a b c", `strings` will be "a b c" at this point
                val strings: Iterator[String] = sc.parts.iterator
                val expressions: Iterator[Any] = args.iterator
                
                // [2] Populate a StringBuilder from the values in the iterators
                val sb = StringBuilder(strings.next.trim)
                while strings.hasNext do
                    sb.append(expressions.next.toString)
                    sb.append(strings.next)
                
                // [3] Convert the StringBuilder back to a String,
                // then apply an algorithm to capitalize each word in the string.
                sb.toString
                    .split(" ")
                    .map(_.trim)
                    .map(_.capitalize)
                    .mkString(" ")
            end caps
        end extension

        extension(sc: StringContext)
            def poo(args: Any*): String =
                val strings: Iterator[String] = sc.parts.iterator
                val expressions: Iterator[Any] = args.iterator
                val sb = StringBuilder(strings.next.trim)
                while strings.hasNext do
                    sb.append(expressions.next.toString)
                    sb.append(strings.next)
                sb.toString
                    .split(" ")
                    .map(_.trim)
                    .map("poo" + _)
                    .mkString(" ")
            end poo
        end extension

        case class Person(name: String, age: Int)

        def fromStringToPerson(line: String): Person = {
            val tokens = line.split(",")
            Person(tokens(0), tokens(1).toInt)
        }
        // ^^^ creates an instance of Person with fromStringToPerson("Bob,35")

        // Create a string interpolator that can do the same as above with...
        // person"Bob,35"

        implicit class PersonInterpolator(sc: StringContext) {
            def person(args: Any*): Person = {
                val parts = sc.parts  // the things in between the args
                val totalString = sc.s(args: _*) //

                val tokens = totalString.split(",")
                Person(tokens(0), tokens(1).toInt)
            }
        }
        val bob = person"Bob,34"

        println(caps"john c doe")
        println(poo"john c doe")
        println(bob.name)


    }
    // Creating Random Strings And Other Sequences
    def creatingRandomStrings(): Unit = {
        // Problem: When you try to generate a random string using the nextSting method of the 
        // Random class, you see a lot of unusual output or ? characters. The typical problem looks like this:
        val r = scala.util.Random()
        // val r: scala.util.Random = scala.util.Random@6b91c4c9
        println(r.nextString(10))  //噠Ȧꕫ털欙᧚ገ芩Ṋỳ  - wtf?

        // To only return alphanumeric characters in your random strings:
        val randString1 = Random.alphanumeric.take(10).mkString
        val randString2 = Random.alphanumeric.take(10).mkString

        println(randString1)  // ekXj1EgBfZ
        println(randString2)  // aHw9vcqmRv

        // Random.alphanumeric returns a LazyList, so use take(10).mkString to get the first n characters from the stream.

        // Note* Because LazyList is fully grown and living in it's mom's basement, it computes its elements only when 
        // they are needed, so you have to use .mkString to force it to actually get off it's ass and make a string.

        // Creating random sequences
        val rando = scala.util.Random
        val randomSeq = for i <- 0 to rando.nextInt(10) yield rando.nextPrintableChar
        // nextPrintableChar has a character range of ASCII characters 33 - 126
        println(randomSeq) // Vector(T, !, \, a, O)
        // again, to make a string out of it -
        println(randomSeq.mkString) // 4Wqyyfg0
        // ASCII ref: https://www.asciitable.com
        // https://oreil.ly/PEijH
    }
  
}
