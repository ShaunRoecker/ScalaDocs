package typeclasses.catstypeclasses.eq.example


import cats._
import cats.implicits._


final case class Account(id: Long, number: String, balance: Double, owner: String)


object Account {

    implicit val universalEq: Eq[Account] = 
        Eq.fromUniversalEquals  

    object Instances {
        ///////// These first 3 equate the same thing different ways...
        // Equate Account By Id
        implicit val byIdEq1: Eq[Account] = 
            Eq.instance[Account]((a, b) => Eq[Long].eqv(a.id, b.id))

        implicit def byIdEq2(implicit eqLong: Eq[Long]): Eq[Account] = 
            Eq.instance[Account]((a, b) => eqLong.eqv(a.id, b.id))

        implicit def byIdEq3(implicit eqLong: Eq[Long]): Eq[Account] = 
            Eq.by(account => account.id)
            // also-->   Eq.by(_.id)

        ////////////

        ////////////
        // Equate Account By Number
        implicit val byNumberEq1: Eq[Account] =
            Eq.instance[Account]((a, b) => Eq[String].eqv(a.number, b.number))

        implicit def byNumberEq2(implicit eqString: Eq[String]): Eq[Account] =
            Eq.instance[Account]((a, b) => eqString.eqv(a.number, b.number))

        implicit def byNumberEq3(implicit eqString: Eq[String]): Eq[Account] =
            Eq.by(account => account.number)
            // also--> Eq.by(_.number)     
            
        ////////////
        // Equate Account By Balance
        implicit val byBalanceEq1: Eq[Account] =
            Eq.instance[Account]((a, b) => Eq[Double].eqv(a.balance, b.balance))

        implicit def byBalanceEq2(implicit eqDouble: Eq[Double]): Eq[Account] =
            Eq.instance[Account]((a, b) => eqDouble.eqv(a.balance, b.balance))

        implicit def byBalanceEq3(implicit eqDouble: Eq[Double]): Eq[Account] =
            Eq.by(account => account.balance)
            // Eq.by(_.balance)

    }

}



object AccoutTests {
    
    val account1: Account = Account(1, "123-45", 1000, "Leandro")
    val account2: Account = Account(2, "123-45", 1500, "Martin")
    val account3: Account = Account(2, "876-67", 1500, "Samuel")

    // Universal
    println("Universal")
    println(Eq[Account].eqv(account1, account2))  // false

    // ById
    println("ID")
    println(Account.Instances.byIdEq1.eqv(account1, account2)) // false
    println(Account.Instances.byIdEq2.eqv(account2, account3)) // true


    // ByNumber
    println("Number")
    println(Account.Instances.byNumberEq1.eqv(account1, account2)) // true
    println(Account.Instances.byNumberEq2.eqv(account2, account3)) // false

    println(account1 === account2) // false (universal)

    // implicit val eqToUse = Account.Instances.byNumberEq3
    println(account1 === account2)  // true 


    // ByBalance
    println("Balance")
    // implicit val eqByBal = Account.Instances.byBalanceEq2
    println(account1 === account2) // false
    println(account2 === account3) // true


}

