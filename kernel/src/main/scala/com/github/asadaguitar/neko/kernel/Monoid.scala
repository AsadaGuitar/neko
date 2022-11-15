package com.github.asadaguitar.neko.kernel

import com.github.asadaguitar.neko.kernel.Eq

/** Monoid[A] extends Semigroup[A]
 *    abstract:
 *      def empty: A
 *      def combine(a: A, b: A): A
 *    concrete:
 *      def isEmpty(a: A)(using: eq: Eq[A]): Boolean
 *      def combineAll(as: Iterator[A]): A
 *      def combineAllOption(as: Iterator[A]): Option[A]
 *      def combineN(a: A, n: Long): A
 *      def reverse(a: A, b: A): Semigroup[A]
 *      def interacate(middle: A): Semigroup[A]
 */
trait Monoid[@specialized(Int,Long,Float,Double) A] extends Semigroup[A]:
  self =>

  def empty: A

  def isEmpty(a: A)(using eq: Eq[A]): Boolean = eq.eqv(a, empty)

  def combineAll(as: Iterator[A]): A = as.foldLeft(empty)(combine)

  override def combineN(a: A, n: Long): A =
    if n < 0 then throw IllegalArgumentException("combineN argument must be not zero.")
    else if n == 0 then empty
    else self.repeatedCombineN(a, n)
  
  override def reverse(a: A, b: A): Monoid[A] = new Monoid[A] {
    override def empty: A = self.empty
    override def combine(a: A, b: A): A = self.combine(b, a)
    override def isEmpty(a: A)(using eq: Eq[A]): Boolean = self.isEmpty(a)
    override def combineAll(as: Iterator[A]): A = self.combineAll(as)
  }


object Monoid:
  @inline final def apply[A](using ev: Monoid[A]): Monoid[A] = ev