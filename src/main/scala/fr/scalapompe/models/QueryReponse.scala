package fr.scalapompe.models
import java.time.LocalDateTime

case class DatasetInfo(
    fetch_date: LocalDateTime
);

case class FuelStationData(
    address: String,
    gazoline_price: Double
);

case class QueryResponse(
    dataset: DatasetInfo,
    data: List[FuelStationData]
);
