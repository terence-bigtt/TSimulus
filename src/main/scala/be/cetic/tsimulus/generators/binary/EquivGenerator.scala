/*
 * Copyright 2106 Cetic ASBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package be.cetic.tsimulus.generators.binary

import be.cetic.tsimulus.config.{GeneratorFormat, Model}
import be.cetic.tsimulus.generators.Generator
import be.cetic.tsimulus.timeseries.TimeSeries
import be.cetic.tsimulus.timeseries.binary.{AndTimeSeries, EquivTimeSeries}
import spray.json.{JsObject, JsString, JsValue, _}

/**
  * A generator for [[be.cetic.tsimulus.timeseries.binary.EquivTimeSeries]].
  */
class EquivGenerator(name: Option[String],
                   val a: Either[String, Generator[Any]],
                   val b: Either[String, Generator[Any]]) extends Generator[Any](name, "Equiv")
{
   override def timeseries(generators: (String) => Generator[Any]) =
   {
      val first = Model.generator(generators)(a).timeseries(generators) match {
         case t: TimeSeries[Boolean] => t
      }

      val second = Model.generator(generators)(b).timeseries(generators) match {
         case t: TimeSeries[Boolean] => t
      }

      new EquivTimeSeries(first, second)
   }

   override def toString = "Equiv(" + name + ", " + a + ", " + b + ")"

   override def equals(o: Any) = o match {
      case that: EquivGenerator => that.name == this.name &&
         that.a == this.a &&
         that.b == this.b
      case _ => false
   }

   override def toJson: JsValue = {
      val _a = a match
      {
         case Left(s) => s.toJson
         case Right(g) => g.toJson
      }

      val _b = b match
      {
         case Left(s) => s.toJson
         case Right(g) => g.toJson
      }

      var t = Map(
         "a" -> _a,
         "b" -> _b,
         "type" -> `type`.toJson
      )

      if(name.isDefined) t = t.updated("name", name.toJson)

      new JsObject(t)
   }
}

object EquivGenerator extends DefaultJsonProtocol
{
   def apply(value: JsValue): EquivGenerator = {
      val fields = value.asJsObject.fields

      val name = fields.get("name").map(_.convertTo[String])

      val a = fields("a") match {
         case JsString(s) => Left(s)
         case g => Right(GeneratorFormat.read(g))
      }

      val b = fields("b") match {
         case JsString(s) => Left(s)
         case g => Right(GeneratorFormat.read(g))
      }

      new EquivGenerator(name, a, b)
   }
}
