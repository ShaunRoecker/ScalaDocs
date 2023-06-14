package higherkindedtypes



import cats._
import cats.implicits._
import concurrent.Future


final case class Account(id: Long, number: String, balance: Double, owner: String)

trait Database[F[_]] {
    def find(id: Long): F[Account]
}

object LiveDatabase extends Database[Future] {
    def find(id: Long): Future[Account] = ???
}

object LiveDatabase2 extends Database[Either[Throwable, *]] {
    def find(id: Long): Either[Throwable, Account] = ???
}



object HigherKindedTypes  {



    println()


}


