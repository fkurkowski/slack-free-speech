package io.github.fkurkowski.bot

import dispatch._
import scala.collection._
import scala.concurrent.ExecutionContext.Implicits.global
import scalawebsocket.WebSocket

import org.json4s.native.Serialization.{read,write}


/**
 * @author fkurkowski
 */

trait Bot extends JsonFormats {

  /**
   * Underlying web socket connection
   */
  val ws: WebSocket

  /**
   * Available channels and groups
   */
  val channels: Seq[Channel]
  val groups: Seq[Group]

  val actions: mutable.Buffer[IncomingMessage => Unit] = mutable.Buffer()

  def onMessage(f: IncomingMessage => Unit): Unit = actions += f

  def send(message: OutgoingMessage): Unit = {
    val payload = write(message)
    ws.sendText(payload)
  }

  private[bot] def publish(message: String): Unit = {
    val incoming = read[IncomingMessage](message)
    actions.foreach(f => f(incoming))
  }
}

object Bot extends JsonFormats {
  private val endpoint = "https://slack.com/api/rtm.start"

  def apply(token: String): Future[Bot] = {
    val svc = url(endpoint) <<? Map("token" -> token)

    Http(svc OK as.String) map { response =>
      val connected = read[Connected](response)

      new Bot {
        val ws: WebSocket = WebSocket()
          .open(connected.url)
          .onTextMessage(publish)

        val channels = connected.channels
        val groups = connected.groups
      }
    }
  }
}
