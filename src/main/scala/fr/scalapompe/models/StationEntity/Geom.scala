package fr.scalapompe.models.StationEntity
import zio.json._
import fr.scalapompe.repositories.StationQueryDto

/** Represents a geolocation point. Provided by the API.
  */
case class Geom(
    lon: Double,
    lat: Double
)

object Geom {
  implicit val decoder: JsonDecoder[Geom] =
    DeriveJsonDecoder.gen[Geom]
  extension (geom: Geom) {

    /** Calculates the distance between two geographical points. Inspired by
      * https://www.baeldung.com/java-find-distance-between-points
      */
    def distance(target: Geom) = {
      val lat1Rad = Math.toRadians(geom.lat)
      val lat2Rad = Math.toRadians(target.lat)
      val lon1Rad = Math.toRadians(geom.lon)
      val lon2Rad = Math.toRadians(target.lon)

      val x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2)
      val y = (lat2Rad - lat1Rad)

      Math.sqrt(x * x + y * y) * 6371
    }
  }
}
