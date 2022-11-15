package com.github.asadaguitar.neko.kernel

/** Semigroup[A] extends Semigroup[A]
 *    abstract:
 *      def combine(a: A, b: A): A
 *    concrete:
 *      def combineAllOption(as: Iterator[A]): Option[A]
 *      def combineN(a: A, n: Long): A
 *      def reverse(a: A, b: A): Semigroup[A]
 *      def interacate(middle: A): Semigroup[A]
 *
 * CommutativeSemigroup は可換半群を表す。
 * 半群は、すべてのxとyについて、x |+| y === y |+| xのとき、可換である。
 */
trait CommutativeSemigroup[@specialized(Int,Long,Float,Double) A] extends Semigroup[A] :
  self =>
  
  override def reverse(a: A, b: A): Semigroup[A] = self
  

object CommutativeSemigroup:
  @inline final def apply[A](using ev: CommutativeSemigroup[A]): CommutativeSemigroup[A] = ev