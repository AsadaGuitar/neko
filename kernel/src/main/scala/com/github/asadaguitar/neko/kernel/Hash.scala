package com.github.asadaguitar.neko.kernel

/** Hash[A] extends Eq[A]
 *    abstract:
 *      def eqv(x: A, y: A): Boolean
 *      def hash(x: A): Int
 *    concrete:
 *      def neqv(x: A, y: A): Boolean
 */
trait Hash[@specialized A] extends Eq[A]:
  def hash(x: A): Int


object Hash:
  @inline final def apply[A](using ev: Hash[A]): Hash[A] = ev