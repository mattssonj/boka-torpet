package com.mattssonj.torpet.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.mattssonj.torpet.DataSourceMockConfiguration
import com.mattssonj.torpet.business.BookingService
import com.mattssonj.torpet.objectMapper
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
import org.springframework.test.web.servlet.*
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
        val upcomingBooking = Booking(1)
        val ongoingBooking = Booking(2)

        every { mockBookingService.getAllUpcomingBookings() } returns listOf(upcomingBooking)
        every { mockBookingService.getAllOngoingBookings() } returns listOf(ongoingBooking)

        mockMvc.get(BASE_URL).andExpect {
            status { isOk() }
            jsonPath("$.[0].id") { value(ongoingBooking.id) }
            jsonPath("$.[1].id") { value(upcomingBooking.id) }
        }
    }

    @Test
    fun `Get all ongoing bookings`() {
        val upcomingBooking = Booking(1)
        val ongoingBooking = Booking(2)

        every { mockBookingService.getAllUpcomingBookings() } returns listOf(upcomingBooking)
        every { mockBookingService.getAllOngoingBookings() } returns listOf(ongoingBooking)

        val jsonResponse = mockMvc.get("$BASE_URL?onlyOngoing=true").andExpect {
            status { isOk() }
        }.andReturn().response.contentAsString

        val bookings = objectMapper.readValue<List<Booking>>(jsonResponse)
        assertThat(bookings).hasSize(1)
        assertThat(bookings[0]).isEqualTo(ongoingBooking)
    }

    @Test
    fun `Get all upcoming bookings`() {
        val upcomingBooking = Booking(1)
        val ongoingBooking = Booking(2)

        every { mockBookingService.getAllUpcomingBookings() } returns listOf(upcomingBooking)
        every { mockBookingService.getAllOngoingBookings() } returns listOf(ongoingBooking)

        val jsonResponse = mockMvc.get("$BASE_URL?onlyUpcoming=true").andExpect {
            status { isOk() }
        }.andReturn().response.contentAsString

        val bookings = objectMapper.readValue<List<Booking>>(jsonResponse)
        assertThat(bookings).hasSize(1)
        assertThat(bookings[0]).isEqualTo(upcomingBooking)
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

    @Test
    fun `Update booking`() {
        val incomingBooking = IncomingBooking(LocalDate.now(), LocalDate.now().plusDays(2), "name", "message")
        val idToBeUpdated = 1L

        every { mockBookingService.update(idToBeUpdated, incomingBooking, any()) } returns mockk(relaxed = true)

        mockMvc.put("$BASE_URL/$idToBeUpdated") {
            with(csrf())
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = incomingBooking.toJson()
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `Update booking that belongs to someone else returns 403`() {
        val incomingBooking = IncomingBooking(LocalDate.now(), LocalDate.now().plusDays(2), "name", "message")
        val idToBeUpdated = 1L

        every { mockBookingService.update(any(), any(), any()) } throws ForbiddenOperationException("")

        mockMvc.put("$BASE_URL/$idToBeUpdated") {
            with(csrf())
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = incomingBooking.toJson()
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    fun `Delete booking`() {
        val idToBeDeleted = 1L

        every { mockBookingService.delete(any(), any()) } returns Unit

        mockMvc.delete("$BASE_URL/$idToBeDeleted") {
            with(csrf())
        }.andExpect {
            status { isNoContent() }
        }
    }

    @Test
    fun `Delete booking that belongs to someone else returns 403`() {
        val idToBeDeleted = 1L

        every { mockBookingService.delete(any(), any()) } throws ForbiddenOperationException("")

        mockMvc.delete("$BASE_URL/$idToBeDeleted") {
            with(csrf())
        }.andExpect {
            status { isForbidden() }
        }
    }

}

@TestConfiguration
class BookingControllerTestConfiguration {
    @Bean fun mockBookingService() = mockk<BookingService>(relaxed = true)
}