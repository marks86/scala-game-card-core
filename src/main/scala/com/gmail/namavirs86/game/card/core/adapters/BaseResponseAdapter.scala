package com.gmail.namavirs86.game.card.core.adapters

import spray.json.JsValue
import akka.actor.{Actor, ActorLogging, ActorRef}
import com.gmail.namavirs86.game.card.core.Definitions.{Flow, GameContext}
import com.gmail.namavirs86.game.card.core.Exceptions.UnknownMessageException
import BaseResponseAdapter.{RequestCreateResponse, ResponseCreateResponse}

trait BaseResponseAdapterMessages {

  final case class RequestCreateResponse[C <: GameContext](playerRef: ActorRef, flow: Flow[C])

  final case class ResponseCreateResponse[C <: GameContext](playerRef: ActorRef, flow: Flow[C])

}

object BaseResponseAdapter extends BaseResponseAdapterMessages

abstract class BaseResponseAdapter[C <: GameContext] extends Actor with ActorLogging {
  val id: String

  override def receive: Receive = {
    case RequestCreateResponse(playerRef: ActorRef, flow: Flow[C]) ⇒
      sender ! ResponseCreateResponse(playerRef, flow.copy(
        response = process(flow)
      ))

    case _ => throw UnknownMessageException()
  }

  def process(flow: Flow[C]): Option[JsValue]
}
