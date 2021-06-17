package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.NoDataFoundException
import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
    fun `Get all messages as a Page when number of messages fit into size`() {
        val firstMessage = Message(null, "First", "user")
        val secondMessage = Message(null, "Second", "user")

        messageRepository.save(firstMessage)
        messageRepository.save(secondMessage)

        val page = MessageService(messageRepository).getMessagesAsPage(0,2)

        assertThat(page.totalElements).isEqualTo(2)
        assertThat(page.content).containsExactlyInAnyOrder(firstMessage, secondMessage)
    }

    @Test
    fun `Get multiple pages when messages dont fit into size`() {
        val firstMessage = Message(null, "First", "user")
        val secondMessage = Message(null, "Second", "user")

        messageRepository.save(firstMessage)
        messageRepository.save(secondMessage)

        val page = MessageService(messageRepository).getMessagesAsPage(0,1)

        assertThat(page.totalPages).isEqualTo(2)
        assertThat(page.hasNext()).isTrue
    }

    @Test
    fun `Page elements are sorted by newest first`() {
        val firstMessage = Message(null, "First", "user")
        val secondMessage = Message(null, "Second", "user")

        messageRepository.save(firstMessage)
        messageRepository.save(secondMessage)
        val msgService = MessageService(messageRepository)

        val firstPage = msgService.getMessagesAsPage(0,1)
        val secondPage = msgService.getMessagesAsPage(1,1)

        assertThat(firstPage.content).containsExactly(secondMessage)
        assertThat(secondPage.content).containsExactly(firstMessage)
    }

    @Test
    fun `Throw exception when page does not contain elements`() {
        assertThrows<NoDataFoundException> { MessageService(messageRepository).getMessagesAsPage(0, 1) }
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