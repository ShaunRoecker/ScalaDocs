package functional.adt



// Simple Option like implementation

sealed trait Optional[+A] extends Product with Serializable:
    def isSome: Boolean
    def isNone: Boolean = !isSome


case object Nope extends Optional[Nothing]:
    def isSome: Boolean = false


case class Item[A](value: A) extends Optional[A]:
    def isSome: Boolean = true



object Optional:
    extension[A](opt: Optional[A])
        def map[B](f: A => B): Optional[B] =
            opt match
                case Item(a) => Item(f(a))
                case Nope => Nope


        def flatMap[B](f: A => Optional[B]): Optional[B] =
            opt match
                case Item(a) => f(a)
                case Nope => Nope

                
        def withFilter(p: A => Boolean): Optional[A] =
            opt match
                case Item(a) if p(a) => opt
                case _ => Nope 
            

        
            
            

