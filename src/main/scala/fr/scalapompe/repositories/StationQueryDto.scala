package fr.scalapompe.repositories

import zio.json._
import fr.scalapompe.models.StationEntity.Geom

/** Parameters for the search
  * @param lat
  *   Latitude, defaults to the one of EFREI.
  * @param lon
  *   Longitude, default to the one of EFREI.
  * @param distance
  *   Max distance radius in km, defaults to 10km.
  * @param isOffline
  *   If true, will use the local file instead of the API. Defaults to false.
  */
final case class StationQueryDto(
    lat: Double = 48.7886889,
    lon: Double = 2.358667,
    distance: Int = 10,
    isOffline: Boolean = false
)

object StationQueryDto {
  implicit val encoder: JsonEncoder[StationQueryDto] = DeriveJsonEncoder.gen[StationQueryDto]

  extension (stationQuery: StationQueryDto) {
    def getGeom: Geom = Geom(stationQuery.lon, stationQuery.lat)
  }
}
