package com.mattssonj.torpet.security

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest
@AutoConfigureMockMvc
internal class SecurityConfigurationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `Login page if not logged in`() {
        mockMvc.get("/").andExpect {
            status { is3xxRedirection() }
            redirectedUrlPattern("**/login")
        }
    }

    @Test
    fun `Logging in is authenticated`() {
        val login = formLogin().user("user").password("password").loginProcessingUrl("/login")
        mockMvc.perform(login)
            .andExpect {
                authenticated().withAuthenticationName("user")
            }
    }

    @Test
    @WithMockUser
    fun `Redirected when accessing login when logged in`() {
        mockMvc.get("/login").andExpect {
            status { is3xxRedirection() }
            redirectedUrl("/")
        }
    }
}