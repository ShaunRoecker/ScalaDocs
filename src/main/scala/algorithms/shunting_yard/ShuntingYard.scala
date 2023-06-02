package algorithms.shuntingyard


    object ShuntingYard:

        def toPostfix(expr: String): List[String] =
            val infix = expr.split(" ").toList
            val pfix = List[String]()
            val ostk = List[String]()
            val (exp, stk) = infix.foldLeft((pfix, ostk)){ (tuple, token) =>
                val (postFix, opStack) = tuple
                token match {
                    case "(" => (postFix, "(" +: opStack)
                    case ")" => 
                        val (beforeBracket, rest) = opStack.span(op => op != "(")
                        (postFix ::: beforeBracket, rest.tail)
                    case "-" =>
                        val (higher, lower) = opStack.span(op => "/*+-".contains(op))
                        (postFix ::: higher, "-" +: lower)
                    case "+" =>
                        val (higher, lower) = opStack.span(op => "/*+-".contains(op))
                        (postFix ::: higher, "+" +: lower)
                    case "*" =>
                        val (higher, lower) = opStack.span(op => "/*+-".contains(op))
                        (postFix ::: higher, "*" +: lower)
                    case "/" =>
                        val (higher, lower) = opStack.span(op => "/*+-".contains(op))
                        (postFix ::: higher, "/" +: lower)
                    case _ => (postFix :+ token, opStack)
                }
            }
            exp ::: stk



        def postfixEval(postFix: List[String]): Double =
            val nstk = postFix.foldLeft(List[Double]()) { case (nStack, token) =>
                (nStack, token) match
                    case (a :: b :: t, "+") => (b + a) +: t
                    case (a :: b :: t, "*") => (b * a) +: t
                    case (a :: b :: t, "/") => (b / a) +: t
                    case (a :: b :: t, "-") => (b - a) +: t
                    case (_, num) => num.toDouble +: nStack 
            }
            nstk.head

        
        def evaluateInfix(infix: String): Double = 
            postfixEval(toPostfix(infix))



        
            
        
