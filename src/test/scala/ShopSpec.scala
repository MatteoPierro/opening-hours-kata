import java.time.DayOfWeek._
import java.time.format.DateTimeFormatter
import java.time.{DayOfWeek, LocalTime, ZonedDateTime}

import org.scalatest.{FlatSpec, Matchers}

class ShopSpec extends FlatSpec with Matchers {

  implicit def stringToDate(string: String) = ZonedDateTime parse string

  val openingDays = List(MONDAY, WEDNESDAY, FRIDAY)

  val openingHours = (LocalTime.of(8, 0), LocalTime.of(16, 0))

  val shop = new Shop(openingDays, openingHours)

  "Shop" should "be open in an opening day" in {
    val wednesday = "2016-05-11T12:22:11.824Z"

    shop isOpenOn wednesday shouldBe true
  }

  it should "be closed in an closing day" in {
    val thursday = "2016-05-12T12:22:11.824Z"

    shop isOpenOn thursday shouldBe false
  }

  it should "be closed in an closing hour" in {
    val wednesdayBeforeOpeningHour = "2016-05-11T07:22:11.824Z"

    shop isOpenOn wednesdayBeforeOpeningHour shouldBe false
  }

  it should "be open at opening hour" in {
    val wednesdayAtOpeningHour = "2016-05-11T08:00:00Z"

    shop isOpenOn wednesdayAtOpeningHour shouldBe true
  }

  it should "have a next opening date" in {
    val wednesday: ZonedDateTime = "2016-05-11T12:22:11.824Z"
    val fridayMorning: ZonedDateTime = "2016-05-13T08:00:00.000Z"

    shop nextOpeningDate wednesday shouldBe fridayMorning
  }

  it should "have current day as opening day if hour is before opening hour" in {
    val wednesdayBeforeOpeningDay: ZonedDateTime = "2016-05-11T06:22:11.824Z"
    val wednesdayAtOpeningDay: ZonedDateTime = "2016-05-11T08:00:00.000Z"

    shop nextOpeningDate wednesdayBeforeOpeningDay shouldBe wednesdayAtOpeningDay
  }
}

class Shop(openingDays: List[DayOfWeek], openingHours: (LocalTime, LocalTime)) {
  def nextOpeningDate(date: ZonedDateTime): ZonedDateTime =
    isAnOpeningDay(date) && isNotAfterOpeningHour(date.toLocalTime) match {
      case true =>
        ZonedDateTime.of(date.toLocalDate, openingHours._1, date.getZone)

      case _ =>
        val nextDay = date.plusDays(1)
        nextOpeningDate(ZonedDateTime.of(nextDay.toLocalDate, openingHours._1, nextDay.getZone))
    }

  def isOpenOn(date: ZonedDateTime): Boolean = isAnOpeningDay(date) && isAnOpeningHour(date.toLocalTime)

  private def isAnOpeningDay(date: ZonedDateTime): Boolean = openingDays contains date.getDayOfWeek

  private def isAnOpeningHour(hour: LocalTime): Boolean = !hour.isBefore(openingHours._1) && !hour.isAfter(openingHours._2)

  private def isNotAfterOpeningHour(hour: LocalTime): Boolean = !hour.isAfter(openingHours._1)
}
