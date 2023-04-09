ThisBuild / scalaVersion := "3.1.3"
ThisBuild / version          := "0.0.1"
ThisBuild / organization     := "dev.scr"


lazy val root = project
  .in(file("."))
  .settings(name := "Scala Documentation")
  .aggregate(homegrown)

lazy val homegrown = project
  .in(file("homegrown"))
  .settings(name := "Homegrown")

  

  







