package com.mattssonj.torpet.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.mattssonj.torpet.business.AdminService
import com.mattssonj.torpet.business.UsernameAlreadyExistsException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

private const val BASE_URL = "/api/admin"

@WebMvcTest(AdminController::class, AdminControllerTestConfiguration::class)
@AutoConfigureMockMvc
@WithMockUser(roles = ["ADMIN"])
class AdminControllerTest {

    @Autowired
    private lateinit var mockAdminService: AdminService
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Create new User`() {
        val newUser = IncomingNewUser("user", "password")

        every { mockAdminService.createUser(any(), any()) } returns newUser.username

        mockMvc.post("$BASE_URL/users") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = newUser.toJson()
        }.andExpect {
            status { isCreated() }
            jsonPath("$.username") { value(newUser.username) }
        }
    }

    @Test
    fun `Create new User, username already exists`() {
        val newUser = IncomingNewUser("user", "password")

        every { mockAdminService.createUser(any(), any()) } throws UsernameAlreadyExistsException(newUser.username)

        mockMvc.post("$BASE_URL/users") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = newUser.toJson()
        }.andExpect {
            status { isConflict() }
        }
    }

    @Test
    fun `Create new User, provided password is invalid`() {
        val newUser = IncomingNewUser("user", "")

        every { mockAdminService.createUser(any(), any()) } throws IllegalArgumentException("Provided password is bad")

        mockMvc.post("$BASE_URL/users") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = newUser.toJson()
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `Role User are not allowed to create user`() {
        val newUser = IncomingNewUser("user", "password")

        mockMvc.post("$BASE_URL/users") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = newUser.toJson()
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `Role admin is allowed to create users`() {
        val newUser = IncomingNewUser("user", "password")

        every { mockAdminService.createUser(any(), any()) } returns newUser.username

        mockMvc.post("$BASE_URL/users") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = newUser.toJson()
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    @WithMockUser(roles = ["DEVELOPER"])
    fun `Role developer is allowed to create users`() {
        val newUser = IncomingNewUser("user", "password")

        every { mockAdminService.createUser(any(), any()) } returns newUser.username

        mockMvc.post("$BASE_URL/users") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = newUser.toJson()
        }.andExpect {
            status { isCreated() }
        }
    }

}

private fun IncomingNewUser.toJson() = ObjectMapper()
    .registerKotlinModule()
    .registerModule(JavaTimeModule())
    .writeValueAsString(this)

@Configuration
class AdminControllerTestConfiguration {
    @Bean fun mockAdminService() = mockk<AdminService>(relaxed = true)
}