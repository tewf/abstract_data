package fif.use

import org.scalatest.FunSuite

import scala.annotation.tailrec
import scala.language.postfixOps
import scala.util.Random

class BinarySearchTreeTest extends FunSuite {

  private val rand = new Random()

  ignore("simple cases: sort empty sequence, sequence of 1, sequence of 3") {
    val sequencesToSort = Seq(
      Seq.empty[Int],
      Seq(1),
      Seq(3, 1, 2)
    )
    val expected = Seq(
      Seq.empty[Int],
      Seq(1),
      Seq(1, 2, 3)
    )
    ???
  }

  val nElementsForRand = 1000

  ignore(s"sort list of $nElementsForRand numbers drawn uniformly at random"){
    val elements = (0 until nElementsForRand).map(_ => rand.nextDouble() * 100).toSeq
    val expected = elements.sorted
    ???
  }

  val randStrLen = 20

  ignore(s"sort list of $nElementsForRand strings length $randStrLen drawn uniformly at random") {
    val elements = (0 until nElementsForRand).map(_ => rand.nextString(randStrLen)).toSeq
    val expected = elements.sorted
    ???
  }



}