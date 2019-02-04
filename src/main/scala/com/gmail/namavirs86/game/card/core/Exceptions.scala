package com.gmail.namavirs86.game.card.core

object Exceptions {

  final case class NoGameContextException()
    extends RuntimeException("No game context found")

  final case class NoActionHandlerException(action: String)
    extends RuntimeException(s"No handler found for action: $action")

  final case class UnknownMessageException() extends RuntimeException()

}
