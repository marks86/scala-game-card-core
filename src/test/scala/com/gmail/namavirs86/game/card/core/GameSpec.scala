package com.gmail.namavirs86.game.card.core

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, OptionValues, WordSpecLike}
import com.gmail.namavirs86.game.card.core.Definitions.{GameConfig, GamePlayResponse}
import com.gmail.namavirs86.game.card.core.Game.ResponsePlay
import com.gmail.namavirs86.game.card.core.helpers.Helpers.TestActionType
import com.gmail.namavirs86.game.card.core.helpers._

class GameSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with OptionValues
    with WordSpecLike
    with BeforeAndAfterAll {

  def this() = this(ActorSystem(classOf[GameSpec].getSimpleName))

  val config = GameConfig(
    id = "bj",
    actions = Map(TestActionType.DEAL → TestAction.props(1)),
    responseAdapter = TestResponseAdapter.props,
    behavior = TestBehavior.props
  )

  override def afterAll: Unit = {
    shutdown(system)
  }

  "A Game actor" should {
    "process requested action" in {
      val probe = TestProbe()
      val game = system.actorOf(Game.props(config), "gameActor")
      val flow = Helpers.createFlow()

      game.tell(Game.RequestPlay(flow), probe.ref)

      val response = probe.expectMsgType[ResponsePlay]
      val requestContext = response.flow.requestContext
      requestContext.requestId shouldBe 0
      requestContext.action shouldBe TestActionType.DEAL
      response.flow.response shouldBe Some(GamePlayResponse())
    }
  }
}