package com.mattssonj.torpet.business

import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Example

@DataJpaTest
internal class MessageServiceTest {

    @Autowired
    lateinit var messageRepository: MessageRepository

    @Test
    fun `No message exists`() {
        val newest = MessageService(messageRepository).getNewestMessage()

        assertThat(newest.message).isEqualTo(DefaultMessage)
    }

    @Test
    fun `Get newest message`() {
        val firstMessage = Message(null, "First", "user")
        val secondMessage = Message(null, "Second", "user")

        messageRepository.save(firstMessage)
        messageRepository.save(secondMessage)

        val newest = MessageService(messageRepository).getNewestMessage()

        assertThat(newest.message).isEqualTo(secondMessage.message)
    }

    @Test
    fun `Create message`() {
        val message = Message(null, "message", "writer")

        MessageService(messageRepository).create(message.message, message.writer)

        val persisted = messageRepository.findAll().first()
        assertThat(persisted.message).isEqualTo(message.message)
        assertThat(persisted.writer).isEqualTo(message.writer)
    }

}