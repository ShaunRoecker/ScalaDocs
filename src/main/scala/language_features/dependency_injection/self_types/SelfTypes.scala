package language.features.dependencyinjection.selftypes

import collection.JavaConverters.mapAsScalaMapConverter


// check out this article on Baeldung:
    // https://www.baeldung.com/scala/self-type-annotation


object SelfTypes:

    class Test(val name: String, val assertion: Map[String, String] => Boolean) {
        def execute(envs: Map[String, String]): Boolean = 
            println(s"Testing: ${envs}") 
            true
    }

    trait TestEnvironment { 
        val envName: String 
        def readEnvironmentProperties: Map[String, String] 

    }


    // Implementation of TestEnvironment that will be required by the TestExecutor
    trait WindowsTestEnvironment extends TestEnvironment { 
        override val envName: String = "Windows" 
        override def readEnvironmentProperties: Map[String, String] = 
            System.getenv().asScala.toMap        
    }


    class TestExecutor { env: TestEnvironment =>   // this self type annotation expresses that in order to run, the TestExecutor
        def execute(tests: List[Test]): Boolean =    // needs an instance of TestEnvironment. 
            println(s"Executing test with $envName environment")
            tests.forall(_.execute(readEnvironmentProperties))
           
    }



    // If we dont "mix in" a TestEnvironment when extending TestExecutor, we get a compile error

    // class JUnit5TestExecutor extends TestExecutor {}

    // --> illegal inheritance: self type language.features.dependencyinjection.selftypes.SelfTypes.JUnit5TestExecutor 
    // of class JUnit5TestExecutor does not conform to self type 
    // language.features.dependencyinjection.selftypes.SelfTypes.TestEnvironment

    // It compiles once we add an instance of TestEnvironment
    class JUnit5TestExecutor extends TestExecutor with WindowsTestEnvironment {}


    val windowsGeneralExecutor: TestExecutor = new TestExecutor with WindowsTestEnvironment

    class TestWithLogging(name: String, assertion: Map[String, String] => Boolean) extends Test(name, assertion) { 
        inner: Test => 
            override def execute(env: Map[String, String]): Boolean = { 
                println("Before the test") 
                val result = inner.execute(env)
                result
            } 
    }


    