package fif.use

import scala.language.postfixOps

trait SortableContainer[A] extends Container[A] {

  val cmp: Cmp[A]

  def sort(existing: Structure): Iterable[A]

  def delete(item: A)(existing: Structure): Option[Structure]

}

object SortableContainer {

  def delete[A](module: SortableContainer[A])(
    existing: module.Structure,
    elements: Iterable[A]
  ): Option[module.Structure] = {

    val (elementsNotInThisStructure, removedAny) =
      elements.foldLeft((existing, false)) {
        case ((removing, check), item) =>
          module.delete(item)(removing) match {

            case Some(removedFrom) =>
              (removedFrom, true)

            case None =>
              (removing, check)

          }
      }

    if (removedAny)
      Some(elementsNotInThisStructure)
    else
      None
  }

}