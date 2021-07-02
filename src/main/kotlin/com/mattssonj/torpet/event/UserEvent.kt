package com.mattssonj.torpet.event

data class UserEvent(val user: String, val methodExecution: MethodExecution)

data class MethodExecution(val method: String, val args: List<MethodArgument>, val result: String)

data class MethodArgument(val name: String, val value: Any?)