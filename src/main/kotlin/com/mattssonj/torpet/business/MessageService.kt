package com.mattssonj.torpet.business

import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import org.springframework.stereotype.Service

const val DefaultMessage = "No message found"

@Service
class MessageService(private val messageRepository: MessageRepository) {

    fun getNewestMessage(): Message {
        return messageRepository.findFirstByOrderByCreatedAtDesc() ?: Message(null, DefaultMessage, "BokaTorpet")
    }

    fun create(message: String, writer: String): Message {
        return messageRepository.save(Message(message = message, writer = writer))
    }
}