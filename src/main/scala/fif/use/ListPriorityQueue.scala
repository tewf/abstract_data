package fif.use

import algebra.Eq

import scala.annotation.tailrec

object ListPriorityQueue {

  def apply[A: Cmp: Eq](maximumHeapSize: Int): PriorityQueue[A, List[A]] =
    new PriorityQueue[A, List[A]] {

      override val maxSize = math.max(0, maximumHeapSize)
      // we unpack here to use it internally, if applicable
      private val isMaxSizeDefined = true
      private val maxSizeIfDefined = maxSize

      override type Structure = List[A]

      override val empty: Structure =
        List.empty[A]

      override def peekMin(existing: Structure): Option[A] =
        existing.headOption

      override val cmp = implicitly[Cmp[A]]

      private val sortFn =
        (a: A, b: A) => cmp.compare(a, b) == Less

      private val equality =
        implicitly[Eq[A]].eqv _

      private def contains(item: A, existing: Structure): Boolean =
        existing.exists(x => equality(x, item))

      override def insert(item: A)(existing: Structure): (Structure, Option[A]) =
        if (contains(item, existing))
          (existing, None)

        else {
          val newList = (existing :+ item).sortWith(sortFn)
          if (isMaxSizeDefined && newList.size > maxSizeIfDefined) {
            val end = math.min(newList.size, maxSizeIfDefined)
            (newList.slice(0, end), Some(newList(end)))

          } else
            (newList, None)
        }

      override def merge(one: Structure, two: Structure): (Structure, Option[Iterable[A]]) =
        if (one.isEmpty && two.isEmpty)
          (empty, None)

        else {
          val (smaller, larger) =
            if (one.size > two.size) (one, two)
            else (two, one)

          BoundedContainer.insert(this)(larger, smaller.toIterable)
        }

      override def takeMin(existing: List[A]): Option[(A, Structure)] =
        existing.headOption
          .map { head =>
            (head, existing.slice(1, existing.size))
          }

      override def sort(existing: Structure): Iterable[A] =
        existing.sortWith(sortFn)

      override def delete(item: A)(existing: Structure): Option[Structure] = {
        val (itemNotPresent, someChange) = delete_h(item, existing)
        if (someChange)
          Some(itemNotPresent)
        else
          None
      }

      /**
       * ASSUMPTION
       *  -- Parameter deletedAny has default value false.
       */
      private def delete_h(item: A, existing: Structure, deletedAny: Boolean = false): (Structure, Boolean) =
        existing match {

          case first :: rest =>
            if (equality(item, first)) {
              (delete_h(item, rest)._1, true)

            } else {
              val (continue, changed) = delete_h(item, rest, deletedAny)
              (continue, changed || deletedAny)
            }

          case Nil =>
            (Nil, deletedAny)
        }
    }

}
