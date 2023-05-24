package datastructures.immutable.binarytree


trait BinarySearchTree[K, V]:

    def search(key: K): Option[V]

    def insert(key: K, value: V): BinarySearchTree[K, V]

    

