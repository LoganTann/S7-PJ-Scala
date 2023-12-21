import zio.Console.{printLine, _}
import zio._
import zio.http.{Client, Handler, Method, Response, Routes, Server, URL}
import zio.stream.ZStream

import java.io.{File, IOException}
import java.nio.file.Paths

object Main extends ZIOAppDefault {

  // Create HTTP route
  val app = Routes(
    Method.GET / "text" -> Handler.fromStream(ZStream.fromPath(Paths.get("data.txt"))),
  ).sandbox.toHttpApp

  // Define an effect to ask a question and read user input
  //  val askUser: ZIO[Any, IOException, ExitCode] =
  //    for {
  //      _     <- printLine("What is your location ?")
  //      input <- readLine
  //      _ <- printLine(s"You are located at $input!")
  //      exitCode <- ZIO.succeed(ExitCode.success)
  //    } yield exitCode

    // Run it like any simple app
    val run = Server.serve(app).provide(Server.default)
}
