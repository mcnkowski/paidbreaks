name := "paidbreak"
version := "0.1"

resolvers += "Maven Central Server" at "https://repo1.maven.org/maven2"
resolvers += "Artima Maven Repository" at "https://repo.artima.com/releases"

scalaVersion := "2.13.4"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"

Test / logBuffered := false
Test / parallelExecution := false

scalacOptions ++= Seq("-feature")