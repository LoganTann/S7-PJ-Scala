package fr.scalapompe.models.StationEntity
import zio.json._
import fr.scalapompe.models.QueryResponse.FuelStationData

case class StationEntity(
    id: Int,
    latitude: String,
    longitude: Double,
    adresse: String,
    ville: String,
    cp: String,
    horaires: String,
    geom: Geom,
    gazole_prix: String,
    gazole_maj: String
)

object StationEntity {
  def toFuelStationData(station: StationEntity): FuelStationData = {
    FuelStationData(
      address = station.adresse,
      gazoline_price = station.gazole_prix.toDouble
    )
  }

  implicit val decoder: JsonDecoder[StationEntity] =
    DeriveJsonDecoder.gen[StationEntity]
}
