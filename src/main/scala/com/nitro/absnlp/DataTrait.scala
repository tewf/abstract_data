package com.nitro.absnlp

import scala.reflect.ClassTag

/**
 * Trait that abstractly represents operations that can be performed on a dataset.
 * The implementation of Data is suitable for both large-scale, distributed data
 * or in-memory structures.
 */
trait DataTrait[A] {

  /** Transform a dataset by applying f to each element. */
  def map[B: ClassTag](f: A => B): DataTrait[B]

  def mapParition[B: ClassTag](f: Iterator[A] => Iterator[B]): DataTrait[B]

  /** Apply a side-effecting function to each element. */
  def foreach(f: A => Any): Unit

  def foreachPartition(f: Iterator[A] => Any): Unit

  def filter(f: A => Boolean): DataTrait[A]

  /**
   * Starting from a defined zero value, perform an operation seqOp on each element
   * of a dataset. Combine results of seqOp using combOp for a final value.
   */
  def aggregate[B: ClassTag](zero: B)(seqOp: (B, A) => B, combOp: (B, B) => B): B

  /** Sort the dataset using a function f that evaluates each element to an orderable type */
  def sortBy[B: ClassTag](f: (A) => B)(implicit ord: math.Ordering[B]): DataTrait[A]

  /** Construct a traversable for the first k elements of a dataset. Will load into main mem. */
  def take(k: Int): Traversable[A]

  def headOption: Option[A]

  /** Load all elements of the dataset into an array in main memory. */
  def toSeq: Seq[A]
  //    override def zip[A1 >: A, B
  def flatMap[B: ClassTag](f: A => TraversableOnce[B]): DataTrait[B]

  def groupBy[B: ClassTag](f: A => B): DataTrait[(B, Iterable[A])]

  def as: DataTrait[A] = this

  def reduce[A1 >: A: ClassTag](r: (A1, A1) => A1): A1

  /** This has type A as opposed to B >: A due to the RDD limitations */
  def reduceLeft(op: (A, A) => A): A

  def toMap[T, U](implicit ev: A <:< (T, U)): Map[T, U]

  def size: Long

  def isEmpty: Boolean

  def sum[N >: A](implicit num: Numeric[N]): N

  def zip[A1 >: A: ClassTag, B: ClassTag](that: DataTrait[B]): DataTrait[(A1, B)]

  def zipWithIndex: DataTrait[(A, Long)]

}