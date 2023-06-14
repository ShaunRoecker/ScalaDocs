

```scala

trait Applicative[F[_]] extends Apply[F] ... {
    def pure[A](x: A): F[A]

    def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
}


trait ApplicativeLaws {
    def applicativeIdentity[A](fa: F[A]): IsEq[F[A]] =
        F.pure((a: A) => a).ap(fa) <-> fa


    def applicativeHomomorphism[A, B](a: A, f: A => B): IsEq[F[B]] =
        F.pure(f).ap(F.pure(a)) <-> F.pure(f(a))

    
    def applicativeInterchange[A, B](a: A, ff: F[A => B]): IsEq[F[B]] =
        ff.ap(F.pure(a)) <-> F.pure((f: A => B) => f(a)).ap(ff)

    
    def applicativeComposition[A, B, C](
        fa: F[A],
        fab: F[A => B],
        fbc: F[B => C]
      ): IsEq[F[C]] = {
        val compose: (B => C) => (A => B) => (A => C) = _.compose
        F.pure(compose).ap(fbc).ap(fab).ap(fa) <-> fbc.ap(fab.ap(fa))
      }
    // imagine some f: A => B, g: B => C and a: A  ==>
            // (g compose f)(a) <-> g(f(a))
}

```



