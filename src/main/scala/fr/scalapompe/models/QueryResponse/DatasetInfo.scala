package fr.scalapompe.models.QueryResponse

import java.time.LocalDateTime
import zio.json._

/** Represents metadata about the search result. Only stores the last updated date of the dataset.
  */
case class DatasetInfo(
    fetch_date: LocalDateTime
);

object DatasetInfo {
  implicit val encoder: JsonEncoder[DatasetInfo] = DeriveJsonEncoder.gen[DatasetInfo]
}
