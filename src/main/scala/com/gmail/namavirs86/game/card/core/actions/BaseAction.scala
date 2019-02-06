package com.gmail.namavirs86.game.card.core.actions

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.gmail.namavirs86.game.card.core.Definitions.{Flow, GameContext}
import com.gmail.namavirs86.game.card.core.Exceptions.UnknownMessageException
import BaseAction.{RequestActionProcess, ResponseActionProcess}

trait BaseActionMessages {

  final case class RequestActionProcess[C <: GameContext](playerRef: ActorRef, flow: Flow[C])

  final case class ResponseActionProcess[C <: GameContext](playerRef: ActorRef, flow: Flow[C])

}

object BaseAction extends BaseActionMessages

abstract class BaseAction[C <: GameContext] extends Actor with ActorLogging {
  val id: String

  override def receive: Receive = {
    case RequestActionProcess(playerRef: ActorRef, flow: Flow[C]) ⇒
      validateRequest(flow)
      sender ! ResponseActionProcess(playerRef, flow.copy(
        gameContext = process(flow)
      ))

    case _ => throw UnknownMessageException()
  }

  def process(flow: Flow[C]): Option[C]

  def validateRequest(flow: Flow[C]): Unit
}
