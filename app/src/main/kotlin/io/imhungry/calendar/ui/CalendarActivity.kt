package io.imhungry.calendar.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.*
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import io.imhungry.R
import java.time.LocalDateTime
import java.time.OffsetDateTime

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            //FIXME: temporary, because ugly and api 26
            val date = LocalDateTime.of(year, month + 1, dayOfMonth, 12, 0).toInstant(OffsetDateTime.now().getOffset())
                .toEpochMilli()
            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CONTENT_URI)
                .putExtra(TITLE, "Lunch at Location")
                .putExtra(EVENT_LOCATION, "Location")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date)

            startActivityForResult(intent, 0)
        }
    }
}
