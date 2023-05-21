package algorithms.searching.kmp



object KMPSubstringSearch:
    extension(text: String)
        def kMPSubstringSearch(pattern: String): Int =
            def createPrefixTable(pattern: String): Vector[Int] = 
                pattern.drop(1).foldLeft(0, Vector(0)) {
                    case ((initialState, prefixT), char) =>
                        val lowerInit = 
                            LazyList.iterate(initialState){ init => prefixT(init - 1)}
                                .find(init => init == 0 || pattern(init) == char)
                                .get
                        val newInit = 
                            if (pattern(lowerInit) == char) lowerInit + 1 else lowerInit
                        (newInit, prefixT :+ newInit)
                }._2
            
            val prefixTable = createPrefixTable(pattern)   // prefix table was pre determined here
            text.indices.foldLeft(-1, 0) {   // foldLeft accepts PartialFunctions
                case ((foundIndex, m), i) if foundIndex > 0 => (foundIndex, 0) // This perpetuates the result if greater than -1 (default)
                case ((foundIndex, m), i) => // This part is the while loop and below (while (m > 0 and pattern(m)...))
                    val streamOfPrevMs = LazyList.iterate(m)(m => prefixTable(m - 1))  // This part is the 'm = prefixTable(m - 1)' 
                                                                               // but keeps a lazy list of all the previous m values
                    val lowerM = streamOfPrevMs.find(m => m == 0 || pattern(m) == text(i)).get
                    val newM = if (pattern(lowerM) == text(i)) lowerM + 1 else lowerM
                    if (newM == pattern.length) (i - newM + 1, 0) else (-1, newM)
            }._1



// sudo:
// 
// def substringSearching(text, pattern): Int =
//   def prefixTable(pattern: String): Vector[Int] =
//      create prefix table from pattern here
//   prefixT = prefixTable(pattern)
//   m = 0                                              <-- // m is the number of chars currently matching
//   for (i <- 0 until length(text))                    <-- // loop through every char in the text
//      while (m > 0 and pattern[m] != text[i])         <-- // This part deals with when we have a partial match
//          m = prefixTable(m - 1)                      <-- // This uses the prefixTable to resize m to be a value where m is as
//                                                               big as possible while still matching a prefix of the pattern
//      if (pattern[m] == text[i]) m++                  <-- // increment m if matching with text (increasing our value for the number of chars matching)
//      if (m == length(pattern))  return i - m + 1     <-- // this means the entire pattern has been matched, 
//                                                          so we return the current i(where in the text we are) minus 
//                                                          the length of the pattern and plus 1 to get the index for t
//                                                          he first matching character
//   return -1                                          <-- // returns -1 if no match is found
//



// sudo 
//
// def createPrefixTable(pattern: String): Vector[Int] =
//     
// 
// 
// 
// 
// 
// 
// 
// 