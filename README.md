abstract_ml
============

Lightweight types, traits, and other abstractions for interacting with, learning from, and infering the meaning of data. This library casts machine learning concepts and algorithms using the core tennents of functinoal programming: strong, expressive types guding function compositions to create rich, correct programs.

Implementations of machine learnings algorithms are as generic as possible. At a high level, all actions, data transformations, model learning or prediction, etc. are guided by descriptive types. Nearly all actions are represented as functions: type aliases are judiciously used in order to categorize and name the variety of machine learning programs.

Use Cases
=========

NOTE: Assume this import below: com.nitro.absml._

* You want to provide a clean, simple, intuitive interface for a learning algorithm you've developed. You want to use the types in the `Learning` module.

`Learning` is a trait that supplies many type definitions that describe the behavior of machine learning (ML) algorithms. The generic type parameters in `Learning[_, _]` specify the `Item` and `Label` types, respectively. The `Classifier` function type yields a `Label` given an `Item`. The `Estimator` type yields a `Distribution` over `Label`s given an `Item`.


* Looking to understand the components of the `Learning` module more? Dive into `Distribution` and `Argmax`.

`Distribution` is a trait for describing a probability distribution over a set of items. It's generic, so the distribution can range over any specific type. There are, however, only two concrete instantiations: a continuous or discrete distribution. Continuous distributions are parameterized by a probability density function only. Discrete distributions have an item set with corresponding probabilities.

`Argmax` is an object whose `apply` method will find the maximal argument in a given list. `Argmax.apply` accepts a generic sequence of items, with the constraint that there must be implicit evidence for each argument's value. 

The `Val` typeclass provides this evidence. There are pre-defined versions that work with `(Double, T)` and `(T, Double)` for a generic type `T`.


Contributing
============
We <3 contributions! We want this code to be useful and used! We use pull requests to review and discuss changes, fixes, and improvements.

To kick off a contribution, fork this repo, make some changes on master, then submit a PR and discuss the changes. Once a consensus is reached, close the PR and manually merge onto this repository's master. 

** MAKE SURE ** you only have one commit per feature / PR. Achieve this by either doing a `git rebase` before merging or a `git merge --sqash`. The commit message should be detailed and explain everything that the commit introduces and removes.

Also, if you contriubte, add yourself to the developers list within `build.sbt`.
