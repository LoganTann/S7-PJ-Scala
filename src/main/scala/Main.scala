import zio.Console.{printLine, _}
import zio._

import java.io.IOException

object Main extends ZIOAppDefault {

  // Define an effect to ask a question and read user input
  val askUser: ZIO[Any, IOException, ExitCode] =
    for {
      _     <- printLine("What is your location ?")
      input <- readLine
      _ <- printLine(s"You are located at $input!")
      exitCode <- ZIO.succeed(ExitCode.success)
    } yield exitCode

  def run: ZIO[Any, IOException, ExitCode] = askUser
}
