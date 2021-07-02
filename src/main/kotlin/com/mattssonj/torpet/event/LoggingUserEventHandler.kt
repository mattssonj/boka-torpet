package com.mattssonj.torpet.event

import com.mattssonj.torpet.toJson
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LoggingUserEventHandler : UserEventHandler {

    private val logger = LoggerFactory.getLogger(LoggingUserEventHandler::class.java)

    override fun handle(event: UserEvent) {
        val msg = formatMessage(
            event.user,
            event.methodExecution.method,
            event.methodExecution.args.map { it.toJson() },
            event.methodExecution.result
        )
        logger.info(msg)
    }

    companion object {
        private const val USER_PLACEHOLDER = "#USER#"
        private const val METHOD_PLACEHOLDER = "#METHOD#"
        private const val ARGUMENTS_PLACEHOLDER = "#ARGS#"
        private const val RESULT_PLACEHOLDER = "#RESULT#"
        private const val LOG_MESSAGE = "'$USER_PLACEHOLDER' executed '$METHOD_PLACEHOLDER' with Arguments: [$ARGUMENTS_PLACEHOLDER] Result: $RESULT_PLACEHOLDER"

        fun formatMessage(
            username: String = "",
            methodName: String = "",
            arguments: List<String> = emptyList(),
            result: String = ""
        ): String =
            LOG_MESSAGE
                .replace(USER_PLACEHOLDER, username)
                .replace(METHOD_PLACEHOLDER, methodName)
                .replace(ARGUMENTS_PLACEHOLDER, arguments.joinToString(", "))
                .replace(RESULT_PLACEHOLDER, result)
    }

}