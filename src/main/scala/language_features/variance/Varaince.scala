package language.features.variance


object Varaince:

    trait CovariantThing[+T]:
        def incoming[U >: T](item: U): U // in order to put type T (which is covariant)
                                        // in the contravariant position (method parameter),
                                        // we create U that must be a super type of T
        def outgoing[T]: T

        
    trait ContravariantThing[-T]: 
        def outgoing[U <: T]: U         // In order to put type T (which is contravariant)
                                        // in the covariant position (method output)
                                        // we create type U that must be a subtype of T
        def incoming[T](item: T): Unit


