
The show is a typeclass to represent a type as a String
```scala
trait Show[A] {
    def show(a: A): String
}
```