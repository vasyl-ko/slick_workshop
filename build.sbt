val globalSettings = Seq[SettingsDefinition](
  version := "0.1",
  scalaVersion := "2.12.4"
)



val model = Project("model", file("model"))
  .settings(globalSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % "0.8.0"),
    addCompilerPlugin(
      "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full
    )
  )

val repositories = Project("repositories", file("repositories"))
  .dependsOn(model)
  .settings(globalSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.2.1",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
      "org.postgresql" % "postgresql" % "42.1.4",
    )
  )


val webservice = Project("application", file("application"))
  .dependsOn(repositories)
  .settings(globalSettings: _*)
  .settings(
    resolvers += Resolver.bintrayRepo("hseeberger", "maven"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.0.11",
      "com.typesafe.akka" %% "akka-http-testkit" % "10.0.11" % Test,
      "de.heikoseeberger" %% "akka-http-circe" % "1.18.0")
  )


val root = Project("slick_workshop", file("."))
  .aggregate(webservice)
