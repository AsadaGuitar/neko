package com.github.asadaguitar.neko.kernel

/** Group[A] extends Monoid[A]
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
trait Group[@specialized A] extends Monoid[A]:

  /**
   * Group[Int].inverse(5) = -5
   */
  def inverse(x: A): A

  /**
   * Group[Int].remove(5, 3) = 2
   */
  def remove(x: A, y: A): A

  override def combineN(a: A, n: Long): A =
    if 0 < n then repeatedCombineN(a, n)
    else if n == 0 then empty
    else if n == Long.MinValue then repeatedCombineN(inverse(combine(a, a)), 1073741824)
    else repeatedCombineN(inverse(a), -n)


object Group:
  @inline final def apply[A](using ev: Group[A]): Group[A] = ev