import java.security.Permission
object Enums extends App {
    def main(): Unit = {
        println("ENUMS")
        alpha()
        bravo()
    }
    main()
    // enums work just like classes, only there are a finite set of constants
    def alpha(): Unit = {
        enum Permissions:
            case READ, WRITE, EXECUTE, NONE
            //can add fields/methods
            def openDocument(): Unit =
                if (this == READ) then println("opening document...")
                else println("reading not allowed.")
    
        val somePermissions: Permissions =  Permissions.READ
        somePermissions.openDocument() 
        // Standard API
        val somePermissionsOrdinal = somePermissions.ordinal
        println(somePermissionsOrdinal) // 0, because READ is in the "0" position, 
                                            // if the enums constants were indexed

        // Return array of all possible permissions
        val allPermissions = Permissions.values
        println(allPermissions)

        // Return the Permissions of the value in a given index location
        println(Permissions.fromOrdinal(2)) // Permissions.EXECUTE

        // Get a Permissions, when given a string value of the Permissions value name
        println(Permissions.valueOf("READ")) // Permissions.READ


    }
    def bravo(): Unit = {
        enum PermissionsWithBits(bits: Int):
            case READ extends PermissionsWithBits(4) //100
            case WRITE extends PermissionsWithBits(2) //010
            case EXECUTE extends PermissionsWithBits(1) //001
            case NONE extends PermissionsWithBits(0) //000

        // Can have companion objects with enums
        object PermissionsWithBits:
            def fromBits(bits: Int): PermissionsWithBits = ???


    }
    
  
}
