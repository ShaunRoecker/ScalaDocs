package algorithms.hashtables.immutable

abstract class HashTableLive[K, V](initialSize: Int) extends HashTable[K, V] { self =>

     def hash[K](key: K) = 
        val h = key.## % initialSize
        if (h < 0) h + initialSize else h

    override def insert(key: K, value: V): HashTable[K, V] = ???

    override def search(key: K): Option[V] = ???

    override def delete(key: K): HashTable[K, V] = ???


}
