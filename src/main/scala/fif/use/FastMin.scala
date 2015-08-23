package fif.use

trait FastMin[A] extends Container[A] {

  def peekMin(existing: Structure): Option[A]

  def takeMin(existing: Structure): Option[(A, Structure)]

}