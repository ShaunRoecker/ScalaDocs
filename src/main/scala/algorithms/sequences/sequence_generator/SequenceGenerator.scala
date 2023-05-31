package algorithms.sequences.generator



trait SequenceGenerator:
    def generate(total: Int): List[Int]

    def generateStr(total: Int): String =
        generate(total).mkString(", ")