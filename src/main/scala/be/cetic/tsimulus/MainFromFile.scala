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

package be.cetic.tsimulus

import java.io.File

import be.cetic.tsimulus.config.Configuration
import com.github.nscala_time.time.Imports._
import spray.json._

import scala.io.Source

object MainFromFile
{
   def main(args: Array[String]): Unit =
   {
      val content = Source .fromFile(new File(args(0)))
                           .getLines()
                           .mkString("\n")

      val dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss.SSS")

      val config = Configuration(content.parseJson)

      println("date;series;value")

      Utils.generate(Utils.config2Results(config)) foreach (e => println(dtf.print(e._1) + ";" + e._2 + ";" + e._3))
   }
}
