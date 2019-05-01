package io.imhungry.calendar.ui

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Events.*
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import io.imhungry.R
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import io.imhungry.common.ui.NavigationActivity

class CalendarActivity : NavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val zone = ZoneId.systemDefault()
            val date = LocalDate.of(year,month+1, dayOfMonth).atStartOfDay(zone).toEpochSecond()
            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CONTENT_URI)
                .putExtra(TITLE, "Lunch at Location")
                .putExtra(EVENT_LOCATION, "Location")
                .putExtra(DTSTART, date)
            startActivityForResult(intent, 0)
        }
    }
}
