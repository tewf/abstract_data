package fif.use

import scala.language.postfixOps

trait Container[A] extends Serializable {

  type Structure

  def empty: Structure

}