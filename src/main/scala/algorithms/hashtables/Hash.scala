package algorithms.hashtables



object Hash:

    def runHash(): Unit =

        val str: String = "Hello"

        println(str.hashCode) // 69609650

        println(str.##) // 69609650

        val str1 = "Hello this is a longer string"

        println(str1.hashCode) // 1388119751

        println(str1.##) // 1388119751


        val m = 13

        def hash[K](key: K) = 
            val h = key.## % m
            if (h < 0) h + m else h

        println(hash("Hello")) // 6

        println(hash(1000)) // 12


    


