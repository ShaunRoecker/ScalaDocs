package typeclasses.catstypeclasses.models



import cats._
import cats.implicits._


final case class Account(id: Long, number: String, balance: Double, owner: String)


object Account {

    implicit val universalEq: Eq[Account] = 
        Eq.fromUniversalEquals  

    implicit def orderById(implicit orderLong: Order[Long]): Order[Account] =
        Order.from((a, b) => orderLong.compare(a.id, b.id))


    implicit val toStringShow: Show[Account] = Show.fromToString


    
    object EqInstances {
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


    object OrderInstances {
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


