package fr.scalapompe.services
import zio._
import zio.test._
import zio.test.Assertion._
import fr.scalapompe.models.StationEntity.Geom
import zio.http.Request
import zio.http.URL
import fr.scalapompe.repositories.StationQueryDto
import fr.scalapompe.mocks.StationsApiRecordsMock
import fr.scalapompe.models.QueryResponse.FuelStationData
import fr.scalapompe.models.StationEntity.StationsApiRecords

object StationsServiceSpec extends ZIOSpecDefault {
  def spec = suite("StationsService")(
    suite("processStations")(
      test("Should output list of DTO sorted by price When giving list of stations") {
        // GIVEN
        val stations: StationsApiRecords = StationsApiRecordsMock.value
        val query = StationQueryDto(distance = 40)

        // WHEN
        val processedRecords: List[FuelStationData] = StationService.processStations(stations, query)

        // THEN
        assertTrue(processedRecords.length == 2)
        assertTrue(processedRecords.head.gazoline_price == 1.749)
        assertTrue(processedRecords.last.gazoline_price == 1.999)
      }
    )
  )
}
