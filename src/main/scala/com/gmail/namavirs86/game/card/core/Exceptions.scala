package com.gmail.namavirs86.game.card.core

object Exceptions {

  final case class NoGameContextException() extends RuntimeException("No game context found")

}
