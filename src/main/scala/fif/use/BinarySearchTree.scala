package fif.use

abstract class BinarySearchTree[A: Cmp]
  extends SortableContainer[A, TreeParts.Tree[A]]
  with TreeLikeContainer[A]
  with SearchableContainer[A, TreeParts.Tree[A]]

//case object BinarySearchTree {
//
//  def apply[A: Cmp]: BinarySearchTree[A] =
//    new BinarySearchTree[A] {
//
//      private val compare = implicitly[Cmp[A]].compare _
//
//      override def contains(item: A)(existing: Structure): Boolean =
//        existing match {
//
//          case Empty =>
//            false
//
//          case Full(left, treeItem, right) =>
//            compare(item, treeItem) == Equivalent || contains(item)(left) || contains(item)(right)
//        }
//
//      override def insert(item: A)(existing: Structure): (Structure, Option[A]) =
//        existing match {
//          case Empty =>
//            (Full(Empty, item, Empty), None)
//
//          case Full(left, treeItem, right) =>
//            compare(item, treeItem) match {
//
//              case Less                 => ???
//
//              case Greater | Equivalent => ???
//              // Greater means that we must put
//
//            }
//
//        }
//
//      override def merge(a: Structure, b: Structure): (Structure, Option[Iterable[A]]) = ???
//
//      override def sort(existing: Structure): Iterable[A] = ???
//
//      override def delete(item: A)(existing: Structure): Option[Structure] = ???
//
//    }
//
//}