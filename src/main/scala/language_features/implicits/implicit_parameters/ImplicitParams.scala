package language.features.implicits.implicit_parameters


object ImplicitParams:

    // def method(item1: String, item2: Int)(implicit p1: Param1, p2: Param2, p3: Param3)
    
    // -- Only the final list of params can be implicit 


    
    case class RetryParams(times: Int)

    import scala.util.control.NonFatal

    def retryCall[A](fn: => A, currentTry: Int)(retryParams: RetryParams): A =
        try fn
        catch 
            case NonFatal(_) if currentTry < retryParams.times =>
                retryCall(fn, currentTry + 1)(retryParams)

    def retry[A](fn: => A)(implicit retryParams: RetryParams): A =
        retryCall(fn, 0)(retryParams)



    var x: Int = 0
    def checkErrorMethod(): Int = 
        x = x + 1
        println(s"Checking $x")
        require(x > 4, "Not big enough")
        x

    


