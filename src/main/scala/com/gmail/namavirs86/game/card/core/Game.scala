package com.gmail.namavirs86.game.card.core

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import Game.{RequestPlay, ResponsePlay}
import actions.BaseAction.{RequestActionProcess, ResponseActionProcess}
import adapters.BaseResponseAdapter.{RequestCreateResponse, ResponseCreateResponse}
import Behavior.{RequestBehaviorProcess, ResponseBehaviorProcess}
import Definitions.{ActionType, Flow, GameConfig}
import Exceptions.{NoActionHandlerException, UnknownMessageException}

object Game {

  def props(config: GameConfig): Props = Props(new Game(config))

  final case class RequestPlay(flow: Flow)

  final case class ResponsePlay(flow: Flow)

}

// @TODO: bet validation
// @TODO: request validation in each action
class Game(config: GameConfig) extends Actor with ActorLogging {

  protected var actions = Map.empty[ActionType, ActorRef]
  protected var behavior: ActorRef = _
  protected var responseAdapter: ActorRef = _

  override def preStart(): Unit = {
    config.actions.foreach {
      case (action, props) ⇒
        actions += action → context.actorOf(props, action)
    }

    behavior = context.actorOf(
      config.behavior, name = "behavior"
    )

    responseAdapter = context.actorOf(
      config.responseAdapter, name = "responseAdapter"
    )
  }

  override def receive: Receive = {
    case RequestPlay(flow: Flow) ⇒
      requestPlay(flow)

    case ResponseActionProcess(playerRef: ActorRef, flow: Flow) ⇒
      behavior ! RequestBehaviorProcess(playerRef, flow)

    case ResponseBehaviorProcess(playerRef: ActorRef, flow: Flow) ⇒
      responseAdapter ! RequestCreateResponse(playerRef, flow)

    case ResponseCreateResponse(playerRef: ActorRef, flow: Flow) ⇒
      playerRef ! ResponsePlay(flow)

    case _ => throw UnknownMessageException()
  }

  protected def requestPlay(flow: Flow): Unit = {
    val action = flow.requestContext.action
    actions.get(action) match {
      case Some(ref) ⇒
        ref ! RequestActionProcess(sender, flow)
      case None ⇒
        throw NoActionHandlerException(action)
    }
  }

}
