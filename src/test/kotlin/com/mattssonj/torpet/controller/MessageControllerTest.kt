package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.MessageService
import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

private const val BASE_URL = "/api/messages"

@WebMvcTest
@AutoConfigureMockMvc
@WithMockUser
internal class MessageControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var messageService: MessageService

    @Test
    fun `Get newest message`() {
        val message = Message(null, "Test")
        `when`(messageService.getNewestMessage()).thenReturn(message)

        mockMvc.get("$BASE_URL/newest").andExpect {
            status { isOk() }
            jsonPath("$.message") { value(message.message) }
        }
    }
}