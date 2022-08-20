package com.kevalpatel2106.repositoryImpl.cache.db.converter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class IdConverterTest {

    @ParameterizedTest(name = "given long {0} when converted to account id then check account id is {1}")
    @MethodSource("provideAccountIdValues")
    fun `given long number when converted to account id then check account id`(
        accountIdValue: Long?,
        accountId: AccountId?,
    ) {
        assertEquals(accountId, IdConverter.toAccountId(accountIdValue))
    }

    @ParameterizedTest(name = "given account id {1} when converted from account id then check long value is {0}")
    @MethodSource("provideAccountIdValues")
    fun `given account id when converted from accountId then check long value`(
        accountIdValue: Long?,
        accountId: AccountId?,
    ) {
        assertEquals(accountIdValue, IdConverter.fromAccountId(accountId))
    }

    @ParameterizedTest(name = "given string {0} when converted to project id then check project id is {1}")
    @MethodSource("provideProjectIdValues")
    fun `given string when converted to project id then check project id`(
        projectIdValue: String?,
        projectId: ProjectId?,
    ) {
        assertEquals(projectId, IdConverter.toProjectId(projectIdValue))
    }

    @ParameterizedTest(name = "given project id {1} when converted from project id then check value is {0}")
    @MethodSource("provideProjectIdValues")
    fun `given project id when converted from project id then check value`(
        projectIdValue: String?,
        projectId: ProjectId?,
    ) {
        assertEquals(projectIdValue, IdConverter.fromProjectId(projectId))
    }

    companion object {
        private val fixture = KFixture()
        private val projectIdFixture = fixture<String>()

        @Suppress("unused")
        @JvmStatic
        fun provideAccountIdValues() = mutableListOf(
            arguments(1L, AccountId(1)),
            arguments(Long.MAX_VALUE, AccountId(Long.MAX_VALUE)),
            arguments(0L, AccountId(0)),
            arguments(null, null),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideProjectIdValues() = mutableListOf(
            arguments("test", ProjectId("test")),
            arguments(projectIdFixture, ProjectId(projectIdFixture)),
            arguments(null, null),
        )
    }
}
