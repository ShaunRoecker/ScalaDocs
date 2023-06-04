package functional.typeclasses


trait Sorter[F[_], A]:
    def sort(container: F[A]): F[A]



object SorterInstances:

    implicit object sortListDesc extends Sorter[List, Int] {
        override def sort(container: List[Int]): List[Int] = 
            container.sortWith(_ > _)
    }

