package com.github.asadaguitar.neko.kernel

enum Comparison(toInt: Int, toDouble: Double):
  case GreaterThan extends Comparison(1, 1.0)
  case EqualTo extends Comparison(0, 0.0)
  case LessThan extends Comparison(-1, -1.0)


object Comparison:
  private val someGt = Some(Comparison.GreaterThan)
  private val someEt = Some(Comparison.EqualTo)
  private val someLt = Some(Comparison.LessThan)
  
  def fromInt(n: Int): Comparison =
    if n < 0 then Comparison.LessThan
    else if n == 0 then Comparison.EqualTo
    else Comparison.GreaterThan

  def fromDouble(n: Double): Option[Comparison] =
    if n < 0 then someLt
    else if n == 0 then someEt
    else if 0 < n then someGt
    else None

  implicit val netKarnelEqMonoidForComparison: Eq[Comparison] =
    new Eq[Comparison] with Monoid[Comparison]:
      override def eqv(x: Comparison, y: Comparison): Boolean = x == y
      override def empty: Comparison = Comparison.EqualTo
      override def combine(a: Comparison, b: Comparison): Comparison = a match {
        case Comparison.EqualTo => b
        case otherwise => otherwise
      }