package com.mattssonj.torpet.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class MessageTest {

    @Autowired lateinit var messageRepository: MessageRepository

    @Test
    fun `Saved entity gets an ID`() {
        val message = Message(null, "Hello", "writer")
        val saved = messageRepository.save(message)

        assertThat(saved.id).isNotNull()
    }

    @Test
    fun `Saved entity gets createdTimestamp`() {
        val message = Message(null, "Hello", "writer")
        val saved = messageRepository.save(message)

        assertThat(saved.createdTimestamp).isNotNull()
    }

    @Test
    fun `Edited entity gets editedTimestamp`() {
        val message = Message(null, "Hello", "writer")
        val saved = messageRepository.save(message)
        val updated = messageRepository.save(saved.apply { this.message = "Bye" })

        assertThat(updated.editedTimestamp).isNotNull()
        assertThat(updated.editedTimestamp).isNotEqualTo(updated.createdTimestamp)
    }

}