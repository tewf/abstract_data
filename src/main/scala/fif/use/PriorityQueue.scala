package fif.use

import algebra.Eq

/**
 * Removes duplicates according to Eq[A]. Orders on priority according to Cmp[A]
 */
abstract class PriorityQueue[A: Cmp: Eq, S]
  extends SortableContainer[A, S]
  with FastMin[A, S]
  with BoundedContainer[A, S]