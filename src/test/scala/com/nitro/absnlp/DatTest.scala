//package com.nitro.absnlp
//
//import java.util.Random
//
//import org.scalatest.FunSuite
//
//import scala.language.postfixOps
//
//class DatTest extends FunSuite {
//
//  import DatTest._
//
//  private[this] val r = new Random()
//  private[this] implicit val randgen =
//    () => r
//
//  test("map") {
//    val d: Data[Int] = dataDat.map(_.length)
//    val t: Seq[Int] = data.map(_.length)
//    t.zip(d.toSeq)
//      .foreach {
//        case (dElem, tElem) => assert(dElem == tElem)
//      }
//  }
//
//  test("mapPartitions") {
//    val d: Data[Int] = dataDat.mapPartitions(x => x.map(_.length))
//    val t: Seq[Int] = data.map(_.length)
//    t.zip(d.toSeq)
//      .foreach {
//        case (dElem, tElem) => assert(dElem == tElem)
//      }
//  }
//
//  test("foreach") {
//    dataDat
//      .foreach(x => assert(x nonEmpty))
//  }
//
//  test("foreachPartitions") {
//    dataDat
//      .foreachPartition(_.foreach(x => assert(x nonEmpty)))
//  }
//
//  test("aggregate") {
//    val sumFromDat =
//      dataDat
//        .map(_.length)
//        .aggregate(0)(
//          { case (s, len) => s + len },
//          _ + _
//        )
//    val sumNormal =
//      data
//        .map(_.length)
//        .aggregate(0)(
//          { case (s, len) => s + len },
//          _ + _
//        )
//    assert(sumFromDat == sumNormal)
//  }
//
//  test("sortBy") {
//    val sortedDat: Data[String] = Data.shuffle(dataDat).sortBy(identity)
//    val sortedNormal: Seq[String] = data.sortBy(_ => r.nextLong()).sortBy(identity)
//    sortedDat.toSeq.zip(sortedNormal)
//      .foreach {
//        case (d, n) => assert(d == n)
//      }
//  }
//
//  test("take") {
//    val dTop2 = dataDat.take(2)
//    val nTop2 = data.take(2)
//    assert(dTop2 == nTop2)
//  }
//
//  test("toSeq") {
//    val expecting: Seq[String] = data
//    assert(dataDat.toSeq == expecting)
//  }
//
//  test("flatMap") {
//    val fDat: Data[String] =
//      dataDat
//        .flatMap(s =>
//          if (s.length % 2 == 0)
//            Some(s)
//          else
//            None)
//    val fNorm: Seq[String] =
//      data
//        .flatMap(s =>
//          if (s.length % 2 == 0)
//            Some(s)
//          else
//            None)
//
//    fDat.toSeq.zip(fNorm)
//      .foreach {
//        case (d, n) => assert(d == n)
//      }
//  }
//
//  test("flatten") {
//    val fDat: Data[String] =
//      dataDat
//        .map(s =>
//          if (s.length % 2 == 0)
//            Some(s)
//          else
//            None)
//        .flatten
//
//    val fNorm: Seq[String] =
//      data
//        .map(s =>
//          if (s.length % 2 == 0)
//            Some(s)
//          else
//            None)
//        .flatten
//
//    fDat.toSeq.zip(fNorm)
//      .foreach {
//        case (d, n) => assert(d == n)
//      }
//  }
//
//  test("groupBy") {
//    val dGb: Data[(Int, Iterable[String])] = dataDat.groupBy(_.length)
//    val nGb: Map[Int, Iterable[String]] = data.groupBy(_.length)
//
//    dGb
//      .foreach {
//        case (n, elements) =>
//          assert(nGb contains n)
//
//          val datElementSet = elements.toSet
//          assert(nGb(n).forall(e => datElementSet contains e))
//
//          val normalElementSet = nGb(n).toSet
//          assert(elements.forall(e => normalElementSet contains e))
//      }
//  }
//
//  test("size") {
//    assert(dataDat.size == data.size)
//  }
//
//  test("reduce") {
//    val datResult = dataDat.map(_.length).reduce(_ + _)
//    val normalResult = data.map(_.length).sum
//    assert(datResult == normalResult)
//  }
//}
//
//object DatTest {
//
//  val data: Seq[String] = DiscreteDistributionTest.itemCounts.keys.toIndexedSeq
//
//  val dataDat: Data[String] = {
//    import TravDat._
//    data
//  }
//}