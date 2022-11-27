val scala3Version = "3.2.0"

libraryDependencies ++= Seq(
  "org.apache.poi" % "poi" % "5.2.3",
  "org.apache.poi" % "poi-ooxml" % "5.2.3",
  "org.apache.poi" % "poi-ooxml-lite" % "5.2.3"
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "scalawalkabout",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )
