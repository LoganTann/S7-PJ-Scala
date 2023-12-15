ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "PJ_la_deshe",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.19"
    )
  )
