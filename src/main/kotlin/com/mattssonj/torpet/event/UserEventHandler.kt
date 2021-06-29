package com.mattssonj.torpet.event

interface UserEventHandler {
    fun handle(event: UserEvent)
}