package com.gmail.namavirs86.game.card.core.helpers

import akka.actor.Props
import com.gmail.namavirs86.game.card.core.Definitions.Flow
import com.gmail.namavirs86.game.card.core.adapters.{BaseResponseAdapter, BaseResponseAdapterMessages}
import com.gmail.namavirs86.game.card.core.helpers.Helpers.TestGameContext
import spray.json.{JsString, JsValue}

object TestResponseAdapter extends BaseResponseAdapterMessages {
  def props: Props = Props(new TestResponseAdapter())
}

class TestResponseAdapter extends BaseResponseAdapter[TestGameContext] {
  val id = "testResponseAdapter"

  def process(flow: Flow[TestGameContext]): Option[JsValue] = None
}