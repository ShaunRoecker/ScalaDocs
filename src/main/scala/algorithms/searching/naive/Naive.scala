package algorithms.searching.naive


object NaiveSubstringSearch:

    def naiveSubstringSearch(text: String, pattern: String): Int =
        var index = -1
        for (i <- 0 to text.length - pattern.length if index == -1) 
            var j = 0
            while (j < pattern.length && text(i + j) == pattern(j))
                j += 1
            if (j == pattern.length) index = i
        index
    


    def naiveSubstringSearchFunctional(text: String, pattern: String): Int =
            text.indices.find { i => 
                i + pattern.length <= text.length &&
                pattern.indices.forall(j => text(j + i) == pattern(j))
            }.getOrElse(-1)



            


