import org.scalatest._
import scala.language.reflectiveCalls
import matchers._
import flatspec._
import models._
import services.Validate
import java.time.{Duration,LocalDate}

class ServiceSpec extends AnyFlatSpec with should.Matchers {

  val break1 = PaidBreak(Duration.ofMinutes(20),Some(Duration.ofHours(3)),
    Some(LocalDate.of(2021,10,19)),Some(LocalDate.of(2021,10,20)))

  val break2 = PaidBreak(Duration.ofMinutes(30),Some(Duration.ofHours(3)),
    Some(LocalDate.of(2021,10,17)),Some(LocalDate.of(2021,10,17)))

  val breakInf = PaidBreak(Duration.ofMinutes(30),None,None,None)

  val breakOL = PaidBreak(Duration.ofMinutes(1),None,
    Some(LocalDate.of(2021,10,20)),Some(LocalDate.of(2021,10,21)))

  val presDayOverlapping = Presence(LocalDate.of(2021,10,17))

  val presDay1 = Presence(LocalDate.of(2021,10,16))
  val presDay2 = Presence(LocalDate.of(2021,10,15))
  val presDay3 = Presence(LocalDate.of(2021,10,14))
  val presDay4 = Presence(LocalDate.of(2021,10,13))
  val presDay5 = Presence(LocalDate.of(2021,10,12))

  val breaks = Seq(break1)
  val breaksAdded = Seq(break1,break2)
  val breaksOverlap = Seq(break1,breakOL)
  val breaksInf = Seq(breakInf,break1,break2)
  val daysNoOverlap = Seq(presDay1,presDay2,presDay3,presDay4,presDay5)
  val daysOverlap = daysNoOverlap :+ presDayOverlapping

  "CheckForOverlap" should "return true if no two breaks overlap" in {
    assert(Validate.checkForOverlap(breaksAdded))
  }

  "CheckForOverlap" should "return false if two breaks overlap" in {
    assert(!Validate.checkForOverlap(breaksOverlap))
  }

  "CheckForOverlap" should "return false if any break doesn't specify break period" in {
    assert(!Validate.checkForOverlap(breaksInf))
  }

  "Validate" should "return true if no day overlaps with changed paidbreak" in {
    assert(Validate.run(breaks,breaksAdded,daysNoOverlap))
  }

  "Validate" should "return false if any day overlaps with changed paidbreak" in {
    assert(!Validate.run(breaks,breaksAdded,daysOverlap))
  }
}
