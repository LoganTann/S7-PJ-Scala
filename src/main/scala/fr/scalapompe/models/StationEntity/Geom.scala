package fr.scalapompe.models.StationEntity
import zio.json._

/** Represents a geolocation point. Provided by the API.
  */
case class Geom(
    lon: Double,
    lat: Double
)

object Geom {
  implicit val decoder: JsonDecoder[Geom] =
    DeriveJsonDecoder.gen[Geom]
}
