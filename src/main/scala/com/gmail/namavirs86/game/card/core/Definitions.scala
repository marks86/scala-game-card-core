package com.gmail.namavirs86.game.card.core

import akka.actor.Props
import Definitions.Outcome.Outcome
import Definitions.Rank.Rank
import Definitions.Suit.Suit
import com.gmail.namavirs86.game.card.core.Definitions.RequestType.RequestType

import scala.util.Random

object Definitions {

  case class GameConfig(
                         id: String,
                         actions: Map[ActionType, Props],
                         responseAdapter: Props,
                         behavior: Props,
                       )

  object RequestType {

    sealed abstract class RequestType

    case object INIT extends RequestType

    case object PLAY extends RequestType

    val requestTypes = List(INIT, PLAY)

  }

  type GameId = String

  case class RequestContext(
                             request: RequestType,
                             gameId: GameId,
                             requestId: Long,
                             action: ActionType,
                             bet: Option[Float],
                           )

  type Hand = List[Card]

  type Shoe = List[Card]

  case class ResponseDealerContext(
                                    hand: Hand,
                                    value: Int,
                                    hasBJ: Boolean,
                                  )

  case class ResponsePlayerContext(
                                    hand: Hand,
                                    value: Int,
                                    hasBJ: Boolean,
                                  )
  case class GamePlayResponse(
                             dealer: ResponseDealerContext,
                             player: ResponsePlayerContext,
                             outcome: Option[Outcome],
                             bet: Option[Float],
                             totalWin: Float,
                             roundEnded: Boolean,
                             )

  case class PlayerContext(
                            hand: Hand,
                            value: Int,
                            hasBJ: Boolean,
                          )

  case class DealerContext(
                            hand: Hand,
                            value: Int,
                            holeCard: Option[Card],
                            hasBJ: Boolean,
                          )

  case class GameContext(
                          dealer: DealerContext,
                          player: PlayerContext,
                          shoe: List[Card],
                          outcome: Option[Outcome],
                          bet: Option[Float],
                          totalWin: Float,
                          roundEnded: Boolean,
                        )

  case class Flow(
                   requestContext: RequestContext,
                   gameContext: Option[GameContext],
                   response: Option[GamePlayResponse],
                   rng: Random,
                 )

  case class ShoeManagerSettings(
                                  deckCount: Int,
                                  cutCardPosition: Int,
                                )

  object Outcome {

    sealed abstract class Outcome

    case object DEALER extends Outcome

    case object PLAYER extends Outcome

    case object TIE extends Outcome

    val outcomes = List(DEALER, PLAYER, TIE)
  }

  type ActionType = String

  object Suit {

    sealed abstract class Suit

    case object CLUBS extends Suit

    case object SPADES extends Suit

    case object HEARTS extends Suit

    case object DIAMONDS extends Suit

    val suits = List(CLUBS, SPADES, HEARTS, DIAMONDS)
  }

  object Rank {

    sealed abstract class Rank

    case object TWO extends Rank

    case object THREE extends Rank

    case object FOUR extends Rank

    case object FIVE extends Rank

    case object SIX extends Rank

    case object SEVEN extends Rank

    case object EIGHT extends Rank

    case object NINE extends Rank

    case object TEN extends Rank

    case object JACK extends Rank

    case object QUEEN extends Rank

    case object KING extends Rank

    case object ACE extends Rank

    val ranks = List(TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE)
  }

  case class Card(rank: Rank, suit: Suit)

  type CardValues = Map[Rank, Int]

}
