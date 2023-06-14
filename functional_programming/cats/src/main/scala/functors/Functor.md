

```scala
trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
}


trait FunctorLaws {
    def covariantIdentity[A](fa: F[A]): IsEq[F[A]] =
        fa.map(identity) <-> fa


    def covariantComposition[A, B, C](fa: F[A], f: A => B, g: B => C): IsEq[F[C]] =
        fa.map(f).map(f) <-> fa.map(f.andThen(g))

}
```