package com.github.asadaguitar.neko.kernel

/** Eq[A]
 *    abstract:
 *      def eqv(x: A, y: A): Boolean
 *    concrete:
 *      def neqv(x: A, y: A): Boolean
 */
trait Eq[@specialized A]:
  def eqv(x: A, y: A): Boolean
  def neqv(x: A, y: A): Boolean = !eqv(x, y)


object Eq:
  @inline final def apply[A](using ev: Eq[A]): Eq[A] = ev

  def and[@specialized A](a: Eq[A], b: Eq[A])(using ev: Eq[A]): Eq[A] =
    (x: A, y: A) => a.eqv(x, y) && b.eqv(x, y)

  def or[@specialized A](a: Eq[A], b: Eq[A])(using ev: Eq[A]): Eq[A] =
    (x: A, y: A) => a.eqv(x, y) || b.eqv(x, y)

  def instance[A](f: (A, A) => Boolean): Eq[A] = f(_, _)
