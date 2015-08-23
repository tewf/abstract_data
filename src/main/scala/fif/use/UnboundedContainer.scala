package fif.use

import scala.language.postfixOps

trait UnboundedContainer[A] extends SortableContainer[A] {

  def merge(a: Structure, b: Structure): Structure

  def insert(item: A)(existing: Structure): Structure

}

object UnboundedContainer {

  def merge[A](module: UnboundedContainer[A])(
    initial:    module.Structure,
    structures: module.Structure*
  ): module.Structure =
    structures.foldLeft(initial) {
      case (merging, next) =>
        module.merge(merging, next)
    }

  def insert[A](module: UnboundedContainer[A])(
    existing: module.Structure,
    elements: Iterable[A]
  ): module.Structure =
    elements.foldLeft(existing) {
      case ((pq, removing), aItem) =>
        module.insert(aItem)(pq)
    }

  def insert[A](module: UnboundedContainer[A], elements: Iterable[A]): module.Structure =
    insert(module)(module.empty, elements)

}

