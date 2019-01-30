package com.gmail.namavirs86.game.card.core.helpers

import akka.actor.Props
import com.gmail.namavirs86.game.card.core.{Behavior, BehaviorMessages}
import com.gmail.namavirs86.game.card.core.Definitions.{Flow, GameContext}

object TestBehavior extends BehaviorMessages {
  def props: Props = Props(new TestBehavior())
}

class TestBehavior extends Behavior {
  val id = "testBehavior"

  def process(flow: Flow): Option[GameContext] = flow.gameContext
}
