package fif.use

trait FastMin[A, S] extends Container[A, S] {

  def peekMin(existing: Structure): Option[A]

  def takeMin(existing: Structure): Option[(A, Structure)]

}