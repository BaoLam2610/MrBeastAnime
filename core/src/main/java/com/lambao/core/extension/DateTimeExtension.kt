package com.lambao.core.extension

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.ConcurrentHashMap

private val simpleDateFormatCache = ConcurrentHashMap<Pair<String, Locale>, SimpleDateFormat>()

private fun getSimpleDateFormat(pattern: String, locale: Locale): SimpleDateFormat {
    return simpleDateFormatCache.getOrPut(Pair(pattern, locale)) { SimpleDateFormat(pattern, locale) }
}

fun Date.format(pattern: String, locale: Locale = Locale.getDefault()): String = try {
    getSimpleDateFormat(pattern, locale).format(this)
} catch (e: Exception) { "" }

fun Date.isBefore(other: Date): Boolean = time < other.time
fun Date.isAfter(other: Date): Boolean = time > other.time

fun Date.isSameDay(other: Date): Boolean {
    val thisCalendar = Calendar.getInstance().apply { time = this@isSameDay }
    val otherCalendar = Calendar.getInstance().apply { time = other }
    return thisCalendar.isSameDay(otherCalendar)
}

fun Date.isToday(): Boolean = isSameDay(Date())
fun Date.isPast(): Boolean = isBefore(Date())
fun Date.isFuture(): Boolean = isAfter(Date())

fun Date.getDayOfWeek(locale: Locale = Locale.getDefault()): String = getSimpleDateFormat("EEEE", locale).format(this)
fun Date.getDayOfWeekNumber(): Int = Calendar.getInstance().apply { time = this@getDayOfWeekNumber }.get(Calendar.DAY_OF_WEEK)
fun Date.plusDays(days: Int): Date = add(Calendar.DAY_OF_MONTH, days)
fun Date.plusMonths(months: Int): Date = add(Calendar.MONTH, months)
fun Date.plusYears(years: Int): Date = add(Calendar.YEAR, years)
fun Date.plusHours(hours: Int): Date = add(Calendar.HOUR_OF_DAY, hours)
fun Date.plusMinutes(minutes: Int): Date = add(Calendar.MINUTE, minutes)

fun Date.add(field: Int, amount: Int): Date = Calendar.getInstance().apply { time = this@add; add(field, amount) }.time
fun Date.toCalendar(): Calendar = Calendar.getInstance().apply { time = this@toCalendar }
fun Date.startOfDay(): Date = Calendar.getInstance().apply { time = this@startOfDay; set(Calendar.HOUR_OF_DAY,0); set(Calendar.MINUTE,0); set(Calendar.SECOND,0); set(Calendar.MILLISECOND,0) }.time
fun Date.endOfDay(): Date = Calendar.getInstance().apply { time = this@endOfDay; set(Calendar.HOUR_OF_DAY,23); set(Calendar.MINUTE,59); set(Calendar.SECOND,59); set(Calendar.MILLISECOND,999) }.time
fun Date.getAge(): Int { val b = Calendar.getInstance().apply { time = this@getAge }; val t = Calendar.getInstance(); var a = t.get(Calendar.YEAR)-b.get(Calendar.YEAR); if (t.get(Calendar.MONTH)<b.get(Calendar.MONTH) || (t.get(Calendar.MONTH)==b.get(Calendar.MONTH) && t.get(Calendar.DAY_OF_MONTH)<b.get(Calendar.DAY_OF_MONTH))) a--; return a }
fun Calendar.format(pattern: String, locale: Locale = Locale.getDefault()): String = try { getSimpleDateFormat(pattern, locale).format(time) } catch (e: Exception) { "" }
fun Calendar.isBefore(other: Calendar): Boolean = timeInMillis < other.timeInMillis
fun Calendar.isAfter(other: Calendar): Boolean = timeInMillis > other.timeInMillis
fun Calendar.isSameDay(other: Calendar): Boolean = get(Calendar.YEAR)==other.get(Calendar.YEAR) && get(Calendar.MONTH)==other.get(Calendar.MONTH) && get(Calendar.DAY_OF_MONTH)==other.get(Calendar.DAY_OF_MONTH)
fun Calendar.isToday(): Boolean = isSameDay(Calendar.getInstance())
fun Calendar.isPast(): Boolean = isBefore(Calendar.getInstance())
fun Calendar.isFuture(): Boolean = isAfter(Calendar.getInstance())
fun Calendar.getDayOfWeek(locale: Locale = Locale.getDefault()): String = getSimpleDateFormat("EEEE", locale).format(time)
fun Calendar.getAmPm(): String = if (get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
fun Calendar.daysUntil(other: Calendar): Int { val thisDate = Calendar.getInstance().apply { timeInMillis = this@daysUntil.timeInMillis; set(Calendar.HOUR_OF_DAY,0); set(Calendar.MINUTE,0); set(Calendar.SECOND,0); set(Calendar.MILLISECOND,0) }; val otherDate = Calendar.getInstance().apply { timeInMillis = other.timeInMillis; set(Calendar.HOUR_OF_DAY,0); set(Calendar.MINUTE,0); set(Calendar.SECOND,0); set(Calendar.MILLISECOND,0) }; val diff = otherDate.timeInMillis - thisDate.timeInMillis; return (diff / (1000*60*60*24)).toInt() }
fun Calendar.toDate(): Date = time
fun Calendar.plusDays(days: Int): Calendar = (clone() as Calendar).apply { add(Calendar.DAY_OF_MONTH, days) }
fun Calendar.plusMonths(months: Int): Calendar = (clone() as Calendar).apply { add(Calendar.MONTH, months) }
fun Calendar.plusYears(years: Int): Calendar = (clone() as Calendar).apply { add(Calendar.YEAR, years) }
fun String.toDate(pattern: String, locale: Locale = Locale.getDefault()): Date? = try { getSimpleDateFormat(pattern, locale).parse(this) } catch (e: Exception) { null }
fun String.toCalendar(pattern: String, locale: Locale = Locale.getDefault()): Calendar? = toDate(pattern, locale)?.toCalendar()
fun String.reformatDate(oldPattern: String, newPattern: String, locale: Locale = Locale.getDefault()): String? = toDate(oldPattern, locale)?.format(newPattern, locale)
fun String.isValidDate(pattern: String, locale: Locale = Locale.getDefault()): Boolean = try { getSimpleDateFormat(pattern, locale).apply { isLenient = false }.parse(this); true } catch (e: ParseException) { false }
fun Date.getDaysInMonth(): Int { val c = Calendar.getInstance().apply { time = this@getDaysInMonth }; return c.getActualMaximum(Calendar.DAY_OF_MONTH) }
fun Date.firstDayOfMonth(): Date = Calendar.getInstance().apply { time = this@firstDayOfMonth; set(Calendar.DAY_OF_MONTH,1); set(Calendar.HOUR_OF_DAY,0); set(Calendar.MINUTE,0); set(Calendar.SECOND,0); set(Calendar.MILLISECOND,0) }.time
fun Date.lastDayOfMonth(): Date = Calendar.getInstance().apply { time = this@lastDayOfMonth; set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH)); set(Calendar.HOUR_OF_DAY,23); set(Calendar.MINUTE,59); set(Calendar.SECOND,59); set(Calendar.MILLISECOND,999) }.time
fun Date.firstDayOfWeek(): Date { val c = Calendar.getInstance().apply { time = this@firstDayOfWeek }; return c.apply { set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); set(Calendar.HOUR_OF_DAY,0); set(Calendar.MINUTE,0); set(Calendar.SECOND,0); set(Calendar.MILLISECOND,0) }.time }
fun Date.lastDayOfWeek(): Date { val c = Calendar.getInstance().apply { time = this@lastDayOfWeek }; return c.apply { set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY); set(Calendar.HOUR_OF_DAY,23); set(Calendar.MINUTE,59); set(Calendar.SECOND,59); set(Calendar.MILLISECOND,999) }.time }
fun Date.firstDayOfYear(): Date = Calendar.getInstance().apply { time = this@firstDayOfYear; set(Calendar.MONTH, Calendar.JANUARY); set(Calendar.DAY_OF_MONTH,1); set(Calendar.HOUR_OF_DAY,0); set(Calendar.MINUTE,0); set(Calendar.SECOND,0); set(Calendar.MILLISECOND,0) }.time
fun Date.lastDayOfYear(): Date = Calendar.getInstance().apply { time = this@lastDayOfYear; set(Calendar.MONTH, Calendar.DECEMBER); set(Calendar.DAY_OF_MONTH,31); set(Calendar.HOUR_OF_DAY,23); set(Calendar.MINUTE,59); set(Calendar.SECOND,59); set(Calendar.MILLISECOND,999) }.time
fun Calendar.isLeapYear(): Boolean { val year = get(Calendar.YEAR); return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0) }
fun Date.getQuarter(): Int { val c = Calendar.getInstance().apply { time = this@getQuarter }; return c.get(Calendar.MONTH) / 3 + 1 }
fun Date.getWeekOfYear(): Int { val c = Calendar.getInstance().apply { time = this@getWeekOfYear }; return c.get(Calendar.WEEK_OF_YEAR) }
fun Date.formatWithTimeZone(pattern: String, timeZone: TimeZone, locale: Locale = Locale.getDefault()): String = try { SimpleDateFormat(pattern, locale).apply { this.timeZone = timeZone }.format(this) } catch (e: Exception) { "" }
fun Date.millisUntil(other: Date): Long = other.time - this.time
fun Date.secondsUntil(other: Date): Long = millisUntil(other) / 1000
fun Date.minutesUntil(other: Date): Long = secondsUntil(other) / 60
fun Date.hoursUntil(other: Date): Long = minutesUntil(other) / 60
fun Date.daysUntil(other: Date): Int = toCalendar().daysUntil(other.toCalendar())
fun Date.isWeekend(): Boolean { val c = Calendar.getInstance().apply { time = this@isWeekend }; val d = c.get(Calendar.DAY_OF_WEEK); return d == Calendar.SATURDAY || d == Calendar.SUNDAY }
fun Date.isWeekday(): Boolean = !isWeekend()
fun createDate(year: Int, month: Int, day: Int): Date = Calendar.getInstance().apply { set(Calendar.YEAR, year); set(Calendar.MONTH, month-1); set(Calendar.DAY_OF_MONTH, day); set(Calendar.HOUR_OF_DAY,0); set(Calendar.MINUTE,0); set(Calendar.SECOND,0); set(Calendar.MILLISECOND,0) }.time
fun createDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int = 0): Date = Calendar.getInstance().apply { set(Calendar.YEAR, year); set(Calendar.MONTH, month-1); set(Calendar.DAY_OF_MONTH, day); set(Calendar.HOUR_OF_DAY, hour); set(Calendar.MINUTE, minute); set(Calendar.SECOND, second); set(Calendar.MILLISECOND,0) }.time
fun Date.getMonthName(locale: Locale = Locale.getDefault()): String = getSimpleDateFormat("MMMM", locale).format(this)
fun Long.toDate(): Date? = try { Date(this) } catch (e: Exception) { null }


