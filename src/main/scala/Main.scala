import zio._
import zio.Console.printLine

object Main extends ZIOAppDefault {

  // Define an effect to ask a question and read user input
  val askUser: ZIO[Console, IOException, String] =
    for {
      _     <- console.putStrLn("What is your location ?")
      input <- console.getStrLn
    } yield input

  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    askUser.flatMap(location => console.putStrLn(s"You are located at $name!")).exitCode
}
