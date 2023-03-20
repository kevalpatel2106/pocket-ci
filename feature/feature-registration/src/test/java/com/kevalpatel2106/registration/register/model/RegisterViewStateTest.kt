package com.kevalpatel2106.registration.register.model

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getCIInfoFixture
import com.kevalpatel2106.registration.register.model.RegisterViewState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RegisterViewStateTest {
    private val kFixture = KFixture()

    @Test
    fun `given ciBasicInfo check initial state check `() {
        val ciInfo = getCIInfoFixture(kFixture())

        val initialState = RegisterViewState.initialState(ciInfo)
        val expected = RegisterViewState(
            ciName = ciInfo.name,
            ciIcon = ciInfo.icon,
            ciInfoUrl = ciInfo.infoUrl,
            defaultUrl = ciInfo.defaultBaseUrl,
            authTokenHintLink = ciInfo.authTokenExplainLink.value,
            allowUrlEdit = ciInfo.supportCustomBaseUrl,
            sampleAuthToken = ciInfo.sampleAuthToken.getValue(),
            enableAddAccountBtn = true,
            urlErrorMsg = null,
            tokenErrorMsg = null,
        )
        assertEquals(expected, initialState)
    }
}
