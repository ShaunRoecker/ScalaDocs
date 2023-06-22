package traversable

import foldable.MList
import foldable.MList._

import cats._
import cats.implicits._


object TraversableRun {
    case class Person(name: String)

    def findPersonByName(name: String): Option[Person] = {
        // dummy function
        val person = Person("John")
        if (person.name == name) Some(person) else None
    }

    def findPeopleByNames(names: MList[String]): Option[MList[Person]] = 
        names match {
            case MNil => Some(MNil)
            case MCons(h, t) => 
                (findPersonByName(h), findPeopleByNames(t)).mapN(MCons.apply)
        }

    

    implicit val listTraverse: Traverse[MList] = new Traverse[MList] {
        // required
        override def traverse[G[_]: Applicative, A, B](fa: MList[A])(f: A => G[B]): G[MList[B]] = 
            fa match {
                case MNil => Applicative[G].pure(MNil)
                case MCons(h, t) => (f(h), traverse(t)(f)).mapN(MCons.apply)
            }
            // implement traverse in terms of sequence and Functor
            // sequence(fa.map(f))


        // required
        override def foldLeft[A, B](fa: MList[A], b: B)(f: (B, A) => B): B =
            fa match {
                case MNil => b
                case MCons(h, t) => foldLeft(t, f(b, h))(f)
            }
        // required
        override def foldRight[A, B](fa: MList[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] = {
            def loop(as: MList[A]): Eval[B] =
                as match {
                    case MNil => lb
                    case MCons(h, t) => f(h, Eval.defer(loop(t)))
                }
            Eval.defer(loop(fa))
        }
        // Not required
        override def sequence[G[_], A](fga: MList[G[A]])(implicit G: Applicative[G]): G[MList[A]] = 
            traverse(fga)(identity)



    }

    // implement Functor typeclass
    implicit val listFunctor: Functor[MList] = new Functor[MList] {
        override def map[A, B](fa: MList[A])(f: A => B): MList[B] = 
            fa match {
                case MNil => MNil
                case MCons(h, t) => MCons(f(h), map(t)(f))
            }
        
    }


    val tv1 = Traverse[MList].sequence(MList(Option(5), Option(4)))
    println(tv1) // Some(MCons(5,MCons(4,MNil)))
     
    val tv2 = Traverse[MList].sequence(MList(Option(5), None))
    println(tv2) // None


    implicit val optionTraverse: Traverse[Option] = new Traverse[Option] {
        override def traverse[G[_], A, B](fa: Option[A])(f: A => G[B])(implicit G: Applicative[G]): G[Option[B]] = 
            fa match {
                case None => G.pure(None)
                case Some(a) => f(a).map(Some.apply)
            }

        override def foldLeft[A, B](fa: Option[A], b: B)(f: (B, A) => B): B = 
            fa match {
                case None => b
                case Some(a) => f(b, a)
            }

        override def foldRight[A, B](fa: Option[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] = 
            fa match {
                case None => lb
                case Some(a) => f(a, lb)
            }
    }

    val optT = optionTraverse.traverse(Some(5))(x => List(x + 1, x + 2)) 
    println(optT)  // List(Some(6), Some(7))

    val optT2 = optionTraverse.traverse[List, Int, Int](None)(x => List(x + 1, x + 2))
    println(optT2) // List(None)



}


