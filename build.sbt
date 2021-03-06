// akka source: https://stackoverflow.com/questions/46313724/noclassdeffounderror-gentraversableonceclass-with-akka

name := "Connect four game"
organization := "de.htwg.se"
version := "1.0"
scalaVersion := "2.11.8"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

//resolvers += Resolver.jcenterRepo

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= {
  // specs2 3.7 is the last version to use scalacheck 1.12.5
  val specs2Version = "3.8.6"
  Seq(
    "org.scalaz" %% "scalaz-core" % "7.2.7",
    // scalaz-scalacheck-binding is built against 1.12.5 so best
    // to use the same version here.
    "org.scalacheck" %% "scalacheck" % "1.13.4",
    "org.specs2" %% "specs2-core" % specs2Version,
    "org.specs2" %% "specs2-scalacheck" % specs2Version,
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  )
}
libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11.0-M7"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.5.4"

scalacOptions in Test ++= Seq("-Yrangepos")

fork in (IntegrationTest, run) := true