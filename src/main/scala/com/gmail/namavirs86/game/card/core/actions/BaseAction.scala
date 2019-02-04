package com.gmail.namavirs86.game.card.core.actions

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.gmail.namavirs86.game.card.core.Definitions.{Flow, GameContext}
import com.gmail.namavirs86.game.card.core.Exceptions.UnknownMessageException
import BaseAction.{RequestActionProcess, ResponseActionProcess}

trait BaseActionMessages {

  final case class RequestActionProcess(playerRef: ActorRef, flow: Flow)

  final case class ResponseActionProcess(playerRef: ActorRef, flow: Flow)

}

object BaseAction extends BaseActionMessages

trait Action {
  val id: String

  def process(flow: Flow): Option[GameContext]

  def validateRequest(flow: Flow): Unit
}

abstract class BaseAction extends Actor with Action with ActorLogging {
  val id: String

  override def receive: Receive = {
    case RequestActionProcess(playerRef: ActorRef, flow: Flow) ⇒
      validateRequest(flow)
      sender ! ResponseActionProcess(playerRef, flow.copy(
        gameContext = process(flow)
      ))

    case _ => throw UnknownMessageException()
  }
}
