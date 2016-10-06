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

package be.cetic.rtsgen.generators.primary

import be.cetic.rtsgen.generators.Generator
import be.cetic.rtsgen.timeseries.primary.DailyTimeSeries
import org.joda.time.LocalTime

/**
  * A generator for [[be.cetic.rtsgen.timeseries.primary.DailyTimeSeries]].
  */
class DailyGenerator(name: Option[String],
                     val points: Map[LocalTime, Double]) extends Generator[Double](name, "daily")
{
   override def timeseries(generators: String => Generator[Any]) = DailyTimeSeries(points)

   override def toString = "DailyGenerator(" + name + "," + points + ")"

   override def equals(o: Any) = o match {
      case that: DailyGenerator => that.name == this.name && that.points == this.points
      case _ => false
   }
}