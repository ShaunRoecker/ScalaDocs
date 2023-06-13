package typeclasses.catstypeclasses.show.example


import cats._
import cats.implicits._


final case class Account(id: Long, number: String, balance: Double, owner: String)


object Account {

    implicit val toStringShow: Show[Account] = Show.fromToString


    object Instances {
        implicit val byOwnerAndBalance: Show[Account] = 
            Show.show { account =>
                s"${account.owner} - $$${account.balance}"
            }

        implicit val belongsToOwner: Show[Account] =
            Show.show { account =>
                s"This account belongs to ${account.owner}"
            }

        
    }


}



object ShowTest {
    

    implicit class AccountOps(acct: Account) {
        def show(implicit ev: Show[Account]): String = 
            ev.show(acct)
    }

    val account1: Account = Account(1, "983-45", 5000, "Leandro")
    val account2: Account = Account(2, "123-45", 2500, "Martin")
    val account3: Account = Account(3, "576-67", 7800, "Samuel")


    println(account1.show) 
        // Account(1,983-45,5000.0,Leandro)

    println(account1.show(Account.Instances.byOwnerAndBalance))
        // Leandro - $5000.0

    println(account1.show(Account.Instances.belongsToOwner))
        // This account belongs to Leandro

    
}