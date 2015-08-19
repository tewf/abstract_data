package com.nitro.absnlp

import scala.language.implicitConversions
import scala.reflect.ClassTag

/** Wraps a Traversable as a Data. */
case class TravDataTrait[A](ls: Traversable[A]) extends DataTrait[A] {

  override def map[B: ClassTag](f: A => B): DataTrait[B] =
    TravDataTrait(ls.map(f))

  override def mapParition[B: ClassTag](f: Iterator[A] => Iterator[B]): DataTrait[B] =
    TravDataTrait(f(ls.toIterator).toTraversable)

  override def foreach(f: A => Any): Unit =
    ls.foreach(f)

  override def foreachPartition(f: Iterator[A] => Any): Unit = {
    val _ = f(ls.toIterator)
  }

  override def filter(f: A => Boolean): DataTrait[A] =
    TravDataTrait(ls.filter(f))

  override def aggregate[B: ClassTag](zero: B)(seqOp: (B, A) => B, combOp: (B, B) => B): B =
    ls.aggregate(zero)(seqOp, combOp)

  override def sortBy[B: ClassTag](f: (A) ⇒ B)(implicit ord: math.Ordering[B]): DataTrait[A] =
    TravDataTrait(ls.toSeq.sortBy(f))

  override def take(k: Int): Traversable[A] =
    ls.take(k)

  override def headOption: Option[A] =
    ls.headOption

  override def toSeq: Seq[A] =
    ls.toSeq

  override def flatMap[B: ClassTag](f: A => TraversableOnce[B]): DataTrait[B] =
    TravDataTrait(ls.flatMap(f))

  override def groupBy[B: ClassTag](f: A => B): DataTrait[(B, Iterable[A])] =
    TravDataTrait(
      ls
        .groupBy(f)
        .toTraversable
        .map {
          case (b, iter) => (b, iter.toIterable)
        }
    )

  override def reduce[A1 >: A: ClassTag](r: (A1, A1) => A1): A1 =
    ls.reduce(r)

  override def reduceLeft(r: (A, A) => A): A =
    ls.reduce(r)

  override def toMap[T, U](implicit ev: A <:< (T, U)): Map[T, U] =
    ls.toMap

  override def size: Long =
    ls.size

  override def isEmpty: Boolean =
    ls.isEmpty

  override def sum[N >: A](implicit num: Numeric[N]): N =
    ls.sum(num)

  override def zip[A1 >: A: ClassTag, B: ClassTag](that: DataTrait[B]): DataTrait[(A1, B)] =
    that match {

      case TravDataTrait(thatLs) =>
        TravDataTrait(ls.toIterable.zip(thatLs.toIterable).toTraversable)

      case other =>
        other
          .zip(TravDataTrait(ls.map(_.asInstanceOf[A1])))(implicitly[ClassTag[B]], implicitly[ClassTag[A1]])
          .map { case (b, a1) => (a1, b) }
    }

  override def zipWithIndex: DataTrait[(A, Long)] =
    TravDataTrait(ls.toIndexedSeq.zipWithIndex.map(a => (a._1, a._2.toLong)))

}

object TravDataTrait {

  object Implicits {

    implicit def traversable2data[A](t: Traversable[A]): DataTrait[A] =
      TravDataTrait(t)

    implicit def seq2data[A](s: Seq[A]): DataTrait[A] =
      s.toTraversable

    implicit def array2Data[A](a: Array[A]): DataTrait[A] =
      a.toTraversable
  }

}