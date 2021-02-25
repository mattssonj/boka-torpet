package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.MessageService
import com.mattssonj.torpet.persistence.Message
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/messages")
class MessageController(private val messageService: MessageService) {

    @GetMapping("/newest")
    fun newestMessage(): Message {
        return messageService.getNewestMessage()
    }
}