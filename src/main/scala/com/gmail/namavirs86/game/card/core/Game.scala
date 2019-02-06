package com.gmail.namavirs86.game.card.core

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import Game.{RequestPlay, ResponsePlay}
import actions.BaseAction.{RequestActionProcess, ResponseActionProcess}
import adapters.BaseResponseAdapter.{RequestCreateResponse, ResponseCreateResponse}
import Behavior.{RequestBehaviorProcess, ResponseBehaviorProcess}
import Definitions.{ActionType, Flow, GameConfig, GameContext}
import Exceptions.{NoActionHandlerException, UnknownMessageException}

object Game {

  def props(config: GameConfig): Props = Props(new Game(config))

  final case class RequestPlay[C <: GameContext](flow: Flow[C])

  final case class ResponsePlay[C <: GameContext](flow: Flow[C])

}

// @TODO: bet validation
// @TODO: request validation in each action
class Game[C <: GameContext](config: GameConfig) extends Actor with ActorLogging {

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
    case RequestPlay(flow: Flow[C]) ⇒
      requestPlay(flow)

    case ResponseActionProcess(playerRef: ActorRef, flow: Flow[C]) ⇒
      behavior ! RequestBehaviorProcess(playerRef, flow)

    case ResponseBehaviorProcess(playerRef: ActorRef, flow: Flow[C]) ⇒
      responseAdapter ! RequestCreateResponse(playerRef, flow)

    case ResponseCreateResponse(playerRef: ActorRef, flow: Flow[C]) ⇒
      playerRef ! ResponsePlay(flow)

    case _ => throw UnknownMessageException()
  }

  protected def requestPlay(flow: Flow[C]): Unit = {
    val action = flow.requestContext.action
    actions.get(action) match {
      case Some(ref) ⇒
        ref ! RequestActionProcess(sender, flow)
      case None ⇒
        throw NoActionHandlerException(action)
    }
  }

}
