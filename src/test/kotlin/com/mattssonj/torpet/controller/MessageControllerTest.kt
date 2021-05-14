package com.mattssonj.torpet.controller

import com.mattssonj.torpet.DataSourceMockConfiguration
import com.mattssonj.torpet.business.MessageService
import com.mattssonj.torpet.persistence.Message
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

private const val BASE_URL = "/api/messages"

@WebMvcTest(MessageController::class)
@AutoConfigureMockMvc
@WithMockUser
@Import(MessageControllerTestConfiguration::class, DataSourceMockConfiguration::class)
internal class MessageControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var messageService: MessageService

    @Test
    fun `Get newest message`() {
        val message = Message(null, "Test", "writer")
        every { messageService.getNewestMessage() } returns message

        mockMvc.get("$BASE_URL/newest").andExpect {
            status { isOk() }
            jsonPath("$.message") { value(message.message) }
        }
    }

    @Test
    fun `Create new message`() {
        val message = "message body"

        mockMvc.post(BASE_URL) {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            content = "{\"message\": \"$message\"}"
        }.andExpect {
            status { isCreated() }
        }
    }
}

@TestConfiguration
class MessageControllerTestConfiguration {
    @Bean fun mockMessageService() = mockk<MessageService>(relaxed = true)
}