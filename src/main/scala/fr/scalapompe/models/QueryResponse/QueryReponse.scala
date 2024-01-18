package fr.scalapompe.models.QueryResponse
import java.time.LocalDateTime
import zio._
import zio.json._

/** Represents a result from the search endpoint. Stores a list of stations
  * found by the search query.
  */
case class QueryResponse(
    dataset: DatasetInfo,
    data: List[FuelStationData]
);

object QueryResponse {
  implicit val encoder: JsonEncoder[QueryResponse] =
    DeriveJsonEncoder.gen[QueryResponse]

  def createFromData(data: List[FuelStationData]): QueryResponse = {
    val dataset: DatasetInfo = DatasetInfo(LocalDateTime.now());
    QueryResponse(dataset, data)
  }
}
