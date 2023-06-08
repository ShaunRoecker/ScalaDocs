package datastructures.immutable.binarytree

import datastructures.immutable.binarytree.BinarySearchTree


enum Color:
    case Red, Black

import Color.*

final case class RedBlackNode[K, V](
    key: K, 
    value: V, 
    color: Color = Red, 
    left: Option[RedBlackNode[K, V]] = None, 
    right: Option[RedBlackNode[K, V]] = None
):
    def isLeftChild(parent: RedBlackNode[K, V]): Boolean = parent.left.exists(_.key == key)
    def isRightChild(parent: RedBlackNode[K, V]): Boolean = parent.right.exists(_.key == key)
    def redChild(): Option[RedBlackNode[K, V]] = left.filter(_.color == Red).orElse(right.filter(_.color == Red))


class RedBlackTree[K, V](root: RedBlackNode[K, V], ord: Ordering[K]) extends BinarySearchTree[K, V]:
    
    override def search(key: K): Option[V] = search(root, key)


    private def search(node: RedBlackNode[K, V], key: K): Option[V] =
        key match
            case node.key => Some(node.value)
            case k if ord.lt(k, node.key) => node.left.flatMap(n => search(n, k))
            case k => node.right.flatMap(n => search(n, k))
        

    private def insert(node: RedBlackNode[K, V], key: K, v: V): RedBlackNode[K, V] = 
        key match
            case node.key => node.copy(value = v)
            case k if ord.lt(k, node.key) =>
                val newLeft =
                    node.left.map(n => insert(n, k, v)).orElse(Some(RedBlackNode(key = key, value = v)))
                val grandP = node.copy(left = newLeft)
                redBlackFix(grandP, newLeft.get, grandP.right).getOrElse(grandP)
            case k =>
                val newRight =
                    node.right.map(n => insert(n, k, v)).orElse(Some(RedBlackNode(key = key, value = v)))
                val grandP = node.copy(right = newRight)
                redBlackFix(grandP, newRight.get, grandP.left).getOrElse(grandP)
        

    def insert(key: K, value: V): RedBlackTree[K, V] = 
        new RedBlackTree(insert(root, key, value).copy(color = Black), ord)

    
    private def redBlackFix(
        grandP: RedBlackNode[K, V], 
        parent: RedBlackNode[K, V], 
        uncle: Option[RedBlackNode[K, V]]
    ): Option[RedBlackNode[K, V]] =
        val redChildOpt = if (parent.color == Red) parent.redChild() else None

        redChildOpt.map(child => 
            uncle.map(_.color).getOrElse(Black) match

                case Red if parent.isLeftChild(grandP) =>
                    val newP = parent.copy(color = Black)
                    val newU = uncle.map(_.copy(color = Black))
                    grandP.copy(color = Red, left = Some(newP), right = newU)

                case Black if parent.isLeftChild(grandP) =>
                    val rotP = if (child.isRightChild(parent)) leftRotate(parent) else parent
                    val newP = rotP.copy(color = Black)
                    val newG = grandP.copy(color = Red, left = Some(newP))
                    rightRotate(newG)

                case Red if parent.isRightChild(grandP) =>
                    val newP = parent.copy(color = Black)
                    val newU = uncle.map(_.copy(color = Black))
                    grandP.copy(color = Red, right = Some(newP), left = newU)

                case Black if parent.isRightChild(grandP) =>
                    val rotP = if (child.isLeftChild(parent)) rightRotate(parent) else parent
                    val newP = rotP.copy(color = Black)
                    val newG = grandP.copy(color = Red, right = Some(newP))
                    leftRotate(newG)
        )

        

    private def rightRotate(parent: RedBlackNode[K, V]): RedBlackNode[K, V] =
        val pivot = parent.left.get
        val newRightChild = parent.copy(left = pivot.right)
        pivot.copy(right = Some(newRightChild))


    private def leftRotate(parent: RedBlackNode[K, V]): RedBlackNode[K, V] =
        val pivot = parent.left.get
        val newLeftChild = parent.copy(left = pivot.left)
        pivot.copy(left = Some(newLeftChild))

    

