package fr.scalapompe.services

import fr.scalapompe.models.StationEntity.StationsApiRecords
import fr.scalapompe.models.StationEntity.StationEntity
import fr.scalapompe.models.QueryResponse.FuelStationData

import fr.scalapompe.models.QueryResponse.QueryResponse
import zio._;
import zio.stream.ZStream
import zio.json._
import fr.scalapompe.models.QueryResponse.DatasetInfo
import java.nio.file.Paths

object FindStationService {

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

  def ComputeQueryResultFromAllStations(
      stations: StationsApiRecords
  ): UIO[String] = {
    val data =
      stations.results.map(station => StationEntity.toFuelStationData(station))

    ZIO.succeed(QueryResponse.createFromData(data).toJson)
  }

}
