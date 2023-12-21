package fr.scalapompe.services

import fr.scalapompe.models.StationEntity.StationsApiRecords
import fr.scalapompe.models.StationEntity.StationEntity
import fr.scalapompe.models.QueryResponse.FuelStationData

import fr.scalapompe.models.QueryResponse.CreateQueryResponseStream
import fr.scalapompe.models.QueryResponse.QueryResponse
import zio._;

object FindStationService {

  def ComputeQueryResultFromAllStations(
      records: StationsApiRecords
  ): Task[String] = {
    val stations: List[FuelStationData] = records.results
      .map(entity => StationEntity.toFuelStationData(entity))
    CreateQueryResponseStream(stations)
  }

}
