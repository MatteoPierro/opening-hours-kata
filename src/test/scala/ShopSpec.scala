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

    shop isOpen wednesday shouldBe true
  }
}

class Shop(openingDay: List[DayOfWeek], openingHours: (LocalTime, LocalTime)) {

  def isOpen(date: ZonedDateTime): Boolean = openingDay contains date.getDayOfWeek
}
