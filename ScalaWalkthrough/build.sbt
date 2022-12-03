val scala3Version = "3.2.0"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "fansi" % "0.4.0"
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "scalawalkabout",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )
