package science.snelgrove

import java.time.Instant

case class Meeting(
  joinUrl: String,
  meetingid: Int,
  maxParticipants: Int,
  uniqueMeetingId: Int,
  conferenceCallInfo: String)

case class MeetingRequest(
  subject: String,
  starttime: Instant,
  endtime: Instant,
  passwordrequired: Boolean = false,
  conferencecallinfo: String = "PSTN",
  meetingtype: String = "immediate",
  timezonekey: String = "")
