package fif

/**
 * Allows importation of compile-time generated infix notation for the Data
 * typeclass. To bring into scope implicits that will convert methods on types
 * that obey the Data typeclass abstraction, do the following:
 *
 * ```
 * import fif.Data.syntax._
 * ```
 *
 * Now, in any method with the folowing type constraint on a generic, higher
 * kinded type D, D[_] : Data , we will be able to use the map (without loss
 * of generalization, any Data method) as:
 *
 * ```
 * import fif.Data
 * import Data.syntax._
 * def foo[D[_] : Data](data: D[Int]): Int =
 *  data.map(_ * 10).sum
 * ```
 */
object DataOps extends Serializable {

  val infix = Data.ops
}
