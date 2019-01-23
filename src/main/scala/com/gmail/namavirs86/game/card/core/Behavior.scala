package com.gmail.namavirs86.game.card.core

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.gmail.namavirs86.game.card.core.Definitions.Flow
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
      process(flow)
      sender ! ResponseBehaviorProcess(playerRef, flow)

    case _ => println("that was unexpected")
  }

  def process(flow: Flow): Unit
}
