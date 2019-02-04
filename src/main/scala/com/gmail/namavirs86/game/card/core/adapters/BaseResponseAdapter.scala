package com.gmail.namavirs86.game.card.core.adapters

import spray.json.JsValue
import akka.actor.{Actor, ActorLogging, ActorRef}
import com.gmail.namavirs86.game.card.core.Definitions.Flow
import com.gmail.namavirs86.game.card.core.Exceptions.UnknownMessageException
import BaseResponseAdapter.{RequestCreateResponse, ResponseCreateResponse}

trait BaseResponseAdapterMessages {

  final case class RequestCreateResponse(playerRef: ActorRef, flow: Flow)

  final case class ResponseCreateResponse(playerRef: ActorRef, flow: Flow)

}

object BaseResponseAdapter extends BaseResponseAdapterMessages

abstract class BaseResponseAdapter extends Actor with ActorLogging {
  val id: String

  override def receive: Receive = {
    case RequestCreateResponse(playerRef: ActorRef, flow: Flow) ⇒
      sender ! ResponseCreateResponse(playerRef, flow.copy(
        response = process(flow)
      ))

    case _ => throw UnknownMessageException()
  }

  def process(flow: Flow): Option[JsValue]
}
