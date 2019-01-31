package com.gmail.namavirs86.game.card.core.helpers

import com.gmail.namavirs86.game.card.core.Definitions._
import com.gmail.namavirs86.game.card.core.random.RandomCheating
import spray.json.JsString

import scala.collection.mutable.ListBuffer

object Helpers {

  object TestActionType {

    val DEAL: ActionType = "DEAL"

    val HIT: ActionType = "HIT"

    val STAND: ActionType = "STAND"
  }

  def createFlow(cheat: ListBuffer[Int] = ListBuffer[Int]()): Flow = {
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
    GameContext(
      dealer = DealerContext(
        hand = List.empty[Card],
        value = 0,
        holeCard = None,
        hasBJ = false,
      ),
      player = PlayerContext(
        hand = List.empty[Card],
        value = 0,
        hasBJ = false,
      ),
      shoe = List.empty[Card],
      bet = None,
      totalWin = 0f,
      outcome = None,
      roundEnded = true,
    )
  }

  val cardValues: CardValues = Map(
    Rank.TWO → 2,
    Rank.THREE → 3,
    Rank.FOUR → 4,
    Rank.FIVE → 5,
    Rank.SIX → 6,
    Rank.SEVEN → 7,
    Rank.EIGHT → 8,
    Rank.NINE → 9,
    Rank.TEN → 10,
    Rank.JACK → 10,
    Rank.QUEEN → 10,
    Rank.KING → 10,
    Rank.ACE → 11
  )

  val shoeManagerSettings = ShoeManagerSettings(
    deckCount = 1,
    cutCardPosition = 40
  )

}

