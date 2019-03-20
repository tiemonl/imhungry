package io.imhungry.login

import androidx.appcompat.app.AppCompatActivity
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class LoginUtilTest {

    @RelaxedMockK
    private lateinit var mockActivity: AppCompatActivity

    @RelaxedMockK
    private lateinit var mockFailureCallback: AuthFailureCallback

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `GIVEN login activity WHEN handle login result THEN verify home launched`() {
        mockActivity.handleLoginActivityResult(LoginConstants.RC_SIGN_IN, AppCompatActivity.RESULT_OK, null)
        verify { mockActivity.startActivity(any()) }
    }

    @Test
    fun `GIVEN login activity WHEN handle login failed result THEN verify home launched fail`() {
        mockActivity.handleLoginActivityResult(
            LoginConstants.RC_SIGN_IN,
            AppCompatActivity.RESULT_CANCELED,
            mockFailureCallback
        )
        verify { mockFailureCallback.invoke() }
    }
}