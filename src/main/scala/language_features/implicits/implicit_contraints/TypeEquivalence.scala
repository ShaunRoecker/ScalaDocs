package language.features.implicits.contraints.equivalence



case class Cell[T](item: T) { self =>
    def *[U: Numeric](other: Cell[U])(implicit ev: T =:= U): Cell[U] =
        Cell(implicitly[Numeric[U]].times(self.item, other.item))

}



