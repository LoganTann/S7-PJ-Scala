package fr.scalapompe.controllers

import fr.scalapompe.services.HelpersService
import zio._
import zio.http._
import java.io.IOException

object HomepageController extends ControllerTrait {
  val routes = Routes(
    Method.GET / "" -> Handler.fromFunctionZIO(showLandingPage)
  )

  /** GET / */
  def showLandingPage(request: Request) = {
    val response = for {
      readme <- HelpersService.fileToString("./readme.md")
      response <- ZIO.succeed(
        Response
          .text(readme)
          .status(Status.Ok)
          .setHeaders(Headers.apply("Content-Type", "text/plain; charset=utf-8"))
      )
    } yield response

    response.catchAll(handleError)
  }

  def handleError(e: Throwable): ZIO[Any, IOException, Response] = {
    Console
      .printLine(e.getMessage())
      .as(
        Response
          .text(s"Fatal Error : \n\n => ${e.getMessage()}")
          .status(Status.InternalServerError)
      )
  }

}
