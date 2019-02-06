package com.gmail.namavirs86.game.card.core.helpers

import com.gmail.namavirs86.game.card.core.Definitions._
import com.gmail.namavirs86.game.card.core.random.RandomCheating
import spray.json.JsString

import scala.collection.mutable.ListBuffer

object Helpers {
  object TestActionType {

    val DEAL: ActionType = "DEAL"

  }

  final case class TestGameContext(
                                    shoe: Shoe,
                                    bet: Option[Float],
                                    totalWin: Float,
                                    roundEnded: Boolean,
                                  ) extends GameContext

  def createFlow(cheat: ListBuffer[Int] = ListBuffer[Int]()): Flow[TestGameContext] = {
    Flow(
      requestContext = createRequestContext(),
      gameContext = None,
      response = None,
      rng = new RandomCheating(cheat)
    )
  }

  def createRequestContext(): RequestContext = {
    RequestContext(
      request = RequestType.PLAY,
      gameId = "bj",
      requestId = 0,
      action = TestActionType.DEAL,
      bet = Some(1f))
  }

  def createGameContext(): GameContext = {
    TestGameContext(
      shoe = List.empty[Card],
      bet = Some(1f),
      totalWin = 0f,
      roundEnded = true,
    )
  }
}

