package com.nitro.absnlp

import scala.language.higherKinds
import scala.reflect.ClassTag

import simulacrum._

/**
 * Trait that abstractly represents operations that can be performed on a dataset.
 * The implementation of Data is suitable for both large-scale, distributed data
 * or in-memory structures.
 */
@typeclass trait DataTypeclass[D[_]] {

  /** Transform a dataset by applying f to each element. */
  def map[A, B: ClassTag](data: D[A])(f: A => B): D[B]

}

case object TravDataTypeclass extends DataTypeclass[Traversable] {

  /** Transform a dataset by applying f to each element. */
  override def map[A, B: ClassTag](data: Traversable[A])(f: (A) => B): Traversable[B] =
    data.map(f)

}