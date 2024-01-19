package fr.scalapompe.services
import zio._
import zio.test._
import zio.test.Assertion._
import fr.scalapompe.models.StationEntity.Geom
import zio.http.Request
import zio.http.URL

object HelperServiceSpec extends ZIOSpecDefault {
  def spec = suite("HelperService")(
    suite("fileToString")(
      test("Should output the content of a file as a string When giving relative path") {
        // GIVEN
        val relativeFilePath = "/src/test/scala/fr/scalapompe/mocks/dummyFile.txt"
        for {
          // WHEN
          fileContent <- HelpersService.fileToString(relativeFilePath)
          // THEN
        } yield assertTrue(fileContent == "Hello")
      }
    ),
    suite("decodeJson")(
      test("Should decode JSON into object When giving representative API Response string") {
        // GIVEN
        val JSONString = """
          {
            "total_count": 268,
            "results": [
              {
                "id": 91410004,
                "latitude": "4870600",
                "longitude": 234300.0,
                "adresse": "69 Avenue Charles de Gaulle",
                "ville": "Morangis",
                "cp": "91420",
                "horaires": "{\"@automate-24-24\": \"1\", \"jour\": [{\"@id\": \"1\", \"@nom\": \"Lundi\", \"@ferme\": \"\"}, {\"@id\": \"2\", \"@nom\": \"Mardi\", \"@ferme\": \"\"}, {\"@id\": \"3\", \"@nom\": \"Mercredi\", \"@ferme\": \"\"}, {\"@id\": \"4\", \"@nom\": \"Jeudi\", \"@ferme\": \"\"}, {\"@id\": \"5\", \"@nom\": \"Vendredi\", \"@ferme\": \"\"}, {\"@id\": \"6\", \"@nom\": \"Samedi\", \"@ferme\": \"\"}, {\"@id\": \"7\", \"@nom\": \"Dimanche\", \"@ferme\": \"\"}]}",
                "geom": {
                  "lon": 2.343,
                  "lat": 48.706
                },
                "gazole_maj": "2023-12-19T08:05:44+00:00",
                "gazole_prix": "1.749"
              }
            ]
          }
        """
        for {
          // WHEN
          decodedObject <- HelpersService.decodeJson(JSONString)
          // THEN
        } yield assertCompletes
          && assertTrue(decodedObject.total_count == 268)
          && assertTrue(decodedObject.results.length == 1)
          && assertTrue(decodedObject.results.last.geom == Geom(2.343, 48.706))
      }
    ),
    suite("extractQuery")(
      test("Should create default parameters When no query params are set up") {
        // GIVEN
        val request = Request(
          url = URL.decode("http://localhost:8080/search").toOption.get
        )

        // WHEN
        val parsedQuery = HelpersService.extractQuery(request)

        // THEN
        assertTrue(parsedQuery.lat == 48.7886889)
        assertTrue(parsedQuery.lon == 2.358667)
        assertTrue(parsedQuery.distance == 10)
        assertTrue(parsedQuery.isOffline == false)
      },
      test("Should set parameters When specified in query params") {
        // GIVEN
        val request = Request(
          url = URL.decode("http://localhost:8080/search?offline&lat=2.99&lon=48.001&distance=20").toOption.get
        )

        // WHEN
        val parsedQuery = HelpersService.extractQuery(request)

        // THEN
        assertTrue(parsedQuery.lat == 48.001)
        assertTrue(parsedQuery.lon == 2.99)
        assertTrue(parsedQuery.distance == 20)
        assertTrue(parsedQuery.isOffline == true)
      },
      test("Should have default position When lat or lon is invalid") {
        // GIVEN
        val request = Request(
          url = URL.decode("http://localhost:8080/search?lat=55&lon=toto").toOption.get
        )

        // WHEN
        val parsedQuery = HelpersService.extractQuery(request)

        // THEN
        assertTrue(parsedQuery.lat == 48.7886889)
        assertTrue(parsedQuery.lon == 2.358667)
      }
    )
  )
}
