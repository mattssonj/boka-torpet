package com.mattssonj.torpet.controller

import com.mattssonj.torpet.DataSourceMockConfiguration
import com.mattssonj.torpet.business.BookingService
import com.mattssonj.torpet.persistence.Booking
import com.mattssonj.torpet.toJson
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
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
import java.time.LocalDate

private const val BASE_URL = "/api/bookings"

@WebMvcTest(BookingController::class)
@AutoConfigureMockMvc
@WithMockUser
@Import(BookingControllerTestConfiguration::class, DataSourceMockConfiguration::class)
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
        val incomingBooking = IncomingBooking(LocalDate.now(), LocalDate.now().plusDays(2), "name", "message")

        every { mockBookingService.create(any(), any()) } returns Booking(1, incomingBooking.startDate, incomingBooking.endDate)

        mockMvc.post(BASE_URL) {
            with(csrf()) // This line is important to make post request work
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = incomingBooking.toJson()
        }.andExpect {
            status { isCreated() }
        }

        verify { mockBookingService.create(withArg { assertThat(it).isEqualTo(incomingBooking) }, any()) }
    }

    @Test
    fun `Creating booking with invalid data returns bad request`() {
        val incomingBooking = IncomingBooking(LocalDate.now(), LocalDate.now().minusDays(2), "name", "message")

        every { mockBookingService.create(any(), any()) } throws IllegalArgumentException()

        mockMvc.post(BASE_URL) {
            with(csrf()) // This line is important to make post request work
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = incomingBooking.toJson()
        }.andExpect {
            status { isBadRequest() }
        }
    }

}

@TestConfiguration
class BookingControllerTestConfiguration {
    @Bean fun mockBookingService() = mockk<BookingService>(relaxed = true)
}