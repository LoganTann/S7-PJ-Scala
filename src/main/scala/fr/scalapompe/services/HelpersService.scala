package fr.scalapompe.services
import zio._;
import fr.scalapompe.models.StationEntity.StationsApiRecords
import java.nio.file.Paths
import zio.stream._
import zio.json._
import fr.scalapompe.repositories.StationQueryDto
import zio.http.Request

object HelpersService {

  def fileToString(relativePath: String): Task[String] = {
    val workingDir = Paths.get("").toAbsolutePath()
    val fullPath = Paths.get(workingDir.toString().concat(relativePath))
    ZStream
      .fromPath(fullPath)
      .runCollect
      .map(bytes => new String(bytes.toArray))
  }

  def decodeJson(jsonContent: String): Task[StationsApiRecords] = ZIO
    .fromEither(jsonContent.fromJson[StationsApiRecords])
    .mapError(error => new Exception(error))

  def extractQuery(request: Request): StationQueryDto = {
    val queryParams = request.url.queryParams
    val distance = queryParams.get("distance").flatMap(_.toIntOption).getOrElse(10)
    val isOffline = queryParams.get("offline").isDefined
    val lat = queryParams.get("lat").flatMap(_.toDoubleOption)
    val lon = queryParams.get("lon").flatMap(_.toDoubleOption)

    (lat, lon) match {
      case (Some(lat), Some(lon)) =>
        StationQueryDto(lat, lon, distance, isOffline)
      case _ =>
        StationQueryDto(distance = distance, isOffline = isOffline)
    }
  }
}
