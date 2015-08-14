package com.nitro.absnlp.util

import org.scalatest.FunSuite

class MapOpsTest extends FunSuite {

  import MapOpsTest._

  test("word count w/ map ops") {
    assert(wordCount(words) == expectedMap)
  }

  test("multi word count w/ map ops") {
    val n = 10
    val wc = MapOps.combine((0 until n).map(_ => wordCount(words)))
    val timesN = expectedMap.toSeq.map(x => (x._1, x._2 * n)).toMap
    assert(wc == timesN)
  }

  test("size 0 ok") {
    assert(Map.empty[String, Int] == MapOps.combine(Seq.empty[Map[String, Int]]))
  }

  test("size 1 ok") {
    assert(expectedMap == MapOps.combine(Seq(expectedMap)))
  }

}

object MapOpsTest {

  val words = "hello world how how how now now brown brown brown brown cow"

  val expectedMap = Map(
    "hello" -> 1,
    "world" -> 1,
    "how" -> 3,
    "now" -> 2,
    "brown" -> 4,
    "cow" -> 1
  )

  def wordCount(s: String): Map[String, Int] =
    s.split(" ")
      .map(_.toLowerCase)
      .foldLeft(Map.empty[String, Int]) {
        case (m, word) =>
          MapOps.increment(m, word, 1)
      }

}