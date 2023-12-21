package fr.scalapompe.models.StationEntity

case class Geom(
    lon: Double,
    lat: Double
)

case class StationEntity(
    id: String,
    latitude: String,
    longitude: Double,
    adresse: String,
    ville: String,
    cp: String,
    horaires: String,
    geom: Geom,
    gazole_prix: String,
    gazole_maj: String
)
