package io.imhungry.calendar.ui

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.*
import android.widget.CalendarView
import io.imhungry.R
import io.imhungry.common.ui.NavigationActivity
import java.util.*


class CalendarActivity : NavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val beginTime = Calendar.getInstance()
            beginTime.set(year, month, dayOfMonth, 12, 0)
            val endTime = Calendar.getInstance()
            endTime.set(year, month, dayOfMonth, 13, 0)
            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CONTENT_URI)
                .putExtra(TITLE, "Lunch at Location")
                .putExtra(EVENT_LOCATION, "Location")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)

            startActivityForResult(intent, 0)
        }
    }
}
