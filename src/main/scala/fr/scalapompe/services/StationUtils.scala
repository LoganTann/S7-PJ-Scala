package fr.scalapompe.services
import zio._;
import fr.scalapompe.models.StationEntity.StationsApiRecords
import java.nio.file.Paths
import zio.stream._
import zio.json._

object StationUtils {

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

}
