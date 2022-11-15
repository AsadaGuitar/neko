package com.github.asadaguitar.neko.kernel

/** PartialOrder[A] extends Eq[A]
 *    abstract:
 *      def partialCompare(x: A, y: A): Double
 *    concrete:
 *      def eqv(x: A, y: A): Boolean
 *      def neqv(x: A, y: A): Boolean
 *      def partialComparison(x: A, y: A): Option[Comparison]
 *      def tryCompare(x: A, y: A): Option[Int]
 *      def pmin(x: A, y: A): Option[A]
 *      def pmax(x: A, y: A): Option[A]
 *      def lteqv(x: A, y: A): Boolean
 *      def lt(x: A, y: A): Boolean
 *      def gteqv(x: A, y: A): Boolean
 *      def gt(x: A, y: A): Boolean
 */
trait PartialOrder[@specialized A] extends Eq[A]:
  self =>

  /**
   * (x,y) match
   *   case (x,y) if x < y  => -1
   *   case (x,y) if x == y => 0
   *   case (x,y) if x > y  => 1
   * */
  def partialCompare(x: A, y: A): Double

  /**
   * (x,y) match
   *   case (x,y) if x < y  => Some(Comparison.LessThan)
   *   case (x,y) if x == y => Some(Comparison.EqualTo)
   *   case (x,y) if x > y  => Some(Comparison.GreaterThan)
   *   case otherwise       => None
   * */
  def partialComparison(x: A, y: A): Option[Comparison] =
    val c = partialCompare(x, y)
    Comparison.fromDouble(c)

  /** scala.runtime.ScalaNumberProxy.sign:
   * val a = 1.sign // 1
   * val b = 0.sign // 0
   * val d = -1.sign // -1
   * val e = Double.NaN.sign // Nan
   */
  def tryCompare(x: A, y: A): Option[Int] =
    val c = partialCompare(x, y).sign
    if c.isNaN then None else Some(c.toInt)

  /**
   * if x <= y then Some(x)
   * else if x > y then Some(y)
   * else None
   *
   * example:
   *   pmin(1, 2) = Some(1)
   *   pmin(2, 2) = Some(2)
   *   pmin(2, 1) = Some(1)
   *   pmin(NaN, NaN) = None
   */
  def pmin(x: A, y: A): Option[A] =
    val c = partialCompare(x, y)
    if c <= 0 then Some(x)
    else if 0 < c then Some(y)
    else None

  /**
   * if x >= y then Some(x)
   * else if x < y Some(y)
   * else None
   *
   * example:
   *   pmax(1, 2) = Some(2)
   *   pmax(2, 2) = Some(2)
   *   pmax(2, 1) = Some(2)
   *   pmax(NaN, NaN) = None
   */
  def pmax(x: A, y: A): Option[A] =
    val c = partialCompare(x, y)
    if 0 <= c then Some(x)
    else if c < 0 then Some(y)
    else None

  /** if x == y then true */
  override def eqv(x: A, y: A): Boolean = partialCompare(x, y) == 0

  /** if x <= y then true */
  def lteqv(x: A, y: A): Boolean = partialCompare(x, y) < 1

  /** if x < y then true */
  def lt(x: A, y: A): Boolean = partialCompare(x, y) < 0

  /** if x >= y then true */
  def gteqv(x: A, y: A): Boolean = -1 < partialCompare(x, y)

  /** if x > y then true */
  def gt(x: A, y: A): Boolean = 0 < partialCompare(x, y)


object PartialOrder:
  @inline final def apply[A](using ev: PartialOrder[A]): PartialOrder[A] = ev