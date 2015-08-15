package com.nitro.absnlp

import org.scalatest.FunSuite

import scala.language.higherKinds
import scala.reflect.ClassTag

/**
 * Trait that abstractly represents operations that can be performed on a dataset.
 * The implementation of Data is suitable for both large-scale, distributed data
 * or in-memory structures.
 */
class DataTypeclassTest extends FunSuite {

  import DataTypeclass.ops._
  implicit val t = TravDataTypeclass

  test("test map"){

    val data = Seq(1,2,3).toTraversable

    val changed = addElementwise10(data)

    assert(changed != data)
    assert(changed == Seq(11, 12, 13))
  }

  def addElementwise10[D[_] : DataTypeclass](data: D[Int]): D[Int] =
    data.map(_ + 10)

}