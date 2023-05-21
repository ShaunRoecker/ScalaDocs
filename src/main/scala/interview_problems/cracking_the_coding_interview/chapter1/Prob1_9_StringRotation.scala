package ctci.chapter1



object StringRotation:

    def stringRotation(s1: String, s2: String): Boolean =
        (s1.length == s2.length) &&
        (s1 * 2).contains(s2)


// val s1 = "waterbottle"
// val s1_times_2 = "waterbottlewaterbottle"
// val s2 =                "ttlewaterbo"


