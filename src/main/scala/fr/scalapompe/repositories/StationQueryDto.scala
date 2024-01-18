package fr.scalapompe.repositories

/** Parameters for the search
  * @param lat
  *   Latitude, defaults to the one of EFREI.
  * @param lon
  *   Longitude, default to the one of EFREI.
  * @param distance
  *   Max distance radius in km, defaults to 10km.
  * @param isOffline
  *   If true, will use the local file instead of the API. Defaults to false.
  */
final case class StationQueryDto(
    lat: Double = 48.7886889,
    lon: Double = 2.358667,
    distance: Int = 10,
    isOffline: Boolean = false
)
