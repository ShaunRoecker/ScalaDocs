

```scala
trait Order[A] extends PartialOrder[A] {
    def compare(fst: A, snd: A): Int
}
// < 0 if fst < snd
// = 0 if fst = snd
// > 0 if fst > snd

trait OrderLaws[A] {
    implicit override def E: Order[A]

    def reflexivityLt(x: A): IsEq[Boolean] =
        E.lteqv(x, x) <-> true


    def antisymmetry(x: A, y: A): IsEq[Boolean] =
        (!(E.lteqv(x, y) && E.lteqv(y, x)) || E.eqv(x, y)) <-> true


    def transitivity(x: A, y: A, z: A): IsEq[Boolean] = 
        (!(E.lteqv(x, y) && E.lteqv(y, z)) || E.eqv(x, z)) <-> true


    def totality(x: A, y: A): IsEq[Boolean] =
        (E.lteqv(x, y) || E.lteqv(y, x)) <-> true

    
}
```
