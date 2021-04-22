package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.BookingService
import com.mattssonj.torpet.persistence.Booking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

private const val BASE_URL = "/api/bookings"

@WebMvcTest(BookingController::class)
@AutoConfigureMockMvc
@WithMockUser
class BookingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookingService: BookingService

    @Test
    fun `Get all bookings`() {
        val booking = Booking(1)
        `when`(bookingService.getAllBookings()).thenReturn(listOf(booking))

        mockMvc.get(BASE_URL).andExpect {
            status { isOk() }
            jsonPath("$.[0].id") { value(booking.id) }
        }
    }

}