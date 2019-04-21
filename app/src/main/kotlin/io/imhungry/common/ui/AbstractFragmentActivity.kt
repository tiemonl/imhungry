package io.imhungry.common.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.imhungry.R

abstract class AbstractFragmentActivity : BaseActivity() {

    protected abstract val fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment, fragment)
            .commit()
    }
}