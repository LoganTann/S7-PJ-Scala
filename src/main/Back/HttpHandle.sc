import zio._
import zio.http._
import zio.stream._
import zio.Duration._

class ConfigBackModule

object HttpStream {

        def fetchData(urlToFetch :String) = {
                val url = URL.decode(urlToFetch)
                        .toOption
                        .get // unsafe

                for {
                        client <- ZIO.service[Client]
                        res <- client.url(url).get("/")
                } yield res
        }

        override def run: ZIO[Any, Any, Unit] =
        val appLogic = for {

        // .groupedWithin(30, 10.seconds)
        .mapZIO { z =>
        for {
        res <- z
        body <- res.body.asString
        _ <- Console.printLine(s"body size is: ${body.length}")
        } yield body
        }
        .foreach(Console.printLine(_))
        } yield ()

        appLogic.provide(Client.default, Scope.default)
}
