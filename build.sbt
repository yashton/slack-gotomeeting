lazy val deps = Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.1",
  "com.typesafe.akka" %% "akka-http" % "10.0.6",
  "de.heikoseeberger" %% "akka-http-argonaut" % "1.15.0",
  "io.argonaut" %% "argonaut" % "6.1",
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.1",
  "com.typesafe" % "config" % "1.3.1",
  "org.slf4j" % "slf4j-api" % "1.7.21",
  "ch.qos.logback" % "logback-classic" % "1.2.2"
)

lazy val root =
  (project in file("."))
    .settings(
      organization := "science.snelgrove",
      name := "slack-gotomeeting",
      version := "0.1.0",
      scalaVersion := "2.12.1",
      resolvers += Resolver.bintrayRepo("hseeberger", "maven"),
      libraryDependencies ++= deps
  )
