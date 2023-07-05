ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "P2"
  )
resolvers +=  "Artima Maven Repository" at "https://repo.artima.com/releases"
libraryDependencies += "io.github.hughsimpson" %% "scalameter" % "0.22.1"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.15.4"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15"
libraryDependencies += "org.scalatestplus" %% "scalacheck-1-17" % "3.2.15.0"
libraryDependencies +="org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"