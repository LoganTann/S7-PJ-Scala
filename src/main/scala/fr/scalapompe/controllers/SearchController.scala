package fr.scalapompe.controllers
import zio.json._
import zio.http._
import fr.scalapompe.services.FindStationService
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

object SearchController extends ControllerTrait {
  val routes = Routes(
    Method.GET / "search" -> Handler.fromFunctionZIO(getSearchResults),
    Method.GET / "testRequests" -> Handler.fromFunctionZIO(testHttpRequest),
    Method.GET / "testResults" -> Handler.fromFunctionZIO(testExtractResults)
  )

  /** GET /search : Retourne le résultat de la recherche */
  def getSearchResults(request: Request): UIO[Response] = {
    FindStationService
      .ComputeQueryResultFromAllStations(StationsApiRecordsMock.value)
      .map(response => Response.json(response))
  }

  /** GET /testRequests. */
  def testHttpRequest(request: Request) = {
    val response = for {
      records <- StationRepository.get(StationQueryDto())
      resultsAsJsonString <- FindStationService
        .ComputeQueryResultFromAllStations(records)
      response <- ZIO.succeed(Response.json(resultsAsJsonString))
    } yield (response)

    response.catchAll(handleError)
  }

  def testExtractResults(request: Request) = {
    val pipeline = for {
      records <- StationRepository.get(StationQueryDto(isOffline = true))
      resultsAsJsonString <- FindStationService
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
