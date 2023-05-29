package datastructures.functional.errorhandling



trait Option[+A] extends Product with Serializable:
    def isSome: Boolean 
    def isNone: Boolean = !isSome

case object None extends Option[Nothing]:
    def isSome: Boolean = false

case class Some[A](get: A) extends Option[A]:
    def isSome: Boolean = true


object Option:
    extension[A](opt: Option[A])
        def map2[B, C](opt2: Option[B])(f: (A, B) => C): Option[C] = 
            for
                x <- opt
                y <- opt2
            yield f(x, y)


        def map[B](f: A => B): Option[B] =
            opt match
                case Some(a) => Some(f(a))
                case None => None

        def flatMap[B](f: A => Option[B]): Option[B] =
            opt match
                case None => None
                case Some(a) => f(a)


        def filter(p: A => Boolean): Option[A] = 
            opt match
                case Some(a) if (p(a)) => opt
                case _ => None

        
        def getOrElse[B >: A](default: => B): B =
            opt match
                case None => default
                case Some(a) => a


        def orElse[B >: A](ob: => Option[B]): Option[B] = 
            opt match
                case None => ob
                case _ => opt


        def isEmpty: Boolean =
            opt match
                case Some(_) => false
                case None => true
    
    extension[A](xs: List[Option[A]])

        def sequence: Option[List[A]] =
            xs match
                case Nil => Some(Nil)
                case h :: t => h.flatMap(hh => t.sequence.map(hh :: _))

        
    
    // def traverse[A, B](xs: List[A])(f: A => Option[B]): Option[List[B]] =
    //     xs match
    //         case Nil => Some(Nil)
    //         case h :: t => h.flatMap(hh => sequence(t).map(hh :: _))

            
    
    def lift[A, B](f: A => B): Option[A] => Option[B] =
        _.map(f)


    def Try[A](a: => A): Option[A] =
        try Some(a)
        catch { case e: Exception => None }

    

    

    

object Various:
    extension(xs: List[Int])
        def mean: Option[Double] =
            if (xs.isEmpty) None
            else Some(xs.sum / xs.length)

    extension(xs: Seq[Double])
        def mean: Option[Double] =
            if (xs.isEmpty) None
            else Some(xs.sum / xs.length)

        def variance: Option[Double] =
            if (xs.isEmpty) None 
            else 
                xs.mean.flatMap(m => xs.mean.map(x => math.pow(x - m, 2)))

    extension[A, B](xs: List[A])
        def traverse(f: A => Option[B]): Option[List[B]] =
            xs.foldRight[Option[List[B]]](Some(Nil))((h,t) => f(h).map2(t)(_ :: _))

