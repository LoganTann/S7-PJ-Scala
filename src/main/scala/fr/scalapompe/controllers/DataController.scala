package fr.scalapompe.controllers

import zio.http._
import fr.scalapompe.services.FindStationService
import fr.scalapompe.mocks.StationsApiRecordsMock
import java.nio.file.Paths
import zio.stream.ZStream

object DataController {
  val routes = Routes(
    Method.GET / "data" -> handler(getData())
  )

  val relativePath = Paths.get("").toAbsolutePath()

  /** GET /data : Retourne le contenu du fichier data.txt en JSON
    */
  def getData() = {
    val file =
      Paths.get(relativePath.toString().concat("\\src\\main\\scala\\data.txt"))

    ZStream
      .fromPath(file)
      .runCollect
      .map(bytes => new String(bytes.toArray))
      .map(response => Response.json(response))
  }
}
