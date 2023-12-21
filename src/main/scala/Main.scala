import zio.Console.{printLine, _}
import zio._
import java.io.IOException
import fr.scalapompe.services.FindStationService
import fr.scalapompe.mocks.StationsApiRecordsMock

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

  def run: ZIO[Any, Throwable, ExitCode] = askUser
}
