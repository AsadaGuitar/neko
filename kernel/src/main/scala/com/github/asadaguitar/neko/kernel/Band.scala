package com.github.asadaguitar.neko.kernel

/** Band[A] extends Semigroup[A]
 *    abstract:
 *      def combine(a: A, b: A): A
 *    concrete:
 *      def combineAllOption(as: Iterator[A]): Option[A]
 *      def combineN(a: A, n: Long): A
 *      def reverse(a: A, b: A): Semigroup[A]
 *      def interacate(middle: A): Semigroup[A]
 */
trait Band [@specialized(Int,Long,Float,Double) A] extends Semigroup[A]:
  override def repeatedCombineN(a: A, n: Long): A = a
  
  
object Band:
  @inline final def apply[A](using ev: Band[A]): Band[A] = ev
