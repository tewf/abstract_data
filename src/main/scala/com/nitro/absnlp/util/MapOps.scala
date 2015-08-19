package com.nitro.absnlp.util


/**
 * Common operations performed on functional Map data structures
 * that have Numeric values and generic keys.
 */
object MapOps {

  /**
   * Updates the value of {{{key}}} in Map {{{m}}} by {{{value}}}. If {{{key}}}
   * is not contained in {{{m}}}, then it is put into the map.
   */
  @inline def increment[A, N](m: Map[A, N], key: A, value: N)(implicit n: Numeric[N]): Map[A, N] =
    m.get(key) match {

      case Some(existing) =>
        (m - key) + ((key, n.plus(existing, value)))

      case None =>
        m + ((key, value))
    }

  /**
   * Combines the key-value pairs in both {{{m1}}} and {{{m2}}}. For all keys
   * in the intersection of these two Maps, the resulting value will be the
   * sum of the values in the two Maps.
   */
  @inline def combine[A, N: Numeric](m1: Map[A, N], m2: Map[A, N]): Map[A, N] = {
    val (larger, smaller) =
      if (m1.size > m2.size)
        (m1, m2)
      else
        (m2, m1)

    aggregate(larger, smaller.toSeq)
  }

  /**
   * Applies combine(Map,Map) to a sequence of Maps. If the input sequence maps
   * is empty, than an empty Map is returned.
   */
  @inline def combine[A, N: Numeric](maps: Seq[Map[A, N]]): Map[A, N] =
    maps.size match {
      case 0 =>
        Map.empty[A, N]

      case 1 =>
        maps.head

      case n =>
        maps.slice(1, n)
          .foldLeft(maps.head) {
            case (m1, m2) => combine(m1, m2)
          }
    }

  /**
   * Converts the (key,value) pairs into a mapping. Elements with the same key
   * will have their values summed.
   */
  @inline def aggregate[A, N: Numeric](elements: Seq[(A, N)]): Map[A, N] =
    aggregate(Map.empty[A, N], elements)

  /**
   * Converts the (key,value) pairs into a mapping. Elements with the same key
   * will have their values summed.
   *
   * Starts from an initial mapping, instead of an empty one.
   */
  @inline def aggregate[A, N: Numeric](initial: Map[A, N], elements: Seq[(A, N)]): Map[A, N] =
    elements
      .foldLeft(initial)({
        case (m, (feature, value)) =>
          increment(m, feature, value)
      })

}
