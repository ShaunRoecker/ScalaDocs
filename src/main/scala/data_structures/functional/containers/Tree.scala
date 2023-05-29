package datastructures.functional.containers




sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A] 


enum TreeS3[+A]:
    case Leaf(value: A)
    case Branch(left: TreeS3[A], right: TreeS3[A])


object Tree:
    extension[A](tree: Tree[A])
        def size: Int =
            tree match
                case Leaf(_) => 1
                case Branch(l, r) => l.size + 1 + r.size

        def depth: Int =
            tree match
                case Leaf(_) => 0
                case Branch(l, r) => 1 + (l.depth max r.depth)

        def map[B](f: A => B): Tree[B] =
            tree match
                case Leaf(a) => Leaf(f(a))
                case Branch(l, r) => Branch(l.map(f), r.map(f))

        def fold[B](f: A => B)(g: (B, B) => B): B =
            tree match
                case Leaf(a) => f(a)
                case Branch(l, r) => g(l.fold(f)(g), r.fold(f)(g))

        def sizeViaFold: Int =
            tree.fold(a => 1)(_ + 1 + _)

        def depthViaFold: Int =
            tree.fold(a => 0)((d1, d2) => 1 + (d1 max d2))

        

    extension(tree: Tree[Int])
        def maximum: Int =
            tree match
                case Leaf(a) => a
                case Branch(l, r) => l.maximum max r.maximum 

        def maximumWithFold: Int =
            tree.fold(a => a)((l, r) => l max r)

        def firstPositive: Int =
            tree match
                case Leaf(i) => i
                case Branch(l, r) => 
                    val lpos = l.firstPositive
                    if lpos > 0 then lpos else r.firstPositive 
