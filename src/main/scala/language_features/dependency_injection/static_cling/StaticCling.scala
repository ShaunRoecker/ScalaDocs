package language.features.dependencyinjection.staticcling


import scala.util.control.NonFatal



case class DB(url: String, user: String, pw: String)

case class User(name: String, pass: String)


// An anit-pattern, everything is hard-coded and dependent
// on singleton-objects
object StaticCling:
    case class Session(id: String):
        def query(q: String) = User("Jon", "12345")


    def withTransaction[A](db: DB)(fn: Session => A): A =
        println(s"Starting session on DB ${db.url}")
        try {
            val a = fn(Session("test"))
            println(s"Committing transaction on DB ${db.url}")
            a
        }
        catch { 
            case NonFatal(ex) =>
                println(s"Rolling back transaction on DB ${db.url}")
                throw(ex)
        }


    object PostgresDBDetails:
        val dbURL: String = "jdbc:postgresql://localhost:5432/data"
        val dbUser: String = "user"
        val dbPassword: String = "12345"


    object PostgresDBConnection:
        import PostgresDBDetails._
        def db = DB(dbURL, dbUser, dbPassword)


    class UserManagement:
        val db = PostgresDBConnection.db
        def findUser(id: Int): User = 
            withTransaction(db) { implicit session =>
                session.query(s"select * from users where id = $id")   
            }


    // (new UserManagement).findUser(123)

    // All these objects and ultimately the UserManagement class are all dependent upon
    // each other, which creates a problem for flexibility and testing.



// Simple and Better:
// We can move from using objects to using classes so we can plug and play new
// DBConnection instances, which gives us flexibility and we can have several
// DBConnections or switch them out easily
object ConstructorParameters:

    case class DB(url: String, user: String, pw: String)

    trait DBConnection:
        val db: DB

    class PostgresDBDetails(val dbUrl: String, val dbUser: String, val dbPass: String)

    class PostgresDBConnection(dbDetails: PostgresDBDetails)
            extends DBConnection:
                import dbDetails._
                val db: DB = DB(dbUrl, dbUser, dbPass)

        
    class UserManagement(val db: DB):
        def findUser(id: Int): User = ???
            // withTransaction(db) { implicit session =>
            //     session.query(s"select * from users where id = $id")//.map(resultsToUser)   
            // }

    
    val dbDetails = new PostgresDBDetails("jdbc:postgresql://localhost:5432/data", "user", "12345")
    val postgresDB: DB = new PostgresDBConnection(dbDetails).db

    val dbUM = new UserManagement(postgresDB)

    // val user = dbUM.findUser(101)

    // This makes our DB injectable...

    // Plain Old Passing In Classes, there are still some problems with this,
    // these include:
            // - When you get to a large system with a lot of dependencies,
                // each of these dependencies has to be passed down
 

// Other Options are Runtime Dependency Injection with libraries like Guice:
    // Downsides to this:
        // - uses reflection - requires extra performance and extra risk 
        // - You don't know if a dependency is missing until runtime
                // (no compile time verification)


// Look at CakePattern.scala for the next zero, library dependency option
// for Dependency Injection


        