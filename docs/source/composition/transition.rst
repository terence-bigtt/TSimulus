Transition
----------

A transition time series may be used for representing a behavior that fluctuates over time.
More precisely, the time series produced by this generator mimic a baseline time series until a given time, after which they mimic
an other baseline time series.

A transition period can be specified. During this period, the values generated by the transition time series
are defined as the interpolation of the values generated by the two baseline time series.
In other words, the generated values progressively switch from those generated by the first baseline time series
to those generated by the second baseline time series.

Three interpolation functions are provided for describing how the transition values must be computed:

* **linear**: a function that provides a linear transition between the considered time series.
* **sigmoid**: a function that provides a S-shaped, smoothly transition from a time series to the other one.
  The variation of the interpolation is mainly observable at the middle of the transition, while the interpolation
  is almost constant at the beginning and the end of the transition.
* **exponential**: a function that gives the second time series an importance that increases as
  the exponential of the transition progress.

**Representation in the configuration document:**

name
    The name given to the generator describing a time series.
    This name must be unique among all generators in the configuration document.

type
    Mandatory. Must be ‘transition’.

first
    Mandatory. The generator describing the baseline time series that is mimicked before the transition.

second
    Mandatory. The generator describing the baseline time series that is mimicked after the transition.

time
    Mandatory. The moment at which the transition starts.

duration
    Optional. The transition duration, in milliseconds. If not specified, the transition is immediate.

transition
    Optional. The interpolation function to use for the transition. If defined, must be either
    'linear', 'sigmoid', or 'exponential'. If 'duration' is not specified, this parameter has no effects.
    Default is 'linear'.

**Example**::

    {
      "name": "transition-generator",
      "type": "transition",
      "first": "daily-generator",
      "second": "noise-generator",
      "time": "2016-04-03 12 :34 :56 .000",
      "duration": 300000,
      "transition" : "linear"
    }

