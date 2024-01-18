package fr.scalapompe.models.StationEntity

import zio.json._

case class StationsApiRecords(
    total_count: Int,
    results: List[StationEntity]
)

object StationsApiRecords {
  implicit val decoder: JsonDecoder[StationsApiRecords] =
    DeriveJsonDecoder.gen[StationsApiRecords]
}
