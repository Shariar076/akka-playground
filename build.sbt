name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.13.3"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.1.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.play" %% "play-json" % "2.9.1",
  "org.json" % "json" % "20180813",
  "io.spray" %%  "spray-json" % "1.3.5",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",
  // "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  // "org.scalatest" %% "scalatest" % "3.1.0" % Test
)
