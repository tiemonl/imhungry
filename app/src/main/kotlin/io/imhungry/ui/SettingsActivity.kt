package io.imhungry.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.imhungry.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        title = getString(R.string.settings)
    }
}
