package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.MessageService
import com.mattssonj.torpet.persistence.Message
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

private const val BASE_URL = "/api/messages"

@WebMvcTest(MessageController::class, MessageControllerTestConfiguration::class)
@AutoConfigureMockMvc
@WithMockUser
internal class MessageControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var messageService: MessageService

    @Test
    fun `Get newest message`() {
        val message = Message(null, "Test")
        every { messageService.getNewestMessage() } returns message

        mockMvc.get("$BASE_URL/newest").andExpect {
            status { isOk() }
            jsonPath("$.message") { value(message.message) }
        }
    }
}

@Configuration
class MessageControllerTestConfiguration {
    @Bean
    fun mockMessageService() = mockk<MessageService>(relaxed = true)
}