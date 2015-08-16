package com.nitro.absnlp

import scala.language.implicitConversions
import scala.reflect.ClassTag

/** Wraps a Traversable as a Data. */
case class TravData[A](ls: Traversable[A]) extends Data[A]  {

  override def map[B: ClassTag](f: A => B): Data[B] =
    TravData(ls.map(f))

  override def mapParition[B: ClassTag](f: Iterable[A] => Iterable[B]): Data[B] =
    TravData(f(ls.toIterable).toTraversable)

  override def foreach(f: A => Any): Unit =
    ls.foreach(f)

  override def foreachPartition(f: Iterable[A] => Any): Unit = {
    val _ = f(ls.toIterable)
  }

  override def filter(f: A => Boolean): Data[A] =
    TravData(ls.filter(f))

  override def aggregate[B: ClassTag](zero: B)(seqOp: (B, A) => B, combOp: (B, B) => B): B =
    ls.aggregate(zero)(seqOp, combOp)

  override def sortBy[B: ClassTag](f: (A) ⇒ B)(implicit ord: math.Ordering[B]): Data[A] =
    TravData(ls.toSeq.sortBy(f))

  override def take(k: Int): Traversable[A] =
    ls.take(k)

  override def headOption: Option[A] =
    ls.headOption

  override def toSeq: Seq[A] =
    ls.toSeq

  override def flatMap[B: ClassTag](f: A => TraversableOnce[B]): Data[B] =
    TravData(ls.flatMap(f))

  override def groupBy[B: ClassTag](f: A => B): Data[(B, Iterable[A])] =
    TravData(
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

  override def zip[A1 >: A: ClassTag, B: ClassTag](that: Data[B]): Data[(A1, B)] =
    that match {

      case TravData(thatLs) =>
        TravData(ls.toIterable.zip(thatLs.toIterable).toTraversable)

      case other =>
        other
          .zip(TravData(ls.map(_.asInstanceOf[A1])))(implicitly[ClassTag[B]], implicitly[ClassTag[A1]])
          .map { case (b, a1) => (a1, b) }
    }

  override def zipWithIndex: Data[(A, Long)] =
    TravData(ls.toIndexedSeq.zipWithIndex.map(a => (a._1, a._2.toLong)))

}

object TravData {

  object Implicits {

    implicit def traversable2data[A](t: Traversable[A]): Data[A] =
      TravData(t)

    implicit def seq2data[A](s: Seq[A]): Data[A] =
      s.toTraversable

    implicit def array2Data[A](a: Array[A]): Data[A] =
      a.toTraversable
  }

}