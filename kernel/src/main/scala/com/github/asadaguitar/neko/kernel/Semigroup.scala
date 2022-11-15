package com.github.asadaguitar.neko.kernel

import scala.annotation.tailrec

/** Semigroup[A]
 *    abstract:
 *      def combine(a: A, b: A): A
 *    concrete:
 *      def combineAllOption(as: Iterator[A]): Option[A]
 *      def combineN(a: A, n: Long): A
 *      def reverse(a: A, b: A): Semigroup[A]
 *      def interacate(middle: A): Semigroup[A]
 */
trait Semigroup [@specialized(Int,Long,Float,Double) A]:
  self =>

  def combine(a: A, b: A): A

  def combineAllOption(as: Iterator[A]): Option[A] =
    as.reduceOption(combine)
  
  /** 結合をn回繰返す */
  def combineN(a: A, n: Long): A =
    if n == 0 then throw IllegalArgumentException("combineN argument must be not zero.")
    else repeatedCombineN(a, n)

  protected def repeatedCombineN(a: A, n: Long): A = {
    @tailrec
    def loop(a: A, n: Long, extra: A): A =
      if n == 0 then extra
      else loop(a, n - 1, combine(a, extra))

    if n == 1 then a else loop(a, n, a)
  }

  /** combineの辺を反転したMonoidを返却する。 */
  def reverse(a: A, b: A): Semigroup[A] = new Semigroup[A] {
    override def combine(a: A, b: A): A = self.combine(b, a)
    override def reverse(a: A, b: A): Semigroup[A] = self
  }

  /** combineの右辺と左辺の間に{middle}を割込ませたSemigroupを返却する。 */
  def interacate(middle: A): Semigroup[A] = (a, b) => combine(a, combine(middle, b))
  
  
object Semigroup:
  @inline final def apply[A](using ev: Semigroup[A]): Semigroup[A] = ev