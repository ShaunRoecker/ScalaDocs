package langfeat.`extension-methods`.`implicit-classes`


object ImplicitClasses {
    // Implicit classes give us the ability to extend the functionality of existing types
    object Implicits {
        // The type of the val value is the type that we extend functionality to:
        implicit class IntExtender(val value: Int) extends AnyVal { self =>
            // we can then create methods in this class that the compiler will turn to
            // if it can't find the method on the Int companion object.
            def calculateTo100 = 
                100 - value

        }

        implicit class StringExtender(val value: String) extends AnyVal { self =>
            def returnDigits: String = 
                value.filter(_.isDigit).mkString

            def sumDigitsInString = 
                value.filter(_.isDigit).map(s => s.toString.toInt).sum

            def sumDigitsInString2 = 
                value.filter(_.isDigit).map(s => (s.toInt - 48)).sum

        }
    }
    import Implicits._


    val distFrom100 = 
        52.calculateTo100

    println(distFrom100) // 48

    println(-30.calculateTo100) // 130

    val extractedDigits = 
        "Hello56 World8f".returnDigits

    println(extractedDigits) //568

    val sumDigits = 
        "Hello56 World8f".sumDigitsInString

    println(sumDigits) // 19

    val sumDigits2 = 
        "Hello56 World8f".sumDigitsInString2

    println(sumDigits2) // 19

    val sumDigitsDist100 = 
        "Hello56 World8f".sumDigitsInString.calculateTo100
    println(sumDigitsDist100) // 81   


}
