object Givens extends App {

    def main(): Unit = {
        println("PACKAGING AND IMPORTS")
        println(genericAdder(1, 1)) //2
        
    }
    main()

    // trait Monoid[T]:
    //     def unit: T
    //     def add(a: T, b: T): T

    // def sum[T](list: List[T])(using m: monoid[T]): T =
    //     if(list.isEmpty) m.unit else m.add(list.head, sum(list.tail))
    ///////////////////////////////////////////////////////////////////
    // package co.kbhr.givens

    object Adder:
        trait Adder[T]:
            def add(a: T, b: T): T
        given Adder[Int] with
            def add(a: Int, b: Int): Int = a + b
        given Adder[String] with
            def add(a: String, b: String): String = "" + (a.toInt + b.toInt)
    ///////////////////////////////////////////////////////////////////
    // package different

    // import it into the current scope with these two import statements:

    // package co.kbhr.givens.Adder.*
    // package co.kbhr.givens.Adder.given
    import Adder.*
    import Adder.given

    def genericAdder[A](x: A, y: A)(using adder: Adder[A]): A = adder.add(x, y)
        

        
  
}
