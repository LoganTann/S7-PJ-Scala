ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "fr.efrei"
ThisBuild / organizationName := "Efrei"

val scala3Version = "3.3.1"
val zioVersion = "2.0.20"
val zioJsonVersion = "0.5.0"
val zioHttpVersion = "3.0.0-RC3"
val scalaCsvVersion = "1.3.10"

lazy val root = (project in file("."))
  .settings(
    name := "PJ_LesRats",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-streams" % zioVersion,
      "dev.zio" %% "zio-http" % zioHttpVersion,
      "dev.zio" %% "zio-json" % zioJsonVersion
    )
  )
