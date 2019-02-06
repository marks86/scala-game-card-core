package com.gmail.namavirs86.game.card.core

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.gmail.namavirs86.game.card.core.Definitions.{Flow, GameContext}
import com.gmail.namavirs86.game.card.core.Exceptions.UnknownMessageException
import Behavior.{RequestBehaviorProcess, ResponseBehaviorProcess}

trait BehaviorMessages {

  final case class RequestBehaviorProcess[C <: GameContext](playerRef: ActorRef, flow: Flow[C])

  final case class ResponseBehaviorProcess[C <: GameContext](playerRef: ActorRef, flow: Flow[C])

}

object Behavior extends BehaviorMessages

abstract class Behavior[C <: GameContext] extends Actor with ActorLogging {
  val id: String

  override def receive: Receive = {
    case RequestBehaviorProcess(playerRef: ActorRef, flow: Flow[C]) ⇒
      sender ! ResponseBehaviorProcess(playerRef, flow.copy(
        gameContext = process(flow)
      ))

    case _ => throw UnknownMessageException()
  }

  def process(flow: Flow[C]): Option[GameContext]
}
