package com.github.asadaguitar.neko.kernel

/** Show[A]
 *    abstract:
 *      def show(a: A): A
 */
trait Show[@specialized A]:
  def show(a: A): A


object Show:
  @inline final def apply[A](using ev: Show[A]): Show[A] = ev