package io.github.fkurkowski.slackfreespeech


import java.util.concurrent.atomic.AtomicInteger

import com.typesafe.config.{Config, ConfigFactory}
import io.github.fkurkowski.bot.{OutgoingMessage, IncomingMessage, Bot}
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {
  val config: Config = ConfigFactory.load()
  val counter: AtomicInteger = new AtomicInteger()

  Bot(config.getString("token")) onSuccess {
    case bot =>
      val groupBroadcast = bot.groups
        .find(_.name == "zuerasemlimite")
        .map(_.id)

      groupBroadcast foreach { id =>
        bot onMessage {
          case IncomingMessage("message", channel, _, Some(text)) if channel.startsWith("D") =>
            bot.send(OutgoingMessage(counter.getAndIncrement(), "message", id, text))
      }
    }
  }
}

