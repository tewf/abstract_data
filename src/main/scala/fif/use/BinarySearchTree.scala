package fif.use

abstract class BinarySearchTree[A: Cmp] extends TreeLikeContainer[A] {

  def merge(a: Structure, b: Structure): (Structure, Option[Iterable[A]])

  def insert(item: A)(existing: Structure): (Structure, Option[A])

  def sort(existing: Structure): Iterable[A]

  def delete(item: A)(existing: Structure): Option[Structure]

  def contains(item: A)(existing: Structure): Boolean

}

case object BinarySearchTree {

  def apply[A: Cmp]: BinarySearchTree[A] =
    new BinarySearchTree[A] {

      private val compare = implicitly[Cmp[A]].compare _

      override def contains(item: A)(existing: Structure): Boolean =
        existing match {

          case Empty =>
            false

          case Full(left, treeItem, right) =>
            compare(item, treeItem) == Equivalent || contains(item)(left) || contains(item)(right)
        }

      override def insert(item: A)(existing: Structure): (Structure, Option[A]) =
        existing match {
          case Empty =>
            (Full(Empty, item, Empty), None)

          case Full(left, treeItem, right) =>
            compare(item, treeItem) match {

              case Less                 => ???

              case Greater | Equivalent => ???
              // Greater means that we must put

              case Equivalent           => ???

            }

        }

      override def merge(a: Structure, b: Structure): (Structure, Option[Iterable[A]]) = ???

      override def sort(existing: Structure): Iterable[A] = ???

      override def delete(item: A)(existing: Structure): Option[Structure] = ???

    }

}