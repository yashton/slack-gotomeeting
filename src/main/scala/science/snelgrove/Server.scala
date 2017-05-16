package science.snelgrove

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.io.StdIn
import java.time._
import scala.concurrent.Future
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

object Server extends App {
  implicit val system = ActorSystem("the-system")
  implicit val materializer = ActorMaterializer()
  implicit val execContext = system.dispatcher

  val bindingFuture = Http().bindAndHandle(Routes.route, "0.0.0.0", 8080)

  StdIn.readLine() // block for user
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
