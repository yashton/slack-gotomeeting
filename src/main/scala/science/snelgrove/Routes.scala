package science.snelgrove
import akka.stream.Materializer
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import argonaut._, Argonaut._
import scala.concurrent._
import java.time._
import de.heikoseeberger.akkahttpargonaut.ArgonautSupport
import com.typesafe.config.ConfigFactory

object Routes extends ArgonautSupport {
  import Serialization._

  val config = ConfigFactory.load()
  val token = config.getString("slack.token")
  val team_id = config.getString("slack.team.id")
  val team_domain = config.getString("slack.team.domain")
  val command = config.getString("slack.command")

  def validateParams(token: String, team_id: String, team_domain: String, command: String): Boolean = {
    return token == this.token && team_id == this.team_id && team_domain == this.team_domain && command == this.command
  }

  val validateForm =
    formFields('token, 'team_id, 'team_domain, 'command) tflatMap { values =>
      validate((validateParams _).tupled(values), "Request doesn't match expected identifiers")
    }

  def createRequest(command: String): MeetingRequest = {
    return MeetingRequest(command, Instant.now(), Instant.now().plus(Duration.ofHours(1)))
  }

  val statusCheck = formFields('ssl_check) { check => complete(StatusCodes.OK) }

  val createMeeting = formFields('text) map { createRequest }

  def route(implicit exec: ExecutionContext, system: ActorSystem, materializer: Materializer) =
    (path("meeting") & post) {
      statusCheck ~
      (validateForm & createMeeting) { meetingRequest =>
        complete {
          GotoMeeting.hitGoto(meetingRequest) map { meeting =>
            SlackResponse(meeting.joinUrl)
          }
        }
      }
    }
}
