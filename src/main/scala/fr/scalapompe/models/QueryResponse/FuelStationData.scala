package fr.scalapompe.models.QueryResponse

import zio.json._

case class FuelStationData(
    address: String,
    gazoline_price: Double
);

object FuelStationData {
  implicit val encoder: JsonEncoder[FuelStationData] =
    DeriveJsonEncoder.gen[FuelStationData]
}
