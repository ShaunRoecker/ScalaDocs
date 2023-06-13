```scala
trait Eq[A] {
    def eqv(fst: A, snd: A): Boolean
}


trait EqLaws[A] {
    implicit def E: Eq[A] 
    // Eq is a Cats typeclass for equality

    def reflexivityEq(x: A): IsEq[A] 
    // means each A should be equal to itself

    def symmetryEq(x: A, y: A): IsEq[Boolean] 
    // tests whether:   E.eqv(x, y) <-> E.eqv(y, x)  ('<->' means '==')

    def antiSymmetryEq(x: A, y: A, f: A => A): IsEq[Boolean] 
    // tests whether:   (!E.eqv(x, y)  || E.eqv(f(x), f(y))) <-> true  --[if x = y then f(x) = f(y)]

    def transitivityEq(x: A, y: A, z: A): IsEq[Boolean] 
    // tests whether:   (!(E.eqv(x, y) && E.eqv(y, z) || E.eqv(x, z)) <-> true  --[if x = y and y = z then x = z]

}
```

