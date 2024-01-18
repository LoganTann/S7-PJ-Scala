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

  def ComputeQueryResultFromAllStations(
      stations: StationsApiRecords
  ): UIO[String] = {
    val data =
      stations.results.map(station => StationEntity.toFuelStationData(station))

    ZIO.succeed(QueryResponse.createFromData(data).toJson)
  }

}
