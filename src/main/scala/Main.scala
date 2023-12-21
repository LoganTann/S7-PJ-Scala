import zio.Console.{printLine, _}
import zio._
import java.io.IOException
import fr.scalapompe.models._
object Main extends ZIOAppDefault {

  // Define an effect to ask a question and read user input
  val askUser: ZIO[Any, Throwable, ExitCode] =
    for {
      _ <- printLine("Bonjour, quelle est votre adresse ?")
      input <- readLine
      response <- QueryResponseFactory.createFrom(
        List(FuelStationData(input, 1.89))
      )
      _ <- printLine(response.toString)
      exitCode <- ZIO.succeed(ExitCode.success)
    } yield exitCode

  def run: ZIO[Any, Throwable, ExitCode] = askUser
}
