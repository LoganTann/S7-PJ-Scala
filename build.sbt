ThisBuild / scalaVersion := scala3Version
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "fr.scalapompe"
ThisBuild / organizationName := "Scalapompe"

val scala3Version = "3.3.1"
val zioVersion = "2.0.20"
val zioJsonVersion = "0.5.0"
val zioHttpVersion = "3.0.0-RC3"
val scalaCsvVersion = "1.3.10"

lazy val root = (project in file("."))
  .settings(
    name := "scalapompe",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-streams" % zioVersion,
      "dev.zio" %% "zio-http" % zioHttpVersion,
      "dev.zio" %% "zio-json" % zioJsonVersion
    )
  )
