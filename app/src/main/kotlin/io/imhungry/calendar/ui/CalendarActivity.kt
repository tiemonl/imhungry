package io.imhungry.calendar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.imhungry.R
import io.imhungry.common.ui.NavigationActivity

class CalendarActivity : NavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
    }
}
