package be.cetic.tsgen.timeseries.composite

import be.cetic.tsgen.timeseries.TimeSeries
import org.joda.time.LocalDateTime

/**
  * A time series based on an underlying time series. The values of the underlying time series
  * are forwarded by this time series iff a binary value from an other time series is true.
  *
  * If the value of the underlying time series is not defined, then the value of this time series is not defined.
  * If the value of the binary time series is not defined, then the value of this time series is not defined.
  *
  * @param condition the binary time series used for determining if the values of
  *               the underlying time series must be forwarded.
  * @param success the time series used when the condition is verified.
  * @param failure the time series used when the condition is not verified.

  */
case class ConditionalTimeSeries[T](condition: TimeSeries[Boolean], success: TimeSeries[T], failure: TimeSeries[T]) extends TimeSeries[T]
{
   override def compute(times: Stream[LocalDateTime]) =
   {
      (condition.compute(times), success.compute(times), failure.compute(times)).zipped.map { (c,s,f) => {
         assert(c._1 == s._1)
         assert(c._1 == f._1)

         val time = c._1

         c._2 match {
            case Some(true) => (time, s._2)
            case Some(false) => (time, f._2)
            case _ => (time, None)
         }
      }}
   }
}
