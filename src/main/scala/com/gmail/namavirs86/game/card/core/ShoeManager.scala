package com.gmail.namavirs86.game.card.core

import com.gmail.namavirs86.game.card.core.Definitions._

import scala.util.Random

class ShoeManager(settings: ShoeManagerSettings) {

  def draw(rng: Random, shoe: Shoe, isNewRound: Boolean = false): (Card, Shoe) = {
    val cards =
      if (isNewRound && checkForReShuffle(shoe)) shuffle(rng) else shoe

    (cards.head, cards.tail)
  }

  protected def checkForReShuffle(shoe: Shoe): Boolean = {
    val shoeMaxLen = Rank.ranks.length * Suit.suits.length * settings.deckCount
    shoeMaxLen - shoe.length >= settings.cutCardPosition
  }

  protected def shuffle(rng: Random): Shoe = {
    val shoe = for (
      s ← Suit.suits;
      r ← Rank.ranks;
      i ← 1 to settings.deckCount
    )
      yield Card(r, s)

    rng.shuffle(shoe)
  }
}
