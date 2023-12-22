package fr.scalapompe.models.QueryResponse

import java.time.LocalDateTime
import zio.json._

case class DatasetInfo(
    fetch_date: LocalDateTime
);

object DatasetInfo {
  implicit val encoder: JsonEncoder[DatasetInfo] =
    DeriveJsonEncoder.gen[DatasetInfo]
}
