package com.github.asadaguitar.neko

import com.github.asadaguitar.neko.kernel.Semigroup

trait SemigroupK[F[_]] extends Serializable:
  self =>

  /** example:
   *    val l1 = List(1, 2)
   *    val l2 = List(3, 4)
   *    SemigroupK[List].combineK(l1, l2) // List(1, 2, 3, 4)
   */
  def combineK[A](x: F[A], y: F[A]): F[A]

  /** example:
   *    val x: Option[Int] = Some(5)
   *    val y: Eval[Option[Int]] = Eval.later(5)
   *    x.combineKEval(x, y) // Eval.Later(Some(10))
   */
  def combineKEval[A](x: F[A], y: Eval[F[A]]): Eval[F[A]] = y.map(yy => combineK(x,yy))

  /** example:
   *    SemigroupK[List].algebra[Int] // Semigroup[List[Int]]
   *
   *  型を指定しSemigroupを取得
   */
  def algebra[A]: Semigroup[F[A]] = combineK(_, _)

  /** example:
   *    SemigroupK[List].compose[Option] // SemigroupK[List[Option]]
   *
   *  高カインドを指定してネストさせる
   */
  def compose[G[_]]: SemigroupK[[A] =>> F[G[A]]] =
    new ComposedSemigroupK[F,G]:
      override def F: SemigroupK[F] = self


  // def sum[A,B](fa: F[A], fb: F[B])