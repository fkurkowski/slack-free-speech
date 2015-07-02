package io.github.fkurkowski.bot

/**
 * @author fkurkowski
 */

case class Connected(url: String, channels: Seq[Channel], groups: Seq[Group])
case class Channel(id: String, name: String)
case class Group(id: String, name: String)
case class OutgoingMessage(id: Int, `type`: String, channel: String, text: String)
case class IncomingMessage(`type`: String, channel: String, user: String, text: Option[String])

