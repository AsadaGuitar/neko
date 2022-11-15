package com.github.asadaguitar.neko

sealed trait Eval[+A]:

  def value: A

  def map[B](f: A => B): Eval[B] = flatMap(a => Now(f(a)))

  def flatMap[B](f: A => Eval[B]): Eval[B] = this match {
    case Now(value) => ???
    case Later(value) => ???
    case Already(value) => ???
  }

  def memoize: Eval[A]


final case class Now[A](value: A) extends Eval.Leaf[A]:
  override def memoize: Eval[A] = ???

final case class Later[A](value: A) extends Eval.Leaf[A]:
  override def memoize: Eval[A] = ???

final case class Already[A](value: A) extends Eval.Leaf[A]:
  override def memoize: Eval[A] = ???


object Eval:

  sealed abstract class Leaf[A] extends Eval[A]

  sealed abstract class Defer[A](val thunk: () => Eval[A]) extends Eval[A]

  sealed abstract class FlatMap[A] extends Eval[A]

  private case class Memoize[A](eval: Eval[A]) extends Eval[A]:
    var result: Option[A] = None
    def memoize: Eval[A] = this
    def value: A = result match {
      case Some(value) => ???
      case None => ???
    }


  sealed private abstract class FnStack[A, B]
  private final case class Ident[A, B]()
  private final case class Many[A, B, C]()

