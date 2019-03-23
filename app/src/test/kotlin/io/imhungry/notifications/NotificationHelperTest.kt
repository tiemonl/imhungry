package io.imhungry.notifications

import android.content.Context
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class NotificationHelperTest {

    @RelaxedMockK
    private lateinit var mockContext: Context

    private lateinit var SUT: NotificationHelper

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
        SUT = NotificationHelper(mockContext)
    }

    @Test
    fun `GIVEN NotificationHelper(Context C) WHEN getStarterNotificationBuilder THEN returned NotificationBuilder context is C`() {
        var builder = SUT.getStarterNotificationBuilder("", "", NotificationPriority.DEFAULT)
        assert(mockContext == builder.mContext)
    }
}