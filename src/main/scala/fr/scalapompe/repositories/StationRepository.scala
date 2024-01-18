package fr.scalapompe.repositories

import fr.scalapompe.models.StationEntity.StationsApiRecords
import fr.scalapompe.services.StationUtils
import zio._;
import zio.http._;
import fr.scalapompe.Types.RoutesEnvironment

object StationRepository {

  type IOStationsRecords = ZIO[RoutesEnvironment, Throwable, StationsApiRecords]

  // val OFFLINE_FILE_PATH = "\\src\\main\\scala\\fr\\scalapompe\\data.json";
  val OFFLINE_FILE_PATH = "/src/main/scala/fr/scalapompe/data.json";
  val API_ENDPOINT =
    "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records";

  def get(query: StationQueryDto): IOStationsRecords = {
    if (query.isOffline) {
      getFromLocalFile(query)
    } else {
      getFromAPI(query)
    }
  }

  def getFromLocalFile(query: StationQueryDto): IOStationsRecords = {
    for {
      _ <- Console.printLine(s"Fetching data from file ${OFFLINE_FILE_PATH}...")
      fileContent <- StationUtils.fileToString(OFFLINE_FILE_PATH)
      _ <- Console.printLine("Received results.")
      records <- StationUtils.decodeJson(fileContent)
    } yield records
  }

  def getFromAPI(query: StationQueryDto): IOStationsRecords = {
    val url = createUrl(query)
    for {
      _ <- Console.printLine(s"Fetching data from ${url.encode}...")
      client <- ZIO.service[Client]
      apiResponse <- client.url(url).get("/")
      apiStringResponse <- apiResponse.body.asString
      _ <- Console.printLine("Received results.")
      records <- StationUtils.decodeJson(apiStringResponse)
    } yield records
  }

  private def createUrl(query: StationQueryDto): URL = {
    val urlBase = URL.decode(API_ENDPOINT).toOption.get
    val queryParams = QueryParams()
      .add(
        "select",
        "id,latitude,longitude,adresse,ville,cp,horaires,geom,gazole_maj,gazole_prix"
      )
      .add(
        "where",
        s"distance(geom, geom'POINT(${query.lat} ${query.lon})', ${query.distance}km)"
      )
      .add("refine", "horaires_automate_24_24:\"Oui\"")
      .add("refine", "carburants_disponibles:\"Gazole\"");

    urlBase.addQueryParams(queryParams)
  }
}
