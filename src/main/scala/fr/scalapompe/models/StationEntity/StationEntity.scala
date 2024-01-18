package fr.scalapompe.models.StationEntity
import zio.json._
import fr.scalapompe.models.QueryResponse.FuelStationData

/** Represents a single station. Provided by the API.
  */
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
  implicit val decoder: JsonDecoder[StationEntity] =
    DeriveJsonDecoder.gen[StationEntity]
}
