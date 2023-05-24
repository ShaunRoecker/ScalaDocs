package datastructures.immutable.hashtables

import datastructures.immutable.hashtables.HashTable



protected case class HashTableLive[K, V](hashVector: Vector[List[(K, V)]]) extends HashTable[K, V] { self =>

    private val initialSize = hashVector.size

    private def hash[K](key: K) = 
        val h = key.## % initialSize
        if (h < 0) h + initialSize else h


    override def insert(key: K, value: V): HashTable[K, V] = 
        val i = hash(key)
        val list = hashVector(i)
        val newList = (key, value) +: list.filter(_._1 != key)
        new HashTableLive[K, V](hashVector.updated(i, newList))


    override def search(key: K): Option[V] = 
         hashVector(hash(key))
            .find(t => t._1 == key)
             .map(t => t._2)

    override def delete(key: K): HashTable[K, V] = 
        val i = hash(key)
        val list = hashVector(i)
        val newList = list.filter(_._1 != key)
        new HashTableLive[K, V](hashVector.updated(i, newList))


}

object HashTableLive:

    def apply[K, V](initialSize: Int): HashTableLive[K, V] =
        val hashVector = Vector.fill(initialSize)(List())
        new HashTableLive[K, V](hashVector)

    