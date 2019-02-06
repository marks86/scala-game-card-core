package com.gmail.namavirs86.game.card.core.helpers

import akka.actor.{ActorLogging, Props}
import com.gmail.namavirs86.game.card.core.Definitions.{Flow, GameContext}
import com.gmail.namavirs86.game.card.core.actions.{BaseAction, BaseActionMessages}
import com.gmail.namavirs86.game.card.core.helpers.Helpers.TestGameContext

object TestAction extends BaseActionMessages {
  def props(a: Int): Props = Props(new TestAction(a))
}

class TestAction(a: Int) extends BaseAction[TestGameContext] with ActorLogging {
  val id = "testAction"

  def process(flow: Flow[TestGameContext]): Option[TestGameContext] = flow.gameContext

  def validateRequest(flow: Flow[TestGameContext]): Unit = {}
}
