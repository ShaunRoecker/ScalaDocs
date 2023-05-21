package ctci.chapter1


// Determine if a given string is a permutation of a palindrome.
// A palindrome is a word or phrase that is the same backwards and forwards
// ex: racecar 

object IsPalindromePermutation:

    def isPalindromePerm(s: String): Boolean =
        extension(string: String)
            def countOdd: Int =
                string
                    .replaceAll("[ ]+", "")
                    .toLowerCase
                    .groupBy(identity)
                    .transform((_, v) => v.length)
                    .values
                    .count(_ % 2 != 0)

        s.countOdd <= 1



