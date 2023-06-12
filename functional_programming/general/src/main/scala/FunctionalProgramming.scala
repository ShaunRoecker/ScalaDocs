package functional

import functional.recursion.Recursion._
import functional.recursion.tailcall.SimpleMethods._
import functional.recursion.tailcall.Bounce._
import functional.recursion.tailcall.SimpleMethodsTailCall._
import functional.adt.Optional
import functional.adt.Optional._
import functional.adt.Item
import functional.adt.Nope




object FunctionalProgramming:
    @main 
    def main: Unit =
        println("Functional Programming")

        println(factorial(5)) //120
        println(5*4*3*2*1) // 120

        println(fib(9))


        println(odd(5).trampoline)  // true
        println(even(5).trampoline)  // false


        println(oddtc(5).result) // true

        println(Item(2).map(_ + 2))


        case class Address(street: String, city: String, state: String, zipCode: String)
        case class Person(first: String, last: String, address: Optional[Address])


        def zipForPerson(op: Optional[Person]): Optional[String] =
            for
                p <- op
                a <- p.address
            yield a.zipCode

        val optPerson = Item(Person("John", "Doe", Item(Address("123 main st", "Cityville", "AY", "45645"))))
        println(zipForPerson(optPerson)) // Item(45645)

        
        def zipForPersonLast(startsWith: String)(op: Optional[Person]): Optional[String] =
            for
                p <- op
                if p.last.toUpperCase.startsWith(startsWith.toUpperCase)
                a <- p.address
            yield a.zipCode

        

        val optListPerson = List(
            Item(Person("John", "Doe", Item(Address("123 main st", "Cityville", "AY", "45645")))),
            Item(Person("Sam", "Greene", Nope)),
            Item(Person("Sarah", "Dingo", Item(Address("456 secondary st", "Townsville", "UZ", "76789")))),
        )



        println(optListPerson.map(zipForPersonLast("D") _ )) // List(Item(45645), Nope, Item(76789))
      

        println(Item(42).map(identity) == Item(42))

        








        
        



    




