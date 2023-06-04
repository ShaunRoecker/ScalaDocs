package language.features.oop.uniformaccess



object UniformAccess:

    class Demo:
        val a: Int = // evaluated at class construction, not when accessed
            println("evaluating a")
            10
        
        def b: Int = // evaluates each time b is accessed
            println("evaluating b")
            20

        lazy val c: Int = // not evaluated at construction, only evaluated once
                            // the first time it's called
            println("evaluating c")
            30

        
    val demo = new Demo 
    // prints..
    // evaluating a 


    val demo2 = new Demo 
    // prints..
    // evaluating a 

    //demo.a // (nothing printed)
    demo.b // evaluating b
    demo.b // evaluating b
    demo.c // evaluating c
    demo.c // (nothing printed)
