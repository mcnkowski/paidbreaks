package models

import java.time.{Duration,LocalDate}

case class PaidBreak(dur:Duration, req:Option[Duration]=None, start: Option[LocalDate]=None, end:Option[LocalDate]=None) {

  //sprawdz czy przerwa zaczyna sie przed obecnoscia
  def startsBefore(date:LocalDate):Boolean = {
    if (start.isDefined) {
      val startDate = start.get
      startDate.isBefore(date) || startDate.isEqual(date)
    } else true
  }

  //sprawdz czy przerwa konczy sie po obecnosci
  def endsAfter(date:LocalDate):Boolean = {
    if (end.isDefined) {
      val endDate = end.get
      endDate.isAfter(date) || endDate.isEqual(date)
    } else true
  }
}
