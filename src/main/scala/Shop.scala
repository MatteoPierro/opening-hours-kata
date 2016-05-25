import java.time.{DayOfWeek, LocalTime, ZonedDateTime}

class Shop(openingDays: List[DayOfWeek], openingHours: (LocalTime, LocalTime)) {

  def nextOpeningDate(date: ZonedDateTime): ZonedDateTime = {
    if (isAnOpeningDay(date) && isUpToOpeningHour(date.toLocalTime)) {
      return ZonedDateTime.of(date.toLocalDate, openingHours._1, date.getZone)
    }

    val nextDay = date.plusDays(1)
    nextOpeningDate(ZonedDateTime.of(nextDay.toLocalDate, openingHours._1, nextDay.getZone))
  }

  def isOpenOn(date: ZonedDateTime): Boolean = isAnOpeningDay(date) && isAnOpeningHour(date.toLocalTime)

  private def isAnOpeningDay(date: ZonedDateTime): Boolean = openingDays contains date.getDayOfWeek

  private def isAnOpeningHour(hour: LocalTime): Boolean = !hour.isBefore(openingHours._1) && !hour.isAfter(openingHours._2)

  private def isUpToOpeningHour(hour: LocalTime): Boolean = !hour.isAfter(openingHours._1)
}
