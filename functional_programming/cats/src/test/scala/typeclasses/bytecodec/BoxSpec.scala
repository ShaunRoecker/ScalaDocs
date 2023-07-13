
// import cats._
// import cats.implicits._
// import org.scalacheck.{Gen, Arbitrary}
// import org.scalatest.funsuite.AnyFunSuite
// import org.scalatest.prop.Configuration
// import org.typelevel.discipline.scalatest.FunSuiteDiscipline
// import testing.box.Box
// import testing.box.Box._
// import org.scalacheck.Arbitrary
// import cats.kernel.laws.discipline.EqTests
// import cats.laws.discipline.MonadTests


// class BoxSpec extends AnyFunSuite with Configuration with FunSuiteDiscipline {
//     val genInt: Gen[Int] = Gen.choose(min=1, max=10)
//     val genInt2: Gen[Int] = Gen.oneOf(t0=1, t1=5, tn=10)
//     val genString: Gen[String] = Gen.alphaNumStr
//     val genString2: Gen[String] = Gen.numStr
//     val genTuple: Gen[(Int, String)] = 
//         for {
//             i <- genInt
//             s <- genString
//         } yield (i, s)

//     val arbInt: Arbitrary[Int] = Arbitrary(genInt)

//     implicit def arbBoxA[A](implicit arbA: Arbitrary[A]): Arbitrary[Box[A]] =
//         Arbitrary(arbA.arbitrary.map(Box.apply))

//     implicit def arbFun[A](implicit arbA: Arbitrary[A]): Arbitrary[A => A] =
//         Arbitrary(arbA.arbitrary.map(a => (_: A) => a))

    
//     // test("Wrapping and unwrapping yields original value") {
//     //     implicit val myArb: Arbitrary[Int] = arbInt
//     //     forAll(genInt, genString) { (i: Int, s: String) =>
//     //         assert(Box(i).value eqv i)
//     //         assert(Box(s).value eqv i)    
//     //     }
//     // }


//     checkAll("Eq[Box[Int]]", EqTests[Box[Int]].eqv)
//     checkAll("Monad[Box]", MonadTests[Box].monad[Int, Int, Int])
    

// }


