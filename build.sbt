ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "fr.efrei"
ThisBuild / organizationName := "Efrei"

lazy val root = (project in file("."))
  .settings(
    name := "PJ_LesRats",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.19"
    )
  )
