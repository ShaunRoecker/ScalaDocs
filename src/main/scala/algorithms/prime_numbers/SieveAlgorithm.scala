package algorithms.primenumbers.sieve




object FunctionalSieveImplementation:
    val s0: LazyList[Int] = 2 #:: LazyList.empty

    val s1: LazyList[Int] = 2 #:: LazyList.from(3)

    def allPrimes: LazyList[Int] = 
        2 #:: LazyList.from(3)
                .filter{ c => 
                    val primesToSqrt = allPrimes.takeWhile(p => p <= math.sqrt(c))
                    !primesToSqrt.exists(p => c % p == 0)
                } 


    println(allPrimes.take(15).toList)




