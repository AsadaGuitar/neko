package com.github.asadaguitar.neko

import com.github.asadaguitar.neko.Eval.FnStack.Many

import scala.annotation.tailrec

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

  /**
   * Eval[A] <- Leaf[A] <- Now[A]
   *         <-         <- Later[A]
   *         <-         <- Already[A]
   *         <- Defer[A]
   *         <- FlatMap[A]
   *         <- Memoize[A]
   */

  sealed abstract class Leaf[A] extends Eval[A]

  sealed abstract class Defer[A](val thunk: () => Eval[A]) extends Eval[A]:
    override def memoize: Eval[A] = Memoize(this)
    override def value: A = evaluate(this)

  sealed abstract class FlatMap[A] extends Eval[A]:
    type Start
    def start: () => Eval[Start]
    def run: Start => Eval[A]
    override def memoize: Eval[A] = Memoize(this)
    override def value: A = evaluate(this)

  private case class Memoize[A](eval: Eval[A]) extends Eval[A]:
    var result: Option[A] = None
    def memoize: Eval[A] = this
    def value: A =
      result match
        case Some(value) => ???
        case None => ???

  enum FnStack[A,B]:
    case Ident[A,B](ev: A <:< B) extends FnStack[A,B]
    case Many[A, B, C](first: A => Eval[B], rest: FnStack[B,C]) extends FnStack[A,C]

  private def evaluate[A](e: Eval[A]): A = {

    /** 引数で受取ったMemoizeにA1を書込む */
    def addToMemo[A1](m: Memoize[A1]): A1 => Eval[A1] =
      a1 =>
        m.result = Some(a1)
        Now(a1)

    /** 評価関数
     * 木構造のEvalを評価
     * */
    @tailrec
    def loop[A1](curr: Eval[A1], fs: FnStack[A1, A]): A =
      curr match
        /** Leaf
         *    Identで再帰終了
         *    Manyでスタックを適用
         */
        case l: Leaf[A1] =>
          fs match
            case FnStack.Ident(ev)         => ev(l.value)
            case FnStack.Many(first, rest) => loop(first(l.value), rest)
        /** Defer
         *    deferのチャンクを適用
         */
        case d: Defer[A1] => loop(d.thunk(), fs)
        case f: FlatMap[A1] =>
          f.start() match
            case fl: Leaf[f.Start]  => loop(f.run(fl.value), fs)
            case fd: Defer[f.Start] => loop(fd.thunk(), Many(f.run, fs))
            case ff: FlatMap[f.Start] =>
              val nxtFs = FnStack.Many(f.run, fs)
              loop(ff.start(), FnStack.Many(ff.run, nxtFs))
            case fm@Memoize(eval) =>
              fm.result match
                case Some(a) => loop(f.run(a), fs)
                case None =>
                  val nxtFs = FnStack.Many(f.run, fs)
                  loop(eval, FnStack.Many(addToMemo(fm), nxtFs))
        case m: Memoize[a] =>
          m.result match
            case None    => loop(m.eval, FnStack.Many[a,A1,A](addToMemo(m), fs))
            case Some(a) =>
              fs match
                case FnStack.Ident(ev)         => ev(a)
                case FnStack.Many(first, rest) => loop(first(a), rest)

    loop(e, FnStack.Ident(implicitly[A <:< A]))
  }
end Eval

