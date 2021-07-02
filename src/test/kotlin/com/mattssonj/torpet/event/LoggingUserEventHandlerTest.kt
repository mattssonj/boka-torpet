package com.mattssonj.torpet.event

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import com.mattssonj.torpet.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

internal class LoggingUserEventHandlerTest {

    @Test
    fun `UserEvents are logged`() {
        val assertAppender = createAssertAppender()
        val userEvent = createUserEvent()

        val handler = LoggingUserEventHandler()
        handler.handle(userEvent)

        assertAppender.assertNotNull()

        val actuallyLoggedMessage = assertAppender.log?.message ?: "Log in AssertAppender seems to be null"
        val expectedLoggedMessage = expectedLoggedMessage(userEvent)

        assertThat(actuallyLoggedMessage).isEqualTo(expectedLoggedMessage)
    }

    private fun createAssertAppender(): AssertAppender<ILoggingEvent> {
        val userEventLogger = LoggerFactory.getLogger(LoggingUserEventHandler::class.java) as Logger
        val assertAppender = AssertAppender<ILoggingEvent>().also { it.start() }

        userEventLogger.addAppender(assertAppender)
        return assertAppender
    }

    private fun expectedLoggedMessage(userEvent: UserEvent): String = LoggingUserEventHandler.formatMessage(
        userEvent.user,
        userEvent.methodExecution.method,
        userEvent.methodExecution.args.map { it.toJson() },
        userEvent.methodExecution.result
    )

    private fun createUserEvent(): UserEvent {
        val arg = MethodArgument("argument", "arg")
        val methodExecution = MethodExecution("SomeMethodName", listOf(arg), "result")
        return UserEvent("test user", methodExecution)
    }

}

internal class AssertAppender<T> : AppenderBase<T>() {

    var log: T? = null

    fun assertNotNull(): Any? = assertThat(log).`as`("When null nothing has been logged").isNotNull

    override fun append(log: T) {
        this.log = log
    }
}