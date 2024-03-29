import fr.scalapompe.services.StationService
import fr.scalapompe.Types.RoutesEnvironment
import zio.ZIOAppDefault
import java.nio.file.Paths
import zio.http._
import zio.stream.ZStream
import zio.Console.printLine
import fr.scalapompe.controllers.SearchController
import fr.scalapompe.controllers.ControllerTrait
import zio.Scope
import fr.scalapompe.controllers.HomepageController

object Main extends ZIOAppDefault {

  /** List of public controllers, to be mounted automatically via [[createAppFromControllers]]
    */
  val controllers: List[ControllerTrait] = List(SearchController, HomepageController)

  override val run = Server
    .serve(createAppFromControllers(controllers))
    .provide(
      Client.default,
      Scope.default,
      Server
        .defaultWithPort(8080)
        .tap(_ => printLine("Server started on port 8080."))
    )

  def createAppFromControllers(
      controllers: List[ControllerTrait]
  ): HttpApp[RoutesEnvironment] = {
    val combinedRoutes = controllers.map(controller => controller.routes).reduce(_ ++ _)
    combinedRoutes.sandbox.toHttpApp
  }
}
