package fr.scalapompe.controllers

import zio.http._
import fr.scalapompe.services.FindStationService
import fr.scalapompe.mocks.StationsApiRecordsMock
import java.nio.file.Paths
import zio.stream.ZStream
import zio.Task

object DataController {
  val routes = Routes(
    Method.GET / "data" -> Handler.fromFunctionZIO(getData),
    Method.GET / "testParams" -> Handler.fromFunction(testQueryParams)
  )

  val relativePath = Paths.get("").toAbsolutePath()

  /** GET /data : Retourne le contenu du fichier data.txt en JSON
    */
  def getData(request: Request): Task[Response] = {
    val file =
      Paths.get(relativePath.toString().concat("\\src\\main\\scala\\data.txt"))

    ZStream
      .fromPath(file)
      .runCollect
      .map(bytes => new String(bytes.toArray))
      .map(response => Response.json(response))
  }

  /** GET /testParams?lat=XXX&lon=XXX : Retourne les paramètres de la requête
    */
  def testQueryParams(request: Request) = {
    val queryParams = request.url.queryParams
    val lat = queryParams.get("lat")
    val lon = queryParams.get("lon")

    if (lat.isDefined && lon.isDefined)
      Response.text(
        s"Vous avez spécifié les paramètres suivants : lat: ${lat.get}, lon: ${lon.get}"
      )
    else
      Response.text("Spécifiez les paramètres lat et lon");
  }
}
