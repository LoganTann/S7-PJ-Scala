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
    // val apiEndpoint = Path(
    //   "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records"
    // )
    // val url = URL(
    //   apiEndpoint,
    //   queryParams = QueryParams(
    //     "select" -> "id,latitude,longitude,adresse,ville,cp,horaires,geom,gazole_maj,gazole_prix",
    //     "limit" -> "20"
    //   )
    // )
    // val request = Request(method = Method.GET, url = url)

    // val response = for {
    //   clientService <- ZIO.service[Client]
    //   res <- clientService.url(url)
    //   jsonResponse <- FindStationService.decodeJson(queryResult.body.toString())
    //   _ <- Console.printLine(jsonResponse.toString())
    //   response <- ZIO.succeed(Response.text("TODO"))
    // } yield (response)

    val url = URL
      .decode(
        "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records?select=id%2C%20latitude%2C%20longitude%2C%20adresse%2C%20ville%2C%20cp%2C%20horaires%2C%20geom%2C%20gazole_maj%2C%20gazole_prix&limit=20&refine=region%3A%22%C3%8Ele-de-France%22&refine=carburants_disponibles%3A%22Gazole%22"
      )
      .toOption
      .get // unsafe

    val response = for {
      client <- ZIO.service[Client]
      res <- client.url(url).get("/")
      response <- ZIO.succeed(Response.text(res.body.toString()))
    } yield response

    // execute the request
    // append "toto" to the response body string
    // return a new response of the modified body
    response.catchAll(handleError)
  }

  def testExtractResults(request: Request): Task[Response] = {
    val pipeline = for {
      fileContent <- FindStationService.fileToString(
        "\\src\\main\\scala\\data.txt"
      )
      records <- FindStationService.decodeJson(fileContent)
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
