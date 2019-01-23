lazy val akkaHttpVersion = "10.1.7"
lazy val akkaVersion = "2.5.19"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      version := "1.0.0",
      organization := "com.gmail.namavirs86",
      scalaVersion := "2.12.8"
    )),
    name := "scala-game-card-core",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
      "com.lihaoyi" %% "pprint" % "0.5.3"
    )
  )
