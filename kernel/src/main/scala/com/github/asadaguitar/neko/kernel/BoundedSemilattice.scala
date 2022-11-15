package com.github.asadaguitar.neko.kernel

/** BoundedSemilattice[A] extends CommutativeMonoid[A] with Semilattice[A]
 *    abstract:
 *      def combine(a: A, b: A): A
 *    concrete:
 *      def isEmpty(a: A)(using: eq: Eq[A]): Boolean
 *      def combineN(a: A, n: Long): A
 *      def combineAll(as: Iterator[A]): A
 *      def combineAllOption(as: Iterator[A]): Option[A]
 *      def reverse(a: A, b: A): Semigroup[A]
 *      def interacate(middle: A): Semigroup[A]
 *      def asMeetPartialOrder(implicit ev: Eq[A]): PartialOrder[A]
 *      def asJoinPartialOrder(implicit ev: Eq[A]): PartialOrder[A]
 */
trait BoundedSemilattice[@specialized A] extends CommutativeMonoid[A] with Semilattice[A]:
  self =>

  override def combineN(a: A, n: Long): A =
    if n < 0 then throw new IllegalArgumentException("combineN argument `n` must be positive.")
    else if n == 0 then empty
    else a

  override def reverse(a: A, b: A): BoundedSemilattice[A] = self


object BoundedSemilattice:
  @inline final def apply[A](using ev: BoundedSemilattice[A]): BoundedSemilattice[A] = ev