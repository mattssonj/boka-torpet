package com.mattssonj.torpet.controller

import com.mattssonj.torpet.DataSourceMockConfiguration
import com.mattssonj.torpet.business.UserService
import com.mattssonj.torpet.persistence.UserInformation
import com.mattssonj.torpet.toJson
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
import org.springframework.test.web.servlet.put

private const val BASE_URL = "/api/users"
private const val MOCK_USERNAME = "mock_username"

@WebMvcTest(UserController::class)
@AutoConfigureMockMvc
@WithMockUser
@Import(UserControllerTestConfiguration::class, DataSourceMockConfiguration::class)
internal class UserControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var userService: UserService

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    fun `Get current user returns user information about logged in user`() {
        val userInformation = UserInformation(MOCK_USERNAME, "test")

        every { userService.getUserInformation(MOCK_USERNAME) } returns userInformation

        mockMvc.get("$BASE_URL/current").andExpect {
            status { isOk() }
            jsonPath("$.username") { value(userInformation.username) }
            jsonPath("$.createdBy") { value(userInformation.createdBy) }
        }
    }

    @Test
    fun `No user information is found for current user returns 404`() {
        every { userService.getUserInformation(any()) } throws NoDataFoundException("")

        mockMvc.get("$BASE_URL/current").andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `Get user information for specific user`() {
        val userInformation = UserInformation(MOCK_USERNAME, "test")

        every { userService.getUserInformation(MOCK_USERNAME) } returns userInformation

        mockMvc.get("$BASE_URL/$MOCK_USERNAME").andExpect {
            status { isOk() }
            jsonPath("$.username") { value(userInformation.username) }
            jsonPath("$.createdBy") { value(userInformation.createdBy) }
        }
    }

    @Test
    fun `No user information is found for specific user returns 404`() {
        every { userService.getUserInformation(any()) } throws NoDataFoundException("")

        mockMvc.get("$BASE_URL/$MOCK_USERNAME").andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    fun `Update current user user information`() {
        val userInformation = UserInformation(MOCK_USERNAME, "test", "abc@test.se", "0000000000")

        every { userService.updateUserInformation(MOCK_USERNAME, userInformation) } returns userInformation

        mockMvc.put("$BASE_URL/current") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            content = userInformation.toJson()
        }.andExpect {
            status { isOk() }
            jsonPath("$.phone") { value(userInformation.phone) }
            jsonPath("$.email") { value(userInformation.email) }
        }
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    fun `Update current user user information with invalid json returns 400`() {
        val userInformation = UserInformation(MOCK_USERNAME, "test", "abc@test.se", "0000000000")
        val invalidJson = userInformation.toJson().replace("username", "otherFieldName")

        mockMvc.put("$BASE_URL/current") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            content = invalidJson
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    fun `Update current user user information with other username then current user return 403`() {
        val userInformation = UserInformation("OTHER", "test", "abc@test.se", "0000000000")

        every { userService.updateUserInformation(MOCK_USERNAME, userInformation) } throws ForbiddenOperationException("")

        mockMvc.put("$BASE_URL/current") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            content = userInformation.toJson()
        }.andExpect {
            status { isForbidden() }
        }
    }

}

@TestConfiguration
internal class UserControllerTestConfiguration {
    @Bean fun userServiceMock() = mockk<UserService>(relaxed = true)
}