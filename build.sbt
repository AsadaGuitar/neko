val scala3Version = "3.2.0"

lazy val kernel =
  project
    .in(file("kernel"))
    .settings(
      name := "neko-kernel",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := scala3Version,
      libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
    )

lazy val root = project
  .in(file("."))
  .settings(
    name := "neko",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    skip := true
  ).aggregate(kernel)