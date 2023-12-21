package fr.scalapompe.models
import java.time.LocalDateTime
import zio._

object QueryResponseFactory {
  def createFrom(data: List[FuelStationData]): Task[QueryResponse] = {
    val dataset = DatasetInfo(LocalDateTime.now())
    ZIO.succeed(QueryResponse(dataset, data))
  }
}
