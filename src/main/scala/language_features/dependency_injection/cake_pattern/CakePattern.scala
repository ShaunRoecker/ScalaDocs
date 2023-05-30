package language.features.dependencyinjection.cake_pattern


class Database
class MySQLDatabase

object CakePattern:

    abstract class DBAccess:
        def database: Database   // abstract definition

        def findAgeOfPerson(name: String): Int = ???
            // withTransaction(database.currentSession) { tx =>
            //     tx.find(name = "fred").select("age")    
            // }



    trait MySQLDB{ this: DBAccess =>  // self type 
        val database = new MySQLDatabase

    }

    // val access = new DBAccess with MySQLDB  // inject
    // access.findAgeOfPerson("fred")
