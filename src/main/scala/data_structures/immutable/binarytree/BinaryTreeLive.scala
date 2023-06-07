package datastructures.immutable.binarytree

import scala.collection.immutable.Queue



case class BinaryNode[K, V](
    key: K, 
    value: V, 
    left: Option[BinaryNode[K, V]] = None, 
    right: Option[BinaryNode[K, V]] = None
)


class UnBalancedBinarySearch[K, V](
    root: BinaryNode[K, V], 
    ord: Ordering[K]
) extends BinarySearchTree[K, V] { self =>

    private def search(key: K, node: BinaryNode[K, V]): Option[V] =
        key match
            case node.key => 
                Some(node.value)
            case k if ord.lt(k, node.key) => 
                node.left.flatMap(n => search(k, n))
            case k => 
                node.right.flatMap(n => search(k, n))

    
    def search(key: K): Option[V] = search(key, root)
        

    private def insert(key: K, v: V, node: BinaryNode[K, V]): BinaryNode[K, V] = 
        key match
            case node.key => node.copy(value = v)
            case k if ord.lt(k, node.key) =>
                val newLeft =
                    node.left.map(n => insert(k, v, n)).orElse(Some(BinaryNode(k, v)))
                node.copy(left = newLeft)
            case k if ord.lt(k, node.key) =>
                val newRight =
                    node.right.map(n => insert(k, v, n)).orElse(Some(BinaryNode(k, v)))
                node.copy(right = newRight)
        

    def insert(key: K, value: V): UnBalancedBinarySearch[K, V] = 
        new UnBalancedBinarySearch[K, V](insert(key, value, root), ord)


    // Depth-First Search
    def foreachDFS(f: (K, V) => Unit): Unit =
        foreachDFS(f, root)


    private def foreachDFS(f: (K, V) => Unit, node: BinaryNode[K, V]): Unit =
        node.left.foreach(n => foreachDFS(f, n))
        f(node.key, node.value)
        node.right.foreach(n => foreachDFS(f, n))

    
    // Breadth-First Search
    def foreachBFS(f: (K, V) => Unit): Unit =
        val sq = LazyList.iterate(Queue(root)) { q => 
            val (node, tail) = q.dequeue
            tail ++ node.left ++ node.right    
        }
        sq.takeWhile( q => q.nonEmpty).foreach(q => f(q.head.key, q.head.value))


    
}


object UnBalancedBinarySearch:

    def apply[K, V](key: K, value: V)(using ord: Ordering[K]) =
        new UnBalancedBinarySearch(BinaryNode(key, value), ord)

    

