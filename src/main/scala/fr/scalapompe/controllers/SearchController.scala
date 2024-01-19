package fr.scalapompe.controllers
import zio.json._
import zio.http._
import fr.scalapompe.services.StationService
import zio._
import java.net.http.HttpClient
import zio.stream.ZStream
import java.nio.file.Paths
import fr.scalapompe.models.StationEntity.StationsApiRecords
import java.io.IOException
import scala.collection.mutable.HashMap
import fr.scalapompe.repositories.StationRepository
import fr.scalapompe.repositories.StationQueryDto
import fr.scalapompe.services.HelpersService

object SearchController extends ControllerTrait {
  val routes = Routes(
    Method.GET / "search" -> Handler.fromFunctionZIO(getSearchResults)
  )

  /** GET /search?lat=<Double>&lon=<Double>&distance=<Int>[&offline=<true>] */
  def getSearchResults(request: Request) = {
    val userQuery = HelpersService.extractQuery(request)
    val rawStations = StationRepository.searchStations(userQuery)
    rawStations
      .map(records => StationService.processStations(records, userQuery))
      .map(result => StationService.generateHttpResponse(result))
      .catchAll(handleError)
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
