package fr.scalapompe.controllers

import zio.http._
import fr.scalapompe.services.FindStationService
import fr.scalapompe.mocks.StationsApiRecordsMock

object SearchController {
  val routes = Routes(
    Method.GET / "search" -> handler(getSearchResults())
  )

  /** GET /search : Retourne le résultat de la recherche */
  def getSearchResults() = {
    FindStationService
      .ComputeQueryResultFromAllStations(StationsApiRecordsMock.value)
      .map(response => Response.json(response))
  }
}
