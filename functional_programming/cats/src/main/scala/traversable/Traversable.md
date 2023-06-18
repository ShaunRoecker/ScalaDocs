### Traversable Typeclass

```scala
trait Traverse[F[_]] {
    def traverse[G[_]: Applicative, A, B](fa: F[A])(f: A => G[B]): G[F[B]]

    def foldLeft[A, B](fa: F[A], b: B)(f: (B, A) => B): B

    def foldRight[A, B](fa: F[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B]

}

trait TraverseLaws {
    def traverseIdentity[A, B](fa: F[A], f: A => B): IsEq[F[B]] =
        fa.traverse[Id, B](f) <-> F.map(fa)(f)

    def traverseSequentialComposition[A, B, C, M[_], N[_]](
        fa: F[A],
        f: A => M[B],
        g: B => N[C]
    )(implicit N: Applicative[N], M: Applicative[M]): IsEq[Nested[M, N, F[C]]] = {
        val lhs = Nested(M.map(fa.traverse(f))(fb => fb.traverse(g)))
        val rhs = fa.traverse[Nested[M, N, *], C](a => Nested(M.map(f(a))(g)))
        lhs <-> rhs
    }
}

```