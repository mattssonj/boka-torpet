package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.BookingService
import com.mattssonj.torpet.persistence.Booking
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
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
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDate

private const val BASE_URL = "/api/bookings"

@WebMvcTest(BookingController::class, BookingControllerTestConfiguration::class)
@AutoConfigureMockMvc
@WithMockUser
class BookingControllerTest {

    @Autowired private lateinit var mockBookingService: BookingService
    @Autowired private lateinit var mockMvc: MockMvc

    @Test
    fun `Get all bookings`() {
        val booking = Booking(1)

        every { mockBookingService.getAllBookings() } returns listOf(booking)

        mockMvc.get(BASE_URL).andExpect {
            status { isOk() }
            jsonPath("$.[0].id") { value(booking.id) }
        }
    }

    @Test
    fun `Creating booking with provided startDate and endDate`() {
        val startDate = LocalDate.now().plusDays(1)
        val endDate = LocalDate.now().plusDays(2)

        every { mockBookingService.create(any()) } returns Booking(1, startDate, endDate)

        mockMvc.post(BASE_URL) {
            accept = MediaType.APPLICATION_JSON
            with(csrf()) // This line is important to make post request work
            param("startDate", startDate.toString())
            param("endDate", endDate.toString())
        }.andExpect {
            status { isCreated() }
            jsonPath("$.startDate") {value(startDate.toString())}
        }
    }

    @Test
    fun `Creating booking without start date defaults to today`() {
        val today = LocalDate.now()
        val endDate = LocalDate.now().plusDays(2)

        every { mockBookingService.create(any()) } returns Booking(1, today, endDate)

        mockMvc.post(BASE_URL) {
            accept = MediaType.APPLICATION_JSON
            with(csrf()) // This line is important to make post request work
            param("endDate", LocalDate.now().plusDays(2).toString())
        }.andExpect {
            status { isCreated() }
        }

        verify { mockBookingService.create(withArg { assertThat(it.startDate).isEqualTo(today) }) }
    }

    @Test
    fun `Creating booking with invalid data returns bad request`() {
        val startDate = LocalDate.now()
        val endDate = LocalDate.now().minusDays(2)

        every { mockBookingService.create(any()) } throws IllegalArgumentException()

        mockMvc.post(BASE_URL) {
            accept = MediaType.APPLICATION_JSON
            with(csrf()) // This line is important to make post request work
            param("startDate", startDate.toString())
            param("endDate", endDate.toString())
        }.andExpect {
            status { isBadRequest() }
        }
    }

}

@Configuration
class BookingControllerTestConfiguration {

    @Bean
    fun mockBookingService() = mockk<BookingService>(relaxed = true)

}