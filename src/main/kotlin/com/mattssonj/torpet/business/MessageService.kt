package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.NoDataFoundException
import com.mattssonj.torpet.event.UserEventProducer
import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

const val DefaultMessage = "No message found"

@Service
class MessageService(private val messageRepository: MessageRepository) {

    @Transactional // This is needed when using @Lob together with postgresql
    fun getNewestMessage(): Message {
        return messageRepository.findFirstByOrderByCreatedAtDesc() ?: Message(null, DefaultMessage, "BokaTorpet")
    }

    @Transactional
    fun getMessagesAsPage(pageNumber: Int, size: Int): Page<Message> {
        val newestFirst = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        val page = messageRepository.findAll(newestFirst)
        if (page.isEmpty) throw NoDataFoundException("There were no page $pageNumber")
        else return page
    }

    @UserEventProducer
    fun create(message: String, writer: String): Message {
        return messageRepository.save(Message(message = message, writer = writer))
    }
}