package datastructures.mutable.hashtables



trait HashTable[K, V] { self =>
    
    def insert(key: K, value: V): Unit
    
    def search(key: K): Option[V]

    def delete(key: K): Option[V]
    
}




