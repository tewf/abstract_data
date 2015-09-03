abstract_data
============
[![Build Status](https://travis-ci.org/malcolmgreaves/abstract_data.svg?branch=master)](https://travis-ci.org/malcolmgreaves/abstract_data) [![Coverage Status](https://coveralls.io/repos/malcolmgreaves/abstract_data/badge.svg?branch=master&service=github)](https://coveralls.io/github/malcolmgreaves/abstract_data?branch=master)
 [![Codacy Badge](http://api.codacy.com:80/project/badge/7a4fbaf2cbe6449993224d6eb4df0f13)](https://www.codacy.com/app/greavesmalcolm/abstract_data) [![Stories in Ready](https://badge.waffle.io/malcolmgreaves/abstract_data.png?label=ready&title=Ready)](https://waffle.io/malcolmgreaves/abstract_data)  [![Join the chat at https://gitter.im/malcolmgreaves/abstract_data](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/malcolmgreaves/abstract_data?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

A unifying typeclass describing collections and higher-order data transformation and manipulation actions common to a wide variety of data processing tasks. Inspired by the Scala collections API.

### Why Use `abstract_data`?

Write an algorithm that accepts a type that adheres to the [Data](https://github.com/malcolmgreaves/abstract_data/blob/master/src/main/scala/fif/Data.scala) type class and watch it work everywhere! Data describes higher-order-functions that manipulate and transform generic collections. It makes use of the [type class](https://en.wikipedia.org/wiki/Type_class) design pattern for ad-hoc polymorphism (instead of the common inheritence-based sub-typing polymorphism). This is a powerful and incredibly flexible mechanism for describing generic behaviors: it is akin to static duck typing.

Implementations for concrete types include:
* [Traversable](https://github.com/malcolmgreaves/abstract_data/blob/master/src/main/scala/fif/TravData.scala)
* [Spark RDD](https://github.com/malcolmgreaves/sparkmod)
* [Flink DataSet](https://github.com/malcolmgreaves/flinkmod)

### Installation

### Examples

We strive for high code coverage. Check out all of the [tests](https://github.com/malcolmgreaves/abstract_data/tree/master/src/test/scala/fif).

For a rather small use case, check out [Sum](), which shows how to implement the common `sum` functionality on a `Data` type class instance:

    object Sum extends Serializable {
    
      // Brings implicits in scope for things like `map`, `flatMap`, etc.
      // as object oriented style infix notation. These are still using
      // the type class method definitions!
      import DataOps.infix._
      
      def apply[N: Numeric: ClassTag, D[_]: Data](data: D[N]): N = {
        val add = implicitly[Numeric[N]].plus _
        data.aggregate(implicitly[Numeric[N]].zero)(add, add)
      }

      def apply[N: Numeric](first: N, numbers: N*): N = {
        val add = implicitly[Numeric[N]].plus _
        numbers.foldLeft(first)(add)
      }
    }

### Contributing
We <3 contributions! We want this code to be useful and used! We use pull requests to review and discuss changes, fixes, and improvements.

### License

Copyright 2015 Malcolm Greaves

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
