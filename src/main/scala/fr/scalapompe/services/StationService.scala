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
import fr.scalapompe.repositories.StationQueryDto
import java.time.LocalDateTime
import zio.http.Response
import fr.scalapompe.models.StationEntity.Geom

object StationService {

  /** Sorts stations by price and adds in a new DTO the distance between station and user
    */
  def processStations(stations: StationsApiRecords, query: StationQueryDto): List[FuelStationData] = {
    val currentPosition = query.getGeom
    stations.results
      .sortBy(_.gazole_prix)
      .map(record => (record, record.geom.distance(currentPosition)))
      // Filter : Although the API already performs this operation, the offline mode does not.
      .filter((_, distance) => distance <= query.distance)
      .map(toStationData)
  }

  private def toStationData(station: StationEntity, distance: Double): FuelStationData = {
    val address = s"${station.adresse}\n${station.cp} ${station.ville}"
    val gazoline_price = station.gazole_prix.toDouble
    FuelStationData(address, gazoline_price, distance = s"${distance.toInt} km")
  }

  /** Generates a JSON response from a list of stations
    */
  def generateHttpResponse(stations: List[FuelStationData]) = {
    val metadata: DatasetInfo = DatasetInfo(LocalDateTime.now())
    val responseData = QueryResponse(metadata, stations).toJson

    Response.json(responseData)
  }

}
