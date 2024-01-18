import fr.scalapompe.mocks.StationsApiRecordsMock
import fr.scalapompe.services.FindStationService
import zio.ZIOAppDefault
import java.nio.file.Paths
import zio.http._
import zio.stream.ZStream
import zio.Console.printLine
import fr.scalapompe.controllers.SearchController
import fr.scalapompe.controllers.DataController
import fr.scalapompe.controllers.ControllerTrait

object Main extends ZIOAppDefault {
  val controllers: List[ControllerTrait] =
    List(DataController, SearchController)

  override val run = Server
    .serve(createAppFromControllers(controllers))
    .provide(
      Server
        .defaultWithPort(8080)
        .tap(_ => printLine("Server started on port 8080."))
    )

  def createAppFromControllers(
      controllers: List[ControllerTrait]
  ): HttpApp[Any] = {
    val combinedRoutes =
      controllers.map(controller => controller.routes).reduce(_ ++ _)
    combinedRoutes.sandbox.toHttpApp
  }
}
