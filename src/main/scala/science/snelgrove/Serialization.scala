package science.snelgrove

import java.time._
import argonaut._, Argonaut._

object Serialization {
  implicit val MeetingCodec: CodecJson[Meeting] =
    casecodec5(Meeting.apply, Meeting.unapply) (
      "joinUrl", "meetingId", "maxParticipants", "uniqueMeetingId", "conferenceCallInfo")

  implicit def InstantEncode: EncodeJson[Instant] = EncodeJson(i => jString(i.toString()))

  implicit def InstantDecode: DecodeJson[Instant] = StringDecodeJson.map(Instant.parse _)

  implicit val MeetingRequestCodec: CodecJson[MeetingRequest] =
    casecodec7(MeetingRequest.apply, MeetingRequest.unapply)(
      "subject", "starttime", "endtime", "passwordrequired",
      "conferencecallinfo", "meetingtype", "timezonekey")

  def SlackResponse(url: String): Json = {
    Json.obj(
      "text" := "New meeting created",
      "response_type" := "in_channel",
      "attachments" -> Json.obj("text" := url))
  }
}
