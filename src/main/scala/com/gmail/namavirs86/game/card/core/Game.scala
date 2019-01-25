package com.gmail.namavirs86.game.card.core

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import Game.{RequestPlay, ResponsePlay}
import actions.BaseAction.{RequestActionProcess, ResponseActionProcess}
import adapters.BaseResponseAdapter.{RequestCreateResponse, ResponseCreateResponse}
import Definitions.{ActionType, Flow, GameConfig, GamePlayResponse}

object Game {

  def props(config: GameConfig): Props = Props(new Game(config))

  final case class RequestPlay(flow: Flow)

  final case class ResponsePlay(flow: Flow)

}
// @TODO: bet validation
// @TODO: request validation in each action
class Game(config: GameConfig) extends Actor with ActorLogging {

  protected var actions = Map.empty[ActionType, ActorRef]
  protected var responseAdapter: ActorRef = _

  override def preStart(): Unit = {
    config.actions.foreach {
      case (actionType, props) ⇒
        actions += actionType → context.actorOf(props)
    }

    responseAdapter = context.actorOf(
      config.responseAdapter
    )
  }

  override def receive: Receive = {
    case RequestPlay(flow: Flow) ⇒
      requestPlay(flow)

    case ResponseActionProcess(playerRef: ActorRef, flow: Flow) ⇒
      responseActionProcess(playerRef, flow)

    case ResponseCreateResponse(playerRef: ActorRef, flow: Flow) ⇒
      responseCreateResponse(playerRef, flow)
  }

  protected def requestPlay(flow: Flow): Unit = {
    val actionType = flow.requestContext.action
    actions.get(actionType) match {
      case Some(ref) ⇒
        ref ! RequestActionProcess(sender, flow)
      case None ⇒
        log.info("No handler for action: {}", actionType)
    }
  }

  protected def responseActionProcess(playerRef: ActorRef, flow: Flow): Unit = {
    responseAdapter ! RequestCreateResponse(playerRef, flow)
  }

  protected def responseCreateResponse(playerRef: ActorRef, flow: Flow): Unit = {
    playerRef ! ResponsePlay(flow)
  }
}
