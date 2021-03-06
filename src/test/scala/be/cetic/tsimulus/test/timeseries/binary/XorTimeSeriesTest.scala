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

package be.cetic.tsimulus.test.timeseries.binary

import be.cetic.tsimulus.test.RTSTest
import be.cetic.tsimulus.timeseries.binary.XorTimeSeries
import org.scalatest.{FlatSpec, Inspectors, Matchers}

class XorTimeSeriesTest extends FlatSpec with Matchers with Inspectors with RTSTest
{
   "True XOR True" should "be False" in {
      forAll (new XorTimeSeries(t, t).compute(dates)) { result => result._2 shouldBe Some(false)}
   }

   "True XOR False" should "be True" in {
      forAll (new XorTimeSeries(t, f).compute(dates)) { result => result._2 shouldBe Some(true)}
   }

   "False XOR True" should "be True" in {
      forAll (new XorTimeSeries(f, t).compute(dates)) { result => result._2 shouldBe Some(true)}
   }

   "False XOR False" should "be False" in {
      forAll (new XorTimeSeries(f, f).compute(dates)) { result => result._2 shouldBe Some(false)}
   }

   "True XOR Undetermined" should "be Undetermined" in {
      forAll (new XorTimeSeries(t, u).compute(dates)) { result => result._2 shouldBe None}
   }

   "False XOR Undetermined" should "be Undetermined" in {
      forAll (new XorTimeSeries(f, u).compute(dates)) { result => result._2 shouldBe None}
   }

   "Undetermined XOR True" should "be Undetermined" in {
      forAll (new XorTimeSeries(u, t).compute(dates)) { result => result._2 shouldBe None}
   }

   "Undetermined XOR False" should "be Undetermined" in {
      forAll (new XorTimeSeries(u, f).compute(dates)) { result => result._2 shouldBe None}
   }

   "Undetermined XOR Undetermined" should "be Undetermined" in {
      forAll (new XorTimeSeries(u, u).compute(dates)) { result => result._2 shouldBe None}
   }
}
