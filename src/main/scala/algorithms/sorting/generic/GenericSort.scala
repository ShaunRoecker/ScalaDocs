package algorithms.sorting.generic


object GenericSort:

    trait CompareT[T]:
        def >(other: T): Boolean
        def <(other: T): Boolean
    
        
    case class Distance(dist: Int) extends CompareT[Distance]{ self =>
        def <(other: Distance): Boolean = self.dist < other.dist
        def >(other: Distance): Boolean = self.dist > other.dist
    }


    def insertDistance(item: Distance, rest: List[Distance]): List[Distance] =
        rest match
            case Nil => List(item)
            case h :: _ if item < h => item :: rest
            case h :: t => h :: insertDistance(item, t)


    def sortDistances(xs: List[Distance]): List[Distance] =
        xs match
            case Nil => Nil
            case x :: xs => insertDistance(x, sortDistances(xs))
            


    // Generic sort using CompareT
    extension[T <: CompareT[T]](xs: List[T])
        def genericInsert(item: T): List[T] =
            xs match
                case Nil => List(item)
                case h :: _ if item < h => item :: xs
                case h :: t => h :: t.genericInsert(item)


        def genericSort: List[T] =
            xs match
                case Nil => Nil
                case h :: t => t.genericSort.genericInsert(h)
        
        
    val listDist: List[Distance] = List(
        Distance(10),
        Distance(5),
        Distance(9),
        Distance(12),
    )

    println(listDist.genericSort)
    // List(Distance(5), Distance(9), Distance(10), Distance(12))


    case class Person(name: String) extends CompareT[Person]{ self =>
        def <(other: Person): Boolean = 
            self.name.toLowerCase < other.name.toLowerCase
        def >(other: Person): Boolean = 
            self.name.toLowerCase > other.name.toLowerCase  
    }

    val listPerson: List[Person] = List(
        Person("James"),
        Person("Zach"),
        Person("Alan"),
        Person("Wilbur"),
    )

    println(listPerson.genericSort)
    // List(Person(Alan), Person(James), Person(Wilbur), Person(Zach))
    //
