package fr.scalapompe.mocks

import fr.scalapompe.models.StationEntity.StationsApiRecords
import fr.scalapompe.models.StationEntity.StationEntity
import fr.scalapompe.models.StationEntity.Geom

/** Mock for the [[StationsApiRecords]] model, to be used in unit tests.
  */
object StationsApiRecordsMock {

  val station1 = StationEntity(
    id = 93240007,
    latitude = "4894874.99536",
    longitude = 237988.07028,
    adresse = "21 AV.DE STALINGRAD - COURS ST L",
    ville = "Stains",
    cp = "93240",
    horaires = null,
    geom = Geom(
      lon = 2.3798807028,
      lat = 48.9487499536
    ),
    gazole_prix = "1.906",
    gazole_maj = "2023-12-20T00:01:00+00:00"
  )

  val station2 = StationEntity(
    id = 95000007,
    latitude = "4903710",
    longitude = 207560.0,
    adresse = "3 Avenue des Trois Fontaines",
    ville = "Cergy",
    cp = "95000",
    horaires = null,
    geom = Geom(
      lon = 2.0756,
      lat = 49.0371
    ),
    gazole_prix = "1.999",
    gazole_maj = "2023-10-16T12:54:23+00:00"
  )

  val value = StationsApiRecords(
    total_count = 2,
    results = List(
      station1,
      station2
    )
  )
}
