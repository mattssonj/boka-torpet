package com.mattssonj.torpet.business

import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

const val DefaultMessage = "No message found"

@Service
class MessageService(private val messageRepository: MessageRepository) {

    @Transactional // This is needed when using @Lob together with postgresql
    fun getNewestMessage(): Message {
        return messageRepository.findFirstByOrderByCreatedAtDesc() ?: Message(null, DefaultMessage, "BokaTorpet")
    }

    fun create(message: String, writer: String): Message {
        return messageRepository.save(Message(message = message, writer = writer))
    }
}