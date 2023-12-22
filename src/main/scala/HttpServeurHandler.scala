import zio.ZIO
import zio.stream.*
import zio.*
import zio.http.*
import java.nio.file.Paths

object HttpServeurHandler extends ZIOAppDefault {

  // URL for the local data route
  val relativePath = Paths.get("").toAbsolutePath()

  val app: HttpApp[Any] =
    Routes(
      Method.GET / "data" -> Handler.fromStream(ZStream.fromPath(Paths.get(relativePath.toString().concat("\\src\\main\\scala\\data.txt"))))
    ).sandbox.toHttpApp

  override val run = Server.serve(app).provide(Server.default)
}
