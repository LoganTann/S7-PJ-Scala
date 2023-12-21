import Main.httpStream
import zio.Console.{printLine, _}
import zio._
import zio.http.{Client, Response, URL}

import java.io.IOException

class HttpStream {
  val Urls: Map[String, String]
  = Map(
    "Gazole" -> "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records?select=id%2C%20latitude%2C%20longitude%2C%20adresse%2C%20ville%2C%20cp%2C%20horaires%2C%20geom%2C%20gazole_maj%2C%20gazole_prix&limit=20&refine=region%3A\"Île-de-France\"&refine=carburants_disponibles%3A\"Gazole\"",
    "banana" -> "https://test1",
    "cherry" -> "https://test1"
  )

  def fetchData(urlToFetch : String) : ZIO[Any, Throwable, String] = {
    val url = URL.decode(urlToFetch)
      .toOption
      .get // unsafe

    for {
      client <- ZIO.service[Client]
      res <- client.url(url).get("/")
      _      <- ZIO.effectTotal(println(s"res = ${res.body.toString()}"))
    } yield res
  }

  def init() : ZIO[Any, Throwable, String] = {
    return fetchData(Urls.get("Gazole").toString())
  }
}


object Main extends ZIOAppDefault {

  var httpStream: HttpStream = new HttpStream

  // Define an effect to ask a question and read user input
  //  val askUser: ZIO[Any, IOException, ExitCode] =
  //    for {
  //      _     <- printLine("What is your location ?")
  //      input <- readLine
  //      _ <- printLine(s"You are located at $input!")
  //      exitCode <- ZIO.succeed(ExitCode.success)
  //    } yield exitCode
  val init: ZIO[Any, Throwable, String] = {
    httpStream.init()
  }
  def init(): Unit = {

  }

  override def run: ZIO[Any, Throwable, String] = init
}
