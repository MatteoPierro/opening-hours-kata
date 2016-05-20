import java.time.DayOfWeek._
import java.time.{DayOfWeek, LocalTime, ZonedDateTime}

import org.scalatest.{FlatSpec, Matchers}

class ShopSpec extends FlatSpec with Matchers {

  implicit def stringToDate(string: String) = ZonedDateTime parse string

  val openingDay = List(MONDAY, WEDNESDAY, FRIDAY)

  val openingHours = (LocalTime.of(8, 0), LocalTime.of(16, 0))

  it should "be open in an opening day" in {
    val shop = new Shop(openingDay, openingHours)
    val wednesday = "2016-05-11T12:22:11.824Z"

    shop isOpenOn wednesday shouldBe true
  }

  it should "be closed in an closing day" in {
    val shop = new Shop(openingDay, openingHours)
    val thursday = "2016-05-12T12:22:11.824Z"

    shop isOpenOn thursday shouldBe false
  }

  it should "be closed in an closing hour" in {
    val shop = new Shop(openingDay, openingHours)
    val wednesdayBeforeOpeningHour = "2016-05-11T07:22:11.824Z"

    shop isOpenOn wednesdayBeforeOpeningHour shouldBe false
  }
}

class Shop(openingDay: List[DayOfWeek], openingHours: (LocalTime, LocalTime)) {

  def isOpenOn(date: ZonedDateTime): Boolean = isAnOpeningDay(date) && isAnOpeningHour(date)

  def isAnOpeningDay(date: ZonedDateTime): Boolean = openingDay contains date.getDayOfWeek


  def isAnOpeningHour(date: ZonedDateTime): Boolean = {
    date.toLocalTime.isAfter(openingHours._1) && date.toLocalTime.isBefore(openingHours._2)
  }
}
