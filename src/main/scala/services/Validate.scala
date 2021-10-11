package services

import models._

object Validate {
  /*
  prevBreaks - poprzednia kolekcja przerw
  newBreaks - nowa kolekcja przerw
  presence - kolekcja obecnosci
  */

  def run(prevBreaks:Seq[PaidBreak],newBreaks:Seq[PaidBreak],presence:Seq[Presence]):Boolean = {
    //jesli ktorekolwiek dwie nowe przerwy pokrywaja sie, nie mozna wykonac operacji
    if(!checkForOverlap(newBreaks)) return false

    val added = newBreaks.diff(prevBreaks) //dodane przerwy
    lazy val removed = prevBreaks.diff(newBreaks) //usuniete przerwy

    //znajdz jedna obecnosc ktora pokrywa sie ze zmieniona przerwa
    val result = presence.exists { pre =>
      added.exists (addBrk => addBrk.startsBefore(pre.date) && addBrk.endsAfter(pre.date)) ||
        removed.exists (rmBrk => rmBrk.startsBefore(pre.date) && rmBrk.endsAfter(pre.date))
    }

    !result
  }

  def checkForOverlap(breaks:Seq[PaidBreak]):Boolean = {
    //Sprawdzic czy zadne dwie przerwy nie pokrywaja sie czasowo

    if (breaks.length <= 1) return true //jesli jest tylko 1 przerwa lub wcale

    val pairs = breaks.combinations(2)

    pairs.forall { case Seq(brk1, brk2) =>
      if (brk1.start.isDefined && brk1.end.isDefined && brk2.start.isDefined && brk2.end.isDefined) {
        !brk1.endsAfter(brk1.start.get) || !brk2.endsAfter(brk1.start.get)
      } else false
    }
  }
}
