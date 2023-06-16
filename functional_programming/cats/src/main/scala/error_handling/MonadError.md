

```scala
trait MonadError[E, A] {
    def raiseError[A](e: E): F[A]

    def handleErrorWith[A](fa: F[A])(f: E => F[A]): F[A]

    def pure[A](x: A): F[A]

    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

    def tailRecM[A, B](a: A)(f: A => F[Either[A, B]]): F[B]

}


trait MonadErrorLaws {
    def MonadErrorLeftZero[A, B](e: E, f: A => F[B]): IsEq[F[B]] =
        F.flatMap(F.raiseError[A](e))(f) <-> F.raiseError[B](e)

}

```

