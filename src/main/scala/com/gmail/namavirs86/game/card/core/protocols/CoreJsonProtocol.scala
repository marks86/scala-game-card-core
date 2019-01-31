package com.gmail.namavirs86.game.card.core.protocols

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.gmail.namavirs86.game.card.core.Definitions.{RequestContext, RequestType}
import com.gmail.namavirs86.game.card.core.Definitions.RequestType.RequestType
import spray.json._

trait CoreJsonProtocol extends SprayJsonSupport {

  import DefaultJsonProtocol._

  implicit object requestTypeFormat extends JsonFormat[RequestType] {
    val string2RequestType: Map[JsValue, RequestType] =
      RequestType.requestTypes.map(r => (JsString(r.toString), r)).toMap

    override def read(json: JsValue): RequestType = {
      string2RequestType.getOrElse(json, throw DeserializationException("Request type expected"))
    }

    override def write(obj: RequestType): JsValue = {
      JsString(obj.toString)
    }
  }

  implicit val gamePlayRequestFormat: RootJsonFormat[RequestContext] = jsonFormat5(RequestContext)
}
