package com.github.asadaguitar.neko.kernel

/** CommutativeGroup[A] extends Group[A] with CommutativeMonoid[A]
 *    abstract:
 *      def empty: A
 *      def combine(a: A, b: A): A
 *      def inverse(x: A): A
 *      def remove(x: A, y: A): A
 *    concrete:
 *      def isEmpty(a: A)(using: eq: Eq[A]): Boolean
 *      def combineAll(as: Iterator[A]): A
 *      def combineAllOption(as: Iterator[A]): Option[A]
 *      def combineN(a: A, n: Long): A
 *      def reverse(a: A, b: A): Semigroup[A]
 *      def interacate(middle: A): Semigroup[A]
 */
trait CommutativeGroup[@specialized A] extends Group[A] with CommutativeMonoid[A]

object CommutativeGroup:
  @inline def apply[A](using ev: CommutativeGroup[A]): CommutativeGroup[A] = ev