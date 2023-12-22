package fr.scalapompe.controllers

import zio.http._
import fr.scalapompe.services.FindStationService
import fr.scalapompe.mocks.StationsApiRecordsMock
import zio.Task

object SearchController {
  val routes = Routes(
    Method.GET / "search" -> Handler.fromFunctionZIO(getSearchResults)
  )

  /** GET /search : Retourne le résultat de la recherche */
  def getSearchResults(request: Request): Task[Response] = {
    FindStationService
      .ComputeQueryResultFromAllStations(StationsApiRecordsMock.value)
      .map(response => Response.json(response))
  }
}
