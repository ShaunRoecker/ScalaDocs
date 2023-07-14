package dependencyinjection

import cats._
import cats.implicits._
import cats.data._


object ReaderMonad extends App {

                    // Int => String
    val signReader: Reader[Int, String] = Reader(n => if(n > 0) "positive" else if(n < 0) "negative" else "zero")

    println(signReader.run(5)) // positive
    println(signReader.run(-5)) // negative
    println(signReader.run(0)) // zero

    val parityReader: Reader[Int, String] = Reader(n => if(n % 2 == 0) "even" else "odd")

    println(parityReader.run(2)) // even
    println(parityReader.run(3)) // odd


    // We can combine Readers to get more useful operations

    val descriptionReader: Reader[Int, String] = 
        for {
            sign <- signReader
            parity <- parityReader
        } yield s"$sign and $parity"

    println(descriptionReader.run(-4)) // negative and even


    val addOneReader: Reader[Int, Int] = 
        for {
            env <- Reader((x: Int) => x)
        } yield env + 1


    case class Person(id: Long, name: String)
    case class Account(id: Long, ownerId: Long)

    trait AccountRepository {
        def findAccountById(id: Long): Account
    }

    trait PersonRepository {
        def findPersonById(id: Long): Person
    }

    def findNextAccount(id: Long): Reader[AccountRepository, Account] =
        for {
            acctRepo <- Reader(identity[AccountRepository])
            account = acctRepo.findAccountById(id + 1)
        } yield account

    
    def findOwnerNameByAccountId(id: Long) =
        for { 
            acctRepo <- Reader(identity[AccountRepository])
            personRepo <- Reader(identity[PersonRepository])
            account = acctRepo.findAccountById(id)
            owner = personRepo.findPersonById(account.ownerId)
        } yield owner.name

    




}


