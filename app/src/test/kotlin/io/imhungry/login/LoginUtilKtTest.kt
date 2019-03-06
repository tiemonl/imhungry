package io.imhungry.login

import androidx.appcompat.app.AppCompatActivity
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class LoginUtilKtTest {

    @RelaxedMockK
    private lateinit var mockActivity: AppCompatActivity

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN login activity WHEN handle login result THEN verify home launched`() {
        mockActivity.handleLoginActivityResult(LoginConstants.RC_SIGN_IN, AppCompatActivity.RESULT_OK, null)
        verify { mockActivity.startActivity(any()) }
    }
}