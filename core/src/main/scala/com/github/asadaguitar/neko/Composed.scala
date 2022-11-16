package com.github.asadaguitar.neko

private[neko] trait ComposedSemigroupK[F[_], G[_]] extends SemigroupK[[A] =>> F[G[A]]]:
  self =>

  def F: SemigroupK[F]

  override def combineK[A](x: F[G[A]], y: F[G[A]]): F[G[A]] = F.combineK(x, y)


private[neko] trait ComposedMonoidK[F[_], G[_]]

private[neko] trait ComposedInvariant[F[_], G[_]] extends Invariant[[A] =>> F[G[A]]]:
  self =>

  def F: Invariant[F]
  def G: Invariant[G]

  override def imap[A, B](fa: F[G[A]])(f: A => B)(g: B => A): F[G[B]]