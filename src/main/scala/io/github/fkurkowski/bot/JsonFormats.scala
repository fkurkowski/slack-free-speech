package io.github.fkurkowski.bot

import org.json4s.DefaultFormats

/**
 * @author fkurkowski
 */
trait JsonFormats {
  implicit val formats = DefaultFormats
}
