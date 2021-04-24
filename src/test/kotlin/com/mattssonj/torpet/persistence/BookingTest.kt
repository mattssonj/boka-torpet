package com.mattssonj.torpet.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate

@DataJpaTest
class BookingTest {

    @Autowired private lateinit var bookingRepository: BookingRepository

    @Test
    fun `Inserting two equal bookings creates stores two bookings`() {
        val booking1 = Booking(startDate = LocalDate.now().plusDays(1), endDate = LocalDate.now().plusDays(2))
        val booking2 = Booking(startDate = LocalDate.now().plusDays(1), endDate = LocalDate.now().plusDays(2))

        bookingRepository.save(booking1)
        bookingRepository.save(booking2)

        assertThat(bookingRepository.findAllByStartDateIsAfterOrderByStartDate(LocalDate.now())).hasSize(2)
    }

}