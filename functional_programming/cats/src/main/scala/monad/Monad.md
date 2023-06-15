

```scala
trait Monad[F[_]] extends ... {
    def pure[A](x: A): F[A]

    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

    def tailRecM[A, B](a: A)(f: A => F[Either[A, B]]): F[B]

}



trait MonadLaws {
    def monadLeftIdentity[A, B](a: A, f: A => F[B]): IsEq[F[B]] =
        F.pure(a).flatMap(f) <-> f(a)

    def monadRightIdentity[A](fa: F[A]): IsEq[F[A]] =
        fa.flatMap(F.pure) <-> fa

    def flatMapAssociativity[A, B, C](fa: F[A], f: F[B], g: B => F[C]): IsEq[F[C]] =
        fa.flatMap(f).flatMap(g) <-> fa.flatMap(a => f(a).flatMap(g))

    
}
```

