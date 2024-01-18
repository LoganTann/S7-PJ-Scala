package fr.scalapompe.models.StationEntity

import zio.json._

/** Represents a list of stations. Provided by the API.
  *
  * @param total_count
  * @param results
  */
case class StationsApiRecords(
    total_count: Int,
    results: List[StationEntity]
)

object StationsApiRecords {
  implicit val decoder: JsonDecoder[StationsApiRecords] = DeriveJsonDecoder.gen[StationsApiRecords]
}
