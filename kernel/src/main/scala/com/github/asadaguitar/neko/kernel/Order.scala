package com.github.asadaguitar.neko.kernel

import scala.math.Ordering

/** Order[A] extends PartialOrder[A]
 *    abstract:
 *      def compare(x: A, y: A): Int
 *    concrete:
 *      def tryCompare(x: A, y: A): Option[Int]
 *      def comparison(x: A, y: A): Comparison
 *      def partialComparison(x: A, y: A): Option[Comparison]
 *      def min(x: A, y: A): A
 *      def max(x: A, y: A): A = if gt(x, y)
 *      def pmin(x: A, y: A): Option[A]
 *      def pmax(x: A, y: A): Option[A]
 *      def eqv(x: A, y: A): Boolean
 *      def neqv(x: A, y: A): Boolean
 *      def lteqv(x: A, y: A): Boolean
 *      def lt(x: A, y: A): Boolean
 *      def gteqv(x: A, y: A): Boolean
 *      def gt(x: A, y: A): Boolean
 *      def toOrdering: Ordering[A]
 */
trait Order [@specialized A] extends PartialOrder[A]:

  def compare(x: A, y: A): Int

  def comparison(x: A, y: A): Comparison = Comparison.fromInt(compare(x, y))

  override def partialCompare(x: A, y: A): Double = compare(x, y).toDouble

  def min(x: A, y: A): A = if lt(x, y) then x else y

  def max(x: A, y: A): A = if gt(x, y) then x else y

  override def eqv(x: A, y: A): Boolean = compare(x, y) == 0

  override def lteqv(x: A, y: A): Boolean = compare(x, y) <= 0

  override def lt(x: A, y: A): Boolean = compare(x, y) < 0

  override def gteqv(x: A, y: A): Boolean = compare(x, y) >= 0

  override def gt(x: A, y: A): Boolean = compare(x, y) > 0

  def toOrdering: Ordering[A] = compare(_, _)


object Order:
  @inline final def apply[A](using ev: Order[A]): Order[A] = ev