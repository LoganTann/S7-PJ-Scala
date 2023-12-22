package fr.scalapompe.models.StationEntity

case class StationsApiRecords(
    total_count: Int,
    results: List[StationEntity]
)

object StationsApiRecords {}
