import org.scalatest._
import scala.language.reflectiveCalls
import matchers._
import flatspec._
import models._
import java.time.{Duration,LocalDate}


class ModelSpec extends AnyFlatSpec with should.Matchers{

  val break1 = PaidBreak(Duration.ofMinutes(20),Some(Duration.ofHours(3)),
    Some(LocalDate.of(2021,10,19)),Some(LocalDate.of(2021,10,20)))

  val break2 = PaidBreak(Duration.ofMinutes(30),Some(Duration.ofHours(3)),
    Some(LocalDate.of(2021,10,17)),Some(LocalDate.of(2021,10,17)))

  val breakInf = PaidBreak(Duration.ofMinutes(30),None,None,None)

  val presDay = Presence(LocalDate.of(2021,10,19))

  "PaidBreak" should "return true if presence occurs during a break" in {
    assert(break1.startsBefore(presDay.date))
    assert(break1.endsAfter(presDay.date))
  }

  "PaidBreak" should "return false if presence doesn't occur during a break" in {
    assert(!(break2.startsBefore(presDay.date) && break2.endsAfter(presDay.date)))
  }

  "PaidBreak" should "count None as indefinite time" in {
    assert(breakInf.startsBefore(presDay.date) && breakInf.endsAfter(presDay.date))
  }

}
