package functional.typeclasses.sorter


trait Sorter[F[_], A]:
    def sort(container: F[A]): F[A]



object SorterInstances:

    implicit object sortListDesc extends Sorter[List, Int] {
        override def sort(container: List[Int]): List[Int] = 
            container.sortWith(_ > _)
    }


object SorterInterface:

    extension[A](xs: List[A])
        def sortDesc(implicit ev: Sorter[List, A]): List[A] =
            ev.sort(xs)

    