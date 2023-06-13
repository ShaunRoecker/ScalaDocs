package typeclasses.catstypeclasses.order.example


import cats._
import cats.implicits._


final case class Account(id: Long, number: String, balance: Double, owner: String)


object Account {

    implicit def orderById(implicit orderLong: Order[Long]): Order[Account] =
        Order.from((a, b) => orderLong.compare(a.id, b.id))

    
    object Instances {
        ///////// These first 2 equate the same thing different ways...
        // Order Account By Number
        implicit val orderByNumber1: Order[Account] = 
            Order.by(account => account.number)


        implicit def orderByNumber3(implicit orderString: Order[String]): Order[Account] =
            Order.from((a, b) => orderString.compare(a.number, b.number))

        /////////

        /////////
        // Order Account By Balance
        implicit val orderByBalance1: Order[Account] = 
            Order.by(account => account.balance)


        implicit def orderByBalance2(implicit orderDouble: Order[Double]): Order[Account] =
            Order.from((a, b) => orderDouble.compare(a.balance, b.balance))

        ////////
    }

}

object OrderTest {

    implicit class listOp[A](val xs: List[A]) {

        def sort(implicit orderA: Order[A]): List[A] =
            xs.sorted(orderA.toOrdering) // Cats aux method to convert Order to standard Ordering
    
    }
    
    val account1: Account = Account(1, "983-45", 5000, "Leandro")
    val account2: Account = Account(2, "123-45", 2500, "Martin")
    val account3: Account = Account(3, "576-67", 7800, "Samuel")


    val accountList = List(account1, account2, account3)



    // Universal orders by ID
    println(accountList.sort) 
    // List(Account(1,983-45,5000.0,Leandro), Account(2,123-45,2500.0,Martin), Account(3,576-67,7800.0,Samuel))

    // Order By Number
    println(accountList.sort(Account.Instances.orderByNumber3))
    // List(Account(2,123-45,2500.0,Martin), Account(3,576-67,7800.0,Samuel), Account(1,983-45,5000.0,Leandro))

    // Order By Balance
    println(accountList.sort(Account.Instances.orderByBalance1))
    // List(Account(2,123-45,2500.0,Martin), Account(1,983-45,5000.0,Leandro), Account(3,576-67,7800.0,Samuel))
    

    
}
