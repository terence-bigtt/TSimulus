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

package be.cetic.rtsgen.generators.composite

import be.cetic.rtsgen.config.Model
import be.cetic.rtsgen.generators.Generator
import be.cetic.rtsgen.timeseries.composite.CorrelatedTimeSeries

import scala.util.Random

/**
  * A generator for [[be.cetic.rtsgen.timeseries.composite.CorrelatedTimeSeries]].
  *
  * The values of the generated time series are correlated to those of the original time series.
  */
class CorrelatedGenerator(name: Option[String],
                          val generator: Either[String, Generator[Any]],
                          val coef: Double) extends Generator[Double](name, "correlated")
{
   override def timeseries(generators: (String) => Generator[Any]) =
   {
      Model.generator(generators)(generator) match {
         case dDouble : Generator[Double] => CorrelatedTimeSeries(dDouble.timeseries(generators), Random.nextInt(), coef)
         case _ => throw new ClassCastException
      }
   }

   override def toString = "CorrelatedGenerator(" + name + "," + generator + "," + "coef" + ")"

   override def equals(o: Any) = o match {
      case that: CorrelatedGenerator => that.name == this.name && that.generator == this.generator && that.coef == this.coef
      case _ => false
   }
}