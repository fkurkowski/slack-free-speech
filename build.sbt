
lazy val commonSettings = Seq(
  organization := "io.github.fkurkowski",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.6"
)

lazy val SlackFreeSpeech = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "slack-free-speech",
    libraryDependencies ++= Seq(
      // Logging
      "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
      "ch.qos.logback" % "logback-classic" % "1.1.2" % "runtime",

      // HTTP
      "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",

      // Testing
      "org.specs2" %% "specs2" % "2.4" % "test"
    )
  )
