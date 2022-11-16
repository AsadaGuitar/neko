package com.github.asadaguitar.neko

trait Invariant[F[_]] extends Serializable:
  self =>

  def imap[A,B](fa: F[A])(f: A => B)(g: B => A): F[B]

  def compose[G[_]: Invariant]: Invariant[[A] =>> F[G[A]]]
  // ComposedInvariant

  // def composeFunctor[G[_]: Functor]: Invariant[λ[α => F[G[α]]]] =
  //  ComposeFunctor & Functor

  // def composeContravariant[G[_]: Contravariant]: Invariant[λ[α => F[G[α]]]]
