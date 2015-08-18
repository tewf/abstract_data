package com.nitro.absnlp

import org.apache.flink.api.common.ExecutionConfig
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.common.typeutils.TypeSerializer
import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer
import org.apache.flink.api.scala.DataSet
import org.apache.flink.api.scala.typeutils.{ CaseClassSerializer, CaseClassTypeInfo }

import scala.language.higherKinds
import scala.reflect.ClassTag

private[absnlp] object FinkHelper {

  private[absnlp] val productClass: Class[Product] =
    classOf[Product]

  private[absnlp] def countFields(c: Class[_]): Int = {

    val fields = c.getFields
    if (fields.isEmpty)
      1

    else
      fields.foldLeft(0) {
        case (result, field) =>
          result + countFields(field.getClass)
      }
  }

  private[absnlp] val emptyTypeInfoList: List[TypeInformation[_]] =
    List.empty[TypeInformation[_]]

  def makeTypeInformation[A: ClassTag](): TypeInformation[A] = {

    val ct = implicitly[ClassTag[A]]

    new TypeInformation[A] {

      override lazy val isBasicType: Boolean =
        ct.runtimeClass.isPrimitive || ct.equals(ClassTag(classOf[String]))

      override lazy val isTupleType: Boolean =
        productClass.isAssignableFrom(ct.runtimeClass)

      override lazy val getArity: Int =
        ct.runtimeClass.getFields.length

      override lazy val getTotalFields: Int =
        countFields(ct.runtimeClass)

      override lazy val getTypeClass: Class[A] =
        ct.runtimeClass.asInstanceOf[Class[A]]

      override lazy val getGenericParameters: java.util.List[TypeInformation[_]] = {

        import scala.collection.JavaConversions._

        val tVars = ct.getClass.getTypeParameters
        if (tVars.isEmpty)
          emptyTypeInfoList

        else
          tVars
            .map { typeVariable =>
              val genericClass = typeVariable.getGenericDeclaration
              makeTypeInformation()(ClassTag(genericClass))
            }
            .toList
      }

      override lazy val isKeyType: Boolean =
        isBasicType

      override lazy val isSortKeyType: Boolean =
        isKeyType

      override def createSerializer(config: ExecutionConfig): TypeSerializer[A] =
        new KryoSerializer[A](getTypeClass, config)
    }
  }

}

case object FlinkDatatypeclass extends DataTypeclass[DataSet] {

  override def empty[A]: DataSet[A] =
    ???

  /** Transform a dataset by applying f to each element. */
  override def map[A, B: ClassTag](data: DataSet[A])(f: (A) => B): DataSet[B] =
    ???

  override def mapParition[A, B: ClassTag](d: DataSet[A])(f: Iterable[A] => Iterable[B]): DataSet[B] =
    ???
  //    d.mapPartition { partition =>
  //      f(partition.toIterable)
  //    }

  private[this] val empty = Seq.empty[Unit]

  /** Apply a side-effecting function to each element. */
  override def foreach[A](d: DataSet[A])(f: A => Any): Unit = {
    ???
    //    // ignore this operation!
    //    d.mapPartition { partition =>
    //      partition.foreach(f)
    //      empty
    //    }
    //    ()
  }

  override def foreachPartition[A](d: DataSet[A])(f: Iterable[A] => Any): Unit = {
    ???
    //    // ignore this operation!
    //    d.mapPartition { partition =>
    //      f(partition.toIterable)
    //      empty
    //    }
    //    ()
  }

  override def filter[A](d: DataSet[A])(f: A => Boolean): DataSet[A] =
    d.filter(f)

  override def aggregate[A, B: ClassTag](d: DataSet[A])(zero: B)(seqOp: (B, A) => B, combOp: (B, B) => B): B =
    ???

  /** Sort the dataset using a function f that evaluates each element to an orderable type */
  override def sortBy[A, B: ClassTag](d: DataSet[A])(f: (A) ⇒ B)(implicit ord: math.Ordering[B]): DataSet[A] =
    ???

  /** Construct a DataSet for the first k elements of a dataset. Will load into main mem. */
  override def take[A](d: DataSet[A])(k: Int): Traversable[A] =
    ???

  override def headOption[A](d: DataSet[A]): Option[A] =
    ???

  /** Load all elements of the dataset into an array in main memory. */
  override def toSeq[A](d: DataSet[A]): Seq[A] =
    ???

  override def flatMap[A, B: ClassTag](d: DataSet[A])(f: A => TraversableOnce[B]): DataSet[B] =
    ???

  override def flatten[A, B: ClassTag](d: DataSet[A])(implicit asDataSet: A => TraversableOnce[B]): DataSet[B] =
    ???

  override def groupBy[A, B: ClassTag](d: DataSet[A])(f: A => B): DataSet[(B, Iterator[A])] =
    ???

  /** This has type A as opposed to B >: A due to the RDD limitations */
  override def reduce[A](d: DataSet[A])(op: (A, A) => A): A =
    ???

  override def toMap[A, T, U](d: DataSet[A])(implicit ev: A <:< (T, U)): Map[T, U] =
    ???

  override def size[A](d: DataSet[A]): Long =
    ???

  override def isEmpty[A](d: DataSet[A]): Boolean =
    ???

  override def sum[N: ClassTag: Numeric](d: DataSet[N]): N =
    ???

  override def zip[A, B: ClassTag](d: DataSet[A])(that: DataSet[B]): DataSet[(A, B)] =
    ???

  override def zipWithIndex[A](d: DataSet[A]): DataSet[(A, Long)] =
    ???

}