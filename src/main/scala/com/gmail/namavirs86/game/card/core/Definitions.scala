package com.gmail.namavirs86.game.card.core

import akka.actor.Props
import Definitions.Rank.Rank
import Definitions.Suit.Suit
import com.gmail.namavirs86.game.card.core.Definitions.RequestType.RequestType

import scala.util.Random

import spray.json.JsValue

object Definitions {

  final case class GameConfig(
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

  final case class RequestContext(
                                   request: RequestType,
                                   gameId: GameId,
                                   requestId: Long,
                                   action: ActionType,
                                   bet: Option[Float],
                                 )

  type Shoe = List[Card]

  trait GameContext {
    val shoe: Shoe
    val bet: Option[Float]
    val totalWin: Float
    val roundEnded: Boolean
  }

  final case class Flow[+C <: GameContext](
                                                 requestContext: RequestContext,
                                                 gameContext: Option[C],
                                                 response: Option[JsValue],
                                                 rng: Random,
                                               )

  final case class ShoeManagerSettings(
                                        deckCount: Int,
                                        cutCardPosition: Int,
                                      )

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

  final case class Card(rank: Rank, suit: Suit)

  type CardValues = Map[Rank, Int]

}
