### Foldable
```scala
trait Foldable[F[_]] {
    def foldLeft[A, B](fa: F[A], b: B)(f: (B, A) => B): B

    def foldRight[A, B](fa: F[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B]

    // foldMap apply f to the elements and then combine with the monoid
    def foldMap[A, B](fa: F[A])(f: A => B)(implicit M: Monoid[B]) =
        foldLeft(fa, M.empty){ (b, a) => M.combine(b, f(a))}  

    // foldMap[Int, String](List(1, 2, 3))(_.show) === "123"
}

trait FoldMapLaws {
    def leftFoldConsistentWithFoldMap[A, B](fa: F[A], f: A => B)(implicit M: Monoid[B]): IsEq[B] =
        fa.foldMap(f) <-> fa.foldLeft(M.empty) { (b, a) => b |+| f(a) }

    def rightFoldConsistentWithFoldMap[A, B](fa: F[A], f: A => B)(implicit M: Monoid[B]): IsEq[B] =
        fa.foldMap(f) <-> fa.foldRight(Later(M.empty)) { (a, lb) => lb.map(f(a) |+| _)}.value
    

}
```