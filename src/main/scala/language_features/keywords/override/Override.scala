package language.features.oop.override_


object OverrideKeyword:

    // Rules for the override keyword:

    // 1. If a val or def defines a field or method with the same parameter types OVER 
    //   another of the same name, it must be marked with 'override'

    // 2. If a val or def defines a field or method that does not override a superclass 
    //   field or method with the same parameter types, it must NOT be marked 'override'
    
    // 3. If a val or def defines a field or method with the same parameter types 
    //    implementing a previously abstract field or method, it may or may not be marked
    //   'override' 



    abstract class Upper:
        def method1: String
        val value1: String = "value1"
        def method2(x: Int, y: Int): Int


    class Lower extends Upper:
        override def method1: String = "method1Implemented"  // override optional
        override val value1: String = "differentValue" // must be override and val
        override def method2(x: Int, y: Int): Int = x + y  // override optional
        def method2(x: Double, y: Double): Double = x + y  // can't override

    
