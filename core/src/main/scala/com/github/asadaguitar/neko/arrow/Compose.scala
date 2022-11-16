package com.github.asadaguitar.neko.arrow

import com.github.asadaguitar.neko.kernel.Semigroup

trait Compose[F[_, _]]:

  def compose[A, B, C](fa: F[B, C], fb: F[A, B]): F[A, C]

  def andThen[A, B, C](fa: F[A, B], fb: F[B, C]): F[A, C] = compose(fb, fa)

  def algebra[A]: Semigroup[F[A, A]] = compose(_, _)
