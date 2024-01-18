package fr.scalapompe.models.QueryResponse

import zio.json._
import fr.scalapompe.models.StationEntity.StationEntity

/** Represents a single station from a search, with only the required data.
  */
case class FuelStationData(
    address: String,
    gazoline_price: Double,
    distance: String
);

object FuelStationData {
  implicit val encoder: JsonEncoder[FuelStationData] =
    DeriveJsonEncoder.gen[FuelStationData]

  implicit val decoder: JsonDecoder[FuelStationData] =
    DeriveJsonDecoder.gen[FuelStationData]
}
