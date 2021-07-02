package com.mattssonj.torpet.event

import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@SpringBootTest(classes = [UserEventListenerTestConfiguration::class])
internal class UserEventListenerTest {

    @Autowired lateinit var testClass: TestClassWithAnnotation
    @Autowired lateinit var mockHandler: UserEventHandler

    @BeforeEach
    fun clearMocks() {
        clearMocks(mockHandler)
    }

    @Test
    fun `Handle annotated UserEventProducer`() {
        testClass.toBeTested("hello")

        verify(exactly = 1) { mockHandler.handle(any()) }
    }

    @Test
    fun `Return value from annotated method is returned correctly`() {
        val input = "test value"
        val result = testClass.toBeTested(input)

        assertThat(result).isEqualTo(input)
    }

    @Test
    fun `Thrown exception in annotated method is thrown correctly`() {
        assertThrows<AssertException> { testClass.throwsAssertException() }
    }

    @Test
    fun `UserEvent is processed when annotated method throws exception`() {
        assertThrows<AssertException> { testClass.throwsAssertException() }
        verify(exactly = 1) { mockHandler.handle(any()) }
    }

    @Test
    fun `Anonymize argument when specified in annotation`() {
        testClass.anonymizeInput("to be anonymized")

        verify { mockHandler.handle(withArg { assertAnonymize(it) }) }
    }

    @Test
    fun `Throws exception if non-existing param name been used in annotation`() {
        assertThrows<IllegalArgumentException> { testClass.wrongParamName("should throw") }
    }

    private fun assertAnonymize(userEvent: UserEvent) {
        assertThat(userEvent.methodExecution.args.first().value).isEqualTo("***")
    }

}

/**
 * This class becomes a bean and is autowired in the test above. Each method is used in different test classes to
 * fulfill the specific cases.
 */
@Component
class TestClassWithAnnotation {
    @UserEventProducer fun toBeTested(stringInput: String) = stringInput
    @UserEventProducer fun throwsAssertException(): Nothing = throw AssertException()
    @UserEventProducer(anonymizeArguments = ["input"]) fun anonymizeInput(input: String) = input
    @UserEventProducer(anonymizeArguments = ["wrong"]) fun wrongParamName(right: String) = right
}

@TestConfiguration
internal class UserEventListenerTestConfiguration {
    @Bean fun mockHandler(): UserEventHandler = mockk(relaxed = true)
}

internal class AssertException : RuntimeException()