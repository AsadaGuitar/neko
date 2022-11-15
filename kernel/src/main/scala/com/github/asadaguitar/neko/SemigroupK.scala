package com.github.asadaguitar.neko

import com.github.asadaguitar.neko.kernel.Semigroup

trait SemigroupK[F[_]]:

  def combineK[A](x: F[A], y: F[A]): F[A]

  def algebra[A]: Semigroup[F[A]]

  def combineAllOptionK[A](as: Iterator[F[A]]): Option[F[A]]

  