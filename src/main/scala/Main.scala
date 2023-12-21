import fr.scalapompe.models.QueryResponse.{
  FuelStationData,
  CreateQueryResponseStream
}
import zio.Console.{printLine, _}
import zio._
import java.io.IOException
object Main extends ZIOAppDefault {

  // Define an effect to ask a question and read user input
  val askUser: ZIO[Any, Throwable, ExitCode] =
    for {
      _ <- printLine("Bonjour, quelle est votre adresse ?")
      input <- readLine
      response <- CreateQueryResponseStream(
        List(FuelStationData(input, 1.89))
      )
      _ <- printLine(response.toString)
      exitCode <- ZIO.succeed(ExitCode.success)
    } yield exitCode

  def run: ZIO[Any, Throwable, ExitCode] = askUser
}
