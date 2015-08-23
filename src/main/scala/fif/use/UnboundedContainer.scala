package fif.use

import scala.language.postfixOps

trait UnboundedContainer[A] extends Container[A] {

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
      case (pq, aItem) =>
        module.insert(aItem)(pq)
    }

  def insert[A](module: UnboundedContainer[A], elements: Iterable[A]): module.Structure =
    insert(module)(module.empty, elements)

}

