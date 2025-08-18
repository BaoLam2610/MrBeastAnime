package com.lambao.presentation.extension

import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

fun FragmentManager.showSingleDatePicker(
    title: String? = null,
    theme: Int = 0,
    minDate: Long? = null,
    maxDate: Long? = null,
    openAt: Long? = null,
    firstDayOfWeek: Int? = null,
    initialSelection: Long = MaterialDatePicker.todayInUtcMilliseconds(),
    validator: CalendarConstraints.DateValidator? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    tag: String = "SINGLE_DATE_PICKER",
    onDateSelected: (Long) -> Unit
) {
    val builder = MaterialDatePicker.Builder.datePicker().apply {
        setSelection(initialSelection)
        title?.let { setTitleText(it) }
        if (theme != 0) setTheme(theme)

        val constraintsBuilder = CalendarConstraints.Builder().apply {
            minDate?.let { setStart(it) }
            maxDate?.let { setEnd(it) }
            openAt?.let { setOpenAt(it) }
            firstDayOfWeek?.let { setFirstDayOfWeek(it) }
            validator?.let { setValidator(it) }
        }
        setCalendarConstraints(constraintsBuilder.build())

        positiveButtonText?.let { setPositiveButtonText(it) }
        negativeButtonText?.let { setNegativeButtonText(it) }
    }

    val picker = builder.build()
    picker.addOnPositiveButtonClickListener { selection -> onDateSelected(selection) }
    picker.show(this, tag)
}

fun FragmentManager.showDateRangePicker(
    title: String? = null,
    theme: Int = 0,
    minDate: Long? = null,
    maxDate: Long? = null,
    openAt: Long? = null,
    firstDayOfWeek: Int? = null,
    initialSelection: Pair<Long, Long> = Pair(
        MaterialDatePicker.thisMonthInUtcMilliseconds(),
        MaterialDatePicker.todayInUtcMilliseconds()
    ),
    validator: CalendarConstraints.DateValidator? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    tag: String = "DATE_RANGE_PICKER",
    onDateRangeSelected: (startDate: Long, endDate: Long) -> Unit
) {
    val builder = MaterialDatePicker.Builder.dateRangePicker().setSelection(initialSelection)

    title?.let { builder.setTitleText(it) }
    if (theme != 0) builder.setTheme(theme)

    val constraintsBuilder = CalendarConstraints.Builder().apply {
        minDate?.let { setStart(it) }
        maxDate?.let { setEnd(it) }
        openAt?.let { setOpenAt(it) }
        firstDayOfWeek?.let { setFirstDayOfWeek(it) }
        validator?.let { setValidator(it) }
        positiveButtonText?.let { builder.setPositiveButtonText(it) }
        negativeButtonText?.let { builder.setNegativeButtonText(it) }
    }
    builder.setCalendarConstraints(constraintsBuilder.build())

    val picker = builder.build()
    picker.addOnPositiveButtonClickListener { selection ->
        onDateRangeSelected(selection.first, selection.second)
    }
    picker.show(this, tag)
}

fun FragmentManager.showTimePicker(
    title: String? = null,
    theme: Int = 0,
    hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    minute: Int = Calendar.getInstance().get(Calendar.MINUTE),
    is24Hour: Boolean = true,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    tag: String = "TIME_PICKER",
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    val builder = MaterialTimePicker.Builder()
        .setTimeFormat(if (is24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
        .setHour(hour)
        .setMinute(minute)

    title?.let { builder.setTitleText(it) }
    if (theme != 0) builder.setTheme(theme)

    positiveButtonText?.let { builder.setPositiveButtonText(it) }
    negativeButtonText?.let { builder.setNegativeButtonText(it) }

    val picker = builder.build()
    picker.addOnPositiveButtonClickListener { onTimeSelected(picker.hour, picker.minute) }
    picker.show(this, tag)
}


