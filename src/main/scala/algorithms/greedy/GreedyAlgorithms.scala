package algorithms.greedy





object WhichReels:

    val lengths = Array(10, 5, 2, 1)


    def functionalWhichReels(perimeter: Int) =
        case class Pack(p: Int, reels: List[Int])
        lengths.foldLeft(Pack(perimeter, List[Int]())) { case (choice, len) =>
            val multiple = choice.p / len 
            val reelsToBuy = List.fill(multiple)(len)        
            Pack(choice.p - multiple * len, choice.reels ::: reelsToBuy)
        }.reels

    

    def imperativeWhichReels(p: Int): List[Int] =
        val reels = collection.mutable.ListBuffer[Int]()
        var x = p
        while (x > 0)
            var i = 0
            while (lengths(i) > x) i += 1
            reels  += lengths(i)
            x -= lengths(i)
        reels.toList



    
