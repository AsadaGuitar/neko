package com.github.asadaguitar.neko.kernel

/** Semilattice[A] extends Band[A] with CommutativeSemigroup[A]
 *    abstract:
 *      def combine(a: A, b: A): A
 *    concrete:
 *      def asMeetPartialOrder(implicit ev: Eq[A]): PartialOrder[A]
 *      def asJoinPartialOrder(implicit ev: Eq[A]): PartialOrder[A]
 *      def combineAllOption(as: Iterator[A]): Option[A]
 *      def combineN(a: A, n: Long): A
 *      def reverse(a: A, b: A): Semigroup[A]
 *      def interacate(middle: A): Semigroup[A]
 *
 *  結合半束：combine(x, x) = x
 */
trait Semilattice[@specialized A] extends Band[A] with CommutativeSemigroup[A]:
  self =>

  /** returns PartialOrder[A].partialCompare(x: A, y: A) =
   *    if x === y then 0.0
   *    else if x === (x |+| y) then -1.0
   *    else if (x |+| y) === y then 1.0
   *    else Double.NaN
   *  */
  def asMeetPartialOrder(implicit ev: Eq[A]): PartialOrder[A] =
    (x, y) =>
      if ev.eqv(x, y) then 0.0
      else
        val z = self.combine(x, y)
        if ev.eqv(x, z) then -1.0
        else if ev.eqv(y, z) then 1.0
        else Double.NaN

  /** returns PartialOrder[A].partialCompare(x: A, y: A) =
   *    if x === y then 0.0
   *    else if x === (x |+| y) then 1.0
   *    else if (x |+| y) === y then -1.0
   *    else Double.NaN
   *  */
  def asJoinPartialOrder(implicit ev: Eq[A]): PartialOrder[A] =
    (x, y) =>
      if ev.eqv(x, y) then 0.0
      else
        val z = self.combine(x, y)
        if ev.eqv(y, z) then -1.0
        else if ev.eqv(x, z) then 1.0
        else Double.NaN


object Semilattice:
  @inline final def apply[A](using ev: Semilattice[A]): Semigroup[A] = ev