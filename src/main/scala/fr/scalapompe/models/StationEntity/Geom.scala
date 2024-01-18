package fr.scalapompe.models.StationEntity
import zio.json._

case class Geom(
    lon: Double,
    lat: Double
)

object Geom {
  implicit val decoder: JsonDecoder[Geom] =
    DeriveJsonDecoder.gen[Geom]
}
