package fif.use

import scala.language.postfixOps

trait BoundedContainer[A] extends Container[A] {

  val maxSize: Int

  def merge(a: Structure, b: Structure): (Structure, Option[Iterable[A]])

  def insert(item: A)(existing: Structure): (Structure, Option[A])

}

object BoundedContainer {

  def merge[A](module: BoundedContainer[A])(
    initial:    module.Structure,
    structures: module.Structure*
  ): (module.Structure, Option[Iterable[A]]) = {

    val (finalMerged, kickedOut) =
      structures.foldLeft((initial, Seq.empty[A])) {
        case ((merging, removed), next) =>
          val (mergedNext, maybeRemvoed) = module.merge(merging, next)
          (
            mergedNext,
            maybeRemvoed match {
              case Some(rm) =>
                removed ++ rm

              case None =>
                removed
            }
          )

      }

    (finalMerged, if (kickedOut isEmpty) None else Some(kickedOut))

  }

  def insert[A](module: BoundedContainer[A])(
    existing: module.Structure,
    elements: Iterable[A]
  ): (module.Structure, Option[Iterable[A]]) = {

    val (newPq, kickedOut) =
      elements.foldLeft((existing, Seq.empty[A])) {
        case ((pq, removing), aItem) =>
          val (resulting, maybeRemoved) = module.insert(aItem)(pq)
          (
            resulting,
            maybeRemoved match {
              case None =>
                removing
              case Some(removed) =>
                removing :+ removed
            }
          )
      }

    (newPq, if (kickedOut isEmpty) None else Some(kickedOut))
  }

  def insert[A](module: BoundedContainer[A], elements: Iterable[A]): (module.Structure, Option[Iterable[A]]) =
    insert(module)(module.empty, elements)

}
