name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.13.3"

lazy val akkaVersion = "2.6.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",
  // "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  // "org.scalatest" %% "scalatest" % "3.1.0" % Test
)