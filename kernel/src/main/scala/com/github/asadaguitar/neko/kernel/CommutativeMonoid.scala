package com.github.asadaguitar.neko.kernel

/** CommutativeMonoid[A] extends Monoid[A]
 *    abstract:
 *      def combine(a: A, b: A): A
 *    concrete:
 *      def isEmpty(a: A)(using: eq: Eq[A]): Boolean
 *      def combineAll(as: Iterator[A]): A
 *      def combineAllOption(as: Iterator[A]): Option[A]
 *      def combineN(a: A, n: Long): A
 *      def reverse(a: A, b: A): Semigroup[A]
 *      def interacate(middle: A): Semigroup[A]
 */
trait CommutativeMonoid[@specialized A] extends Monoid[A]:
  self =>

  override def reverse(a: A, b: A): Monoid[A] = self


object CommutativeMonoid:
  @inline final def apply[A](using ev: CommutativeMonoid[A]): CommutativeMonoid[A] = ev