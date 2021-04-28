package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.MessageService
import com.mattssonj.torpet.persistence.Message
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/messages")
class MessageController(private val messageService: MessageService) {

    @GetMapping("/newest")
    fun newestMessage(): Message {
        return messageService.getNewestMessage()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMessage(
        @RequestBody incomingMessage: IncomingMessage,
        @AuthenticationPrincipal user: User
    ): Message {
        return messageService.create(incomingMessage.message, user.username)
    }


}

data class IncomingMessage(val message: String)