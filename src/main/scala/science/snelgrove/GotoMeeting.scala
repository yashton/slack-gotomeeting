package science.snelgrove

import scala.concurrent._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.{ ActorMaterializer, ActorMaterializerSettings }
import akka.util.ByteString
import HttpMethods._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.marshalling.Marshal
import akka.actor.ActorSystem
import de.heikoseeberger.akkahttpargonaut.ArgonautSupport
import akka.stream.Materializer

object GotoMeeting extends ArgonautSupport {
  import Serialization._

  val config = ConfigFactory.load()
  val token = config.getString("goto.oauth.access_token")

  def hitGoto(body: MeetingRequest)
      (implicit execution: ExecutionContext,
        system: ActorSystem,
        materializer: Materializer): Future[Meeting] = {
    for {
      entity <- Marshal(body).to[RequestEntity]
      request = HttpRequest(
        POST,
        uri = "$api/meetings",
        entity = entity,
        headers = List(Authorization.oath(token))
      response <- Http().singleRequest(request)
      result <- Unmarshal(response.entity).to[Meeting]
    } yield result
  }
}
