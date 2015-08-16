package com.nitro.absnlp



import scala.reflect.ClassTag
import scala.util.Random

/**
 * Trait that abstractly represents operations that can be performed on a dataset.
 * The implementation of Data is suitable for both large-scale, distributed data
 * or in-memory structures.
 */
trait Data[A] {

  /** Transform a dataset by applying f to each element. */
  def map[B: ClassTag](f: A => B): Data[B]

  def mapParition[B: ClassTag](f: Iterable[A] => Iterable[B]): Data[B]

  /** Apply a side-effecting function to each element. */
  def foreach(f: A => Any): Unit

  def foreachPartition(f: Iterable[A] => Any): Unit

  def filter(f: A => Boolean): Data[A]

  /**
   * Starting from a defined zero value, perform an operation seqOp on each element
   * of a dataset. Combine results of seqOp using combOp for a final value.
   */
  def aggregate[B: ClassTag](zero: B)(seqOp: (B, A) => B, combOp: (B, B) => B): B

  /** Sort the dataset using a function f that evaluates each element to an orderable type */
  def sortBy[B: ClassTag](f: (A) ⇒ B)(implicit ord: math.Ordering[B]): Data[A]

  /** Construct a traversable for the first k elements of a dataset. Will load into main mem. */
  def take(k: Int): Traversable[A]

  def headOption: Option[A]

  /** Load all elements of the dataset into an array in main memory. */
  def toSeq: Seq[A]
  //    override def zip[A1 >: A, B
  def flatMap[B: ClassTag](f: A => TraversableOnce[B]): Data[B]

  def flatten[B: ClassTag](implicit asEnumerator: A => Traversable[B]): Data[B] =
    flatMap(x => asEnumerator(x))

  def groupBy[B: ClassTag](f: A => B): Data[(B, Iterable[A])]

  def as: Data[A] = this

  def reduce[A1 >: A: ClassTag](r: (A1, A1) => A1): A1

  /** This has type A as opposed to B >: A due to the RDD limitations */
  def reduceLeft(op: (A, A) => A): A

  def toMap[T, U](implicit ev: A <:< (T, U)): Map[T, U]

  def size: Long

  def isEmpty: Boolean

  def sum[N >: A](implicit num: Numeric[N]): N

  def zip[A1 >: A: ClassTag, B: ClassTag](that: Data[B]): Data[(A1, B)]

  def zipWithIndex: Data[(A, Long)]

}

object Data {

  type RandomGenerator = () => Random

  def shuffle[A: ClassTag](data: Data[A])(implicit randgen: RandomGenerator): Data[A] =
    data
      .mapParition[A] { partition =>
        val r = randgen()
        r.setSeed(System.currentTimeMillis * 13l)
        partition
          .toSeq
          .sortBy(_ => r.nextInt())
      }

}