package packaging 

import scala.strings.Strings //can import a different package and use it in the current package


object PackagingAndImports extends App {
  def main(): Unit = {
        println("PACKAGING AND IMPORTS")
        // introduction()
        // Strings.testingStringEquality()
        // println(Strings.testString)
        // importingOneOrMoreMembers()
        // renamingMembersOnImport()
        // hidingAClassDuringTheImportProcess()
        // importingStaticMembers()
        // usingImportStatementsAnywhere()
        // importingGivens()
        // These two packages are automatically imported into the scope
        // of all your source code files:
            // java.lang.*
            // scala.*
    }
    main()

    def introduction(): Unit = {
        // In Scala you can...
        // -Place import statements anywhere
        // -Import packages, classes, objects, and methods
        // -Hide and rename members when you import them

        // the * character means "import every member" in those packages

        // THE PREDEF OBJECT

        // Predef source code: https://oreil.ly/KtxXV
    }
    // PACKAGING WITH THE CURLY BRACES STYLE NOTATION
    def packagingWithTheCurlyBracesStyleNotation(): Unit = {
        // You want to use a nested style packaging notation, 
        // similar to the namespace notation in C++ and C#
        
    }
    // IMPORTING ONE OR MORE MEMBERS
    def importingOneOrMoreMembers(): Unit = {
        // Problem: You want to import one or more into the scope of your current code.
        import java.io.File
        import java.io.IOException
        import java.io.FileNotFoundException

        // or you can be more concise liek this
        import java.io.{File, IOException, FileNotFoundException}

        // you can import everything from a package like this
        import java.io.*


    }
    // RENAMING MEMBERS ON IMPORT
    def renamingMembersOnImport(): Unit = {
        // Problem: You want to rename members when you import them
        // to help avoid namespace collisions or confusion

        import java.awt.{List as AwList}
        import java.time.{LocalDate as Dt, Instant as Inst, Duration as Dur, *}

        

    }
    // HIDING A CLASS DURING THE IMPORT PROCESS
    def hidingAClassDuringTheImportProcess(): Unit = {
        // Problem: To avoid naming conflicts or confusion,
        // you want to hide one or more classes while importing 
        // other members from the same package

        // The following import hides the Random class,
        // while importing everything else from java.util
        import java.util.{Random => _, *}

        // hide multiple members:
        import java.util.{List => _, Map => _, Set as JSet, *}
        
    }
    // IMPORTING STATIC MEMBERS
    def importingStaticMembers(): Unit = {
        // Problem: You want to import members in a way similar to the Java
        // static import approach, so you can refer to member names directly,
        // without having to prefix them with their package or class names.

        // static members mean the methods or anything specific you want from
        // the class you are importing so you dont have to name the class every
        // time you use the method

        import scala.math.cos
        
        val x = cos(0) //1.0

        import scala.math.*

        val y = sin(0)

        import java.awt.Color

        println(Color.BLUE) //java.awt.Color[r=0,g=0,b=255]
        // println(BLUE) //error

        // to use BLUE without Color.BLUE

        import java.awt.Color.*
        println(BLUE) //java.awt.Color[r=0,g=0,b=255]

        // Objects and Enums are other great candidates for this technique

        // object StringUtils:
        //     def truncate(s: String, length: Int): String = s.take(length)
        //     def leftTrim(s: String): String = s.replaceAll("^\\s+", "")

        // you can import and use its methods like this:
        import packaging.StringUtils.*
        println(truncateL("truncate this string", 10)) //truncate t
        println(truncateR("truncate this string", 10)) //his string
        println(leftTrim("    left trim this string")) //left trim this string
     
        // Similarly you can import enumerations
        // enum Day:
        //     case Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday

        import packaging.Day.*
        println(Monday)

        // Some devs don't like static imports but it really does make it easier
        // to read

        val dateStatic = Sunday
        val date = Day.Sunday

        if date == Day.Saturday || date == Day.Sunday then 
            println("It's the weekend!")

        // vs.

        if dateStatic == Saturday || dateStatic == Sunday then
            println("It's the weekend!")

    }
    // USING IMPORT STATEMENTS ANYWHERE
    def usingImportStatementsAnywhere(): Unit = {
        // Problem: You want to use an import statement somewhere 
        // other than at the top of a file, generally to limit the scope 
        // of the import or to make your code cleaner.

        // You can place an import statement almost anywhere inside a program
        // Basically they can go anywhere, but they need to be before the 
        // code that references the imported member
        import scala.util.Random
        val random = Random()

        println(random.nextInt(5))
        println(random.between(10, 21)) //min inclusive max exclusive


    }
    // IMPORTING GIVENS
    def importingGivens(): Unit = {
        // Problem: You need to import one or more given instances into the current scope,
        // while possibly importing types from that same package at the same time.

        // A given instance, known more simply as a 'given' will typically defined in a 
        // separate module, and it must be imported into the current scope with a special 
        // import statement. For example, when you have this given code in an object named 
        // Adder, in a package named co.kbhr.givens:

        ///////////////////////////////////////////////////////////////////
        // package co.kbhr.givens

        object Adder:
            trait Adder[T]:
                def add(a: T, b: T): T
            given Adder[Int] with
                def add(a: Int, b: Int): Int = a + b
            given Adder[String] with
                def add(a: String, b: String): String = "" + (a.toInt + b.toInt)
        ///////////////////////////////////////////////////////////////////
        // package different

        // import it into the current scope with these two import statements:

        // package co.kbhr.givens.Adder.*
        // package co.kbhr.givens.Adder.given
        import Adder.*
        import Adder.given

        def genericAdder[A](x: A, y: A)(using adder: Adder[A]): A = adder.add(x, y)
        println(genericAdder(1, 1)) //2

        // can also compine the two import statements into one
        // package co.kbhr.givens.Adder.{given, *}


        // package co.kbhr.givens.Adder.*
        // package co.kbhr.givens.Adder.{given Adder[Int], given Adder[String]}


        // docs on importing givens : https://oreil.ly/aobrq
        // docs on givens : https://oreil.ly/5rep7

    }

}
object StringUtils:
    def truncateL(s: String, length: Int): String = s.take(length)
    def truncateR(s: String, length: Int): String = s.takeRight(length)
    def leftTrim(s: String): String = s.replaceAll("^\\s+", "")

enum Day:
    case Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday

