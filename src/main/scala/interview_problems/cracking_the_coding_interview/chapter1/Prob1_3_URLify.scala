package ctci.chapter1

// Write a method to replace all spaces in a string with '%20'

object URLify:

    def urlify1(s: String): String =
        def rightTrim(s: String) = 
            s.replaceAll("\\s+$", "")
        rightTrim(s).replaceAll("[ ]+", "%20")


    def urlify2(s: String, len: Int): String =
        def rightTrim(s: String): String =
            s.replaceAll("\\s+$", "")
        rightTrim(s).replaceAll("[ ]+", "%20").take(len)

    


def ltrim(s: String) = s.replaceAll("^\\s+", "")
def rtrim(s: String) = s.replaceAll("\\s+$", "")

    