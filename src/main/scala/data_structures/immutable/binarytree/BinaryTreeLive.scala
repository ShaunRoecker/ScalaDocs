package datastructures.immutable.binarytree



case class BinaryNode[K, V](
    key: K, 
    value: V, 
    left: Option[BinaryNode[K, V]] = None, 
    right: Option[BinaryNode[K, V]] = None
)


case class UnBalancedBinarySearch[K, V](
    root: BinaryNode[K, V], 
    ord: Ordering[K]
) extends BinarySearchTree[K, V]:

    def search(key: K): Option[V] = search(key, root)

    private def search(key: K, node: BinaryNode[K, V]): Option[V] =
        key match
            case node.key => 
                Some(node.value)
            case k if ord.lt(k, node.key) => 
                node.left.flatMap(n => search(k, n))
            case k => 
                node.right.flatMap(n => search(k, n))
        

    private def insert(key: K, v: V, root: BinaryNode[K, V]): BinaryNode[K, V] = 
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
        
        

    def insert(key: K, value: V): BinarySearchTree[K, V] = 
        new BinarySearchTree[K, V](insert(key, value, root), ord)

object UnBalancedBinarySearch:

    def apply[K, V](key: K, value: V)(ord: Ordering[K]): UnBalancedBinarySearch[K, V] =
        new UnBalancedBinarySearch(BinaryNode(key, value), ord)

    

