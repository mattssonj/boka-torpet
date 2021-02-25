package com.mattssonj.torpet.business

import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import org.springframework.stereotype.Service

interface MessageService {

    fun getNewestMessage(): Message

}

const val DefaultMessage = "No message found"

@Service
class MessageServiceImpl(private val messageRepository: MessageRepository) : MessageService {

    override fun getNewestMessage(): Message {
        return messageRepository.findFirstByOrderByCreatedTimestampDesc() ?: Message(null, DefaultMessage)
    }
}