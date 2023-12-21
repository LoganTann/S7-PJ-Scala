import zio.*
import zio.http.*
import zio.http.netty.NettyConfig
import zio.http.netty.NettyConfig.LeakDetectionLevel
import zio.stream.ZStream

import java.nio.file.Paths
import scala.util.Try

object sc extends ZIOAppDefault {

    // Set a port
    val PORT = 0

    val relativePath = Paths.get("").toAbsolutePath()

    val data = Routes(
      Method.GET / "data" -> Handler.fromStream(ZStream.fromPath(Paths.get(relativePath.toString().concat("\\src\\main\\scala\\data.txt"))))
    ).sandbox.toHttpApp

    val run = ZIOAppArgs.getArgs.flatMap { args =>
      // Configure thread count using CLI
      val nThreads: Int = args.headOption.flatMap(x => Try(x.toInt).toOption).getOrElse(0)

      val config = Server.Config.default
        .port(PORT)
      val nettyConfig = NettyConfig.default
        .leakDetection(LeakDetectionLevel.PARANOID)
        .maxThreads(nThreads)
      val configLayer = ZLayer.succeed(config)
      val nettyConfigLayer = ZLayer.succeed(nettyConfig)

      (Server.install(data).flatMap { port =>
        Console.printLine(s"Started server on port: $port")
      } *> ZIO.never)
        .provide(configLayer, nettyConfigLayer, Server.customized)
    }
  }
