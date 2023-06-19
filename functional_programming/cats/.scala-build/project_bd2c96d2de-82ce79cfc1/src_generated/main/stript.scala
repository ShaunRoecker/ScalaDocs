



object stript {
def args = stript_sc.args$
/*<script>*/

object Script extends App {
    println("hello, world!")
}/*</script>*/ /*<generated>*/
/*</generated>*/
}

object stript_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }
  def main(args: Array[String]): Unit = {
    args$set(args)
    stript.hashCode() // hasCode to clear scalac warning about pure expression in statement position
  }
}

