package fr.scalapompe.models.QueryResponse
import java.time.LocalDateTime
import zio._
import zio.json._

case class QueryResponse(
    dataset: DatasetInfo,
    data: List[FuelStationData]
);

object QueryResponse {
  implicit val encoder: JsonEncoder[QueryResponse] =
    DeriveJsonEncoder.gen[QueryResponse]
}

def CreateQueryResponseStream(
    data: List[FuelStationData]
): Task[String] = {
  val dataset: DatasetInfo = DatasetInfo(LocalDateTime.now());
  val response = QueryResponse(dataset, data)
  ZIO.succeed(response.toJson);
}
