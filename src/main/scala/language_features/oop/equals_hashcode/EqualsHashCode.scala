package language.features.oop.equalshashcode

import topics.extensionmethods.implicitclasses.ImplicitClasses.extractedDigits

// It is not recommended to implement these methods yourself, but this 
// is how it is done.  IntelliJ has a feature that creates them for you 
// as another option.

// Just use case classes. 

object EqualsHashCodeImplementation:

    class Fruit(val name: String):
        def canEqual(other: Any): Boolean = 
            other.isInstanceOf[Fruit]

        override def equals(other: Any): Boolean = 
            other match
                case that: Fruit =>
                    (that.canEqual(this)) &&
                        name == that.name
                case _ => false


        override def hashCode(): Int = 
            name.hashCode



    class Apple(val brand: String, val color: String) extends Fruit("apple"):
        override def canEqual(other: Any): Boolean = 
            other.isInstanceOf[Apple]

        override def equals(other: Any): Boolean = 
            other match
                case that: Apple =>
                    super.equals(that) &&
                      (that.canEqual(this)) &&
                      brand == that.brand &&
                      color == that.color
                case _ => false


        override def hashCode(): Int = 
            41 * (
                41 * (
                    41 + super.hashCode
                ) + brand.hashCode
            ) + color.hashCode
    



