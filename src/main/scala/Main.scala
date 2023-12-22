import zio.Console.{printLine, _}
import zio._
import java.io.IOException
import fr.scalapompe.services.FindStationService
import fr.scalapompe.mocks.StationsApiRecordsMock
import zio.http.{Client, Handler, Method, Response, Routes, Server, URL}
import zio.stream.ZStream

import java.io.{File, IOException}
import java.nio.file.Paths

object Main extends ZIOAppDefault {

  val askUser: ZIO[Any, Throwable, ExitCode] =
    for {
      _ <- printLine(
        "Bonjour, ceci est un test. Entez votre nom mais de toute façon on s'en fout."
      )
      input <- readLine
      response <- FindStationService.ComputeQueryResultFromAllStations(
        StationsApiRecordsMock.value
      )
      _ <- printLine("Voici le résultat: ")
      _ <- printLine(response.toString)
      exitCode <- ZIO.succeed(ExitCode.success)
    } yield exitCode

  // Create HTTP route
  val app = Routes(
    Method.GET / "text" -> Handler.fromStream(
      ZStream.fromPath(Paths.get("data.txt"))
    )
  ).sandbox.toHttpApp

  val run = Server.serve(app).provide(Server.default)
}
