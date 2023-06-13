### Monoid Typeclass

```scala
trait Monoid[A] extends Semigroup[A] {
    def combine(x: A, y: A): A  // <-- from Semigroup[A]

    def empty: A
}
```

#### Monoid Laws

```scala
trait MonoidLaws[A] {
    implicit override def S: Monoid[A]

    def semigroupAssociative(x: A, y: A, z: A): IsEq[A] =
        S.combine(S.combine(x, y), z) <-> S.combine(x, S.combine(y, z))

    def leftIdentify(x: A): IsEq[A] =
        S.combine(S.empty, x) <-> x

    def rightIdentity(x: A): IsEq[A] =
        S.combine(x, S.empty) <-> x
}
```

