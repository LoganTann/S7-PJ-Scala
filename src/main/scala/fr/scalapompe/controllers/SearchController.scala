package fr.scalapompe.controllers
import zio.json._
import zio.http._
import fr.scalapompe.services.StationService
import fr.scalapompe.mocks.StationsApiRecordsMock
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

  /** GET /search : Retourne le résultat de la recherche */
  def getSearchResults(request: Request) = {
    val userQuery = HelpersService.extractQuery(request)
    val pipeline = for {
      records <- StationRepository.searchStations(userQuery)
      resultsAsJsonString <- StationService
        .ComputeQueryResultFromAllStations(records)
      response <- ZIO.succeed(Response.json(resultsAsJsonString))
    } yield response

    pipeline.catchAll(handleError)
  }

  /** GET /testRequests. */
  def testHttpRequest(request: Request) = {
    val response = for {
      records <- StationRepository.searchStations(StationQueryDto())
      resultsAsJsonString <- StationService
        .ComputeQueryResultFromAllStations(records)
      response <- ZIO.succeed(Response.json(resultsAsJsonString))
    } yield (response)

    response.catchAll(handleError)
  }

  def testExtractResults(request: Request) = {
    val pipeline = for {
      records <- StationRepository.searchStations(
        StationQueryDto(isOffline = true)
      )
      resultsAsJsonString <- StationService
        .ComputeQueryResultFromAllStations(records)
      response <- ZIO.succeed(Response.json(resultsAsJsonString))
    } yield response

    pipeline.catchAll(handleError)
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
