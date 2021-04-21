package com.mattssonj.torpet.business

import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

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
        val firstMessage = Message(null, "First")
        val secondMessage = Message(null, "Second")

        messageRepository.save(firstMessage)
        messageRepository.save(secondMessage)

        val newest = MessageService(messageRepository).getNewestMessage()

        assertThat(newest.message).isEqualTo(secondMessage.message)
    }

}