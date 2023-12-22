import zio._
import zio.http._
import zio.stream._
import zio.Duration._
import StationEntity.* 

object StreamAPI extends ZIOAppDefault {

  def fetchData() = {
    val url = URL
      .decode(
        "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records?select=id%2C%20latitude%2C%20longitude%2C%20adresse%2C%20ville%2C%20cp%2C%20horaires%2C%20geom%2C%20gazole_maj%2C%20gazole_prix&limit=20&refine=region%3A%22%C3%8Ele-de-France%22&refine=carburants_disponibles%3A%22Gazole%22"
      )
      .toOption
      .get // unsafe

    for {
      client <- ZIO.service[Client]
      res <- client.url(url).get("/")
    } yield res
  }

  override def run: ZIO[Any, Any, Unit] =
    val appLogic = for {
      _ <- ZStream(fetchData())
        .repeat(Schedule.spaced(10.seconds))
        // .groupedWithin(30, 10.seconds)
        .mapZIO { z =>
          for {
            res <- z
            body <- res.body.asString 
          } yield body          
        }
        .foreach(Console.printLine(_))
    } yield ()

    appLogic.provide(Client.default, Scope.default)

}
