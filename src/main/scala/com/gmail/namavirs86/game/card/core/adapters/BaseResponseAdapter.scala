package com.gmail.namavirs86.game.card.core.adapters

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.gmail.namavirs86.game.card.core.Definitions.{Flow, GamePlayResponse}
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

    case _ => println("that was unexpected")
  }

  def process(flow: Flow): Option[GamePlayResponse]
}
