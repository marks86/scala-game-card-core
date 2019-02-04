package com.gmail.namavirs86.game.card.core

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.gmail.namavirs86.game.card.core.Definitions.{Flow, GameContext}
import com.gmail.namavirs86.game.card.core.Exceptions.UnknownMessageException
import Behavior.{RequestBehaviorProcess, ResponseBehaviorProcess}

trait BehaviorMessages {

  final case class RequestBehaviorProcess(playerRef: ActorRef, flow: Flow)

  final case class ResponseBehaviorProcess(playerRef: ActorRef, flow: Flow)

}

object Behavior extends BehaviorMessages

abstract class Behavior extends Actor with ActorLogging {
  val id: String

  override def receive: Receive = {
    case RequestBehaviorProcess(playerRef: ActorRef, flow: Flow) ⇒
      sender ! ResponseBehaviorProcess(playerRef, flow.copy(
        gameContext = process(flow)
      ))

    case _ => throw UnknownMessageException()
  }

  def process(flow: Flow): Option[GameContext]
}
