package algorithms.hashtables.immutable



trait HashTable[K, V] { self =>
    
    def insert(key: K, value: V): HashTable[K, V]
    
    def search(key: K): Option[V]

    def delete(key: K): HashTable[K, V]

}


