import fr.scalapompe.mocks.StationsApiRecordsMock
import fr.scalapompe.services.FindStationService
import zio.ZIOAppDefault
import java.nio.file.Paths
import zio.http._
import zio.stream.ZStream
import zio.Console.printLine
import fr.scalapompe.controllers.SearchController
import fr.scalapompe.controllers.DataController

object Main extends ZIOAppDefault {
  val routes: Routes[Any, Throwable] = (
    DataController.routes
      ++ SearchController.routes
  )

  override val run = Server
    .serve(routes.sandbox.toHttpApp)
    .provide(
      Server
        .defaultWithPort(8080)
        .tap(_ => printLine("Server started on port 8080."))
    )
}
