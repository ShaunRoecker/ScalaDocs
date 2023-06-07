package datastructures.immutable.binarytree

import datastructures.immutable.binarytree.BinarySearchTree


enum Color:
    case Red, Black



final case class RedBlackNode[K, V](
    key: K, 
    value: V, 
    color: Color, 
    left: Option[RedBlackNode[K, V]], 
    right: Option[RedBlackNode[K, V]]
)


class RedBlackTree[K, V](root: RedBlackNode[K, V], ord: Ordering[K]) extends BinarySearchTree[K, V]:
    override def search(key: K): Option[V] = ???

    override def insert(key: K, value: V): BinarySearchTree[K, V] = ???

    private def rightRotate(parent: RedBlackNode[K, V]): RedBlackNode[K, V] =
        val pivot = parent.left.get
        val newRightChild = parent.copy(left = pivot.right)
        pivot.copy(right = Some(newRightChild))

    private def leftRotate(parent: RedBlackNode[K, V]): RedBlackNode[K, V] =
        val pivot = parent.left.get
        val newLeftChild = parent.copy(left = pivot.left)
        pivot.copy(left = Some(newLeftChild))

    

