package com.gmail.namavirs86.game.card.core

import scala.collection.mutable.ListBuffer
import scala.util.Random
import org.scalatest.{Matchers, WordSpecLike}
import com.gmail.namavirs86.game.card.core.Definitions._
import com.gmail.namavirs86.game.card.core.helpers.Helpers
import com.gmail.namavirs86.game.card.core.random.RandomCheating

class ShoeManagerSpec extends WordSpecLike with Matchers {

  val shoeManager = new ShoeManager(Helpers.shoeManagerSettings)

  "Shoe Manager" should {
    "draw a card" in {
      val rng = new Random()
      val shoe = List(
        Card(Rank.ACE, Suit.CLUBS),
        Card(Rank.QUEEN, Suit.HEARTS),
      )

      val (card, resultShoe) = shoeManager.draw(rng, shoe)
      card shouldBe Card(Rank.ACE, Suit.CLUBS)
      resultShoe shouldBe List(Card(Rank.QUEEN, Suit.HEARTS))
    }

    "reshuffle and draw a card" in {
      val cheat = (for(i ← 1 to 51) yield 1).to[ListBuffer]
      val rng = new RandomCheating(cheat)
      val (card, resultShoe) = shoeManager.draw(rng, List(), isNewRound = true)

      card shouldBe Card(Rank.TWO, Suit.CLUBS)
      resultShoe.length shouldBe 51
    }
  }
}
