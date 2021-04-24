package com.mattssonj.torpet.business

import com.mattssonj.torpet.persistence.Booking
import com.mattssonj.torpet.persistence.BookingRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate

@DataJpaTest
internal class BookingServiceTest {

    @Autowired
    private lateinit var bookingRepository: BookingRepository

    @Test
    fun `No bookings exist`() {
        val bookingService = BookingService(bookingRepository)

        bookingRepository.deleteAll()

        assertThat(bookingService.getAllBookings()).isEmpty()
    }

    @Test
    fun `Booking exist`() {
        val bookingService = BookingService(bookingRepository)

        val booking = bookingRepository.save(Booking(null, LocalDate.now()))

        assertThat(bookingService.getAllBookings()).containsOnly(booking)
    }

    @Test
    fun `Order multiple bookings by next upcoming`() {
        val next = Booking(null, LocalDate.now(), LocalDate.now().plusDays(1))
        val after = Booking(null, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3))

        bookingRepository.save(after)
        bookingRepository.save(next)

        val bookingService = BookingService(bookingRepository)
        assertThat(bookingService.getAllBookings()).containsExactly(next, after)
    }

    @Test
    fun `Dont return bookings that passed`() {
        val passed = Booking(null, LocalDate.now().minusDays(5), LocalDate.now().minusDays(4))

        bookingRepository.save(passed)

        val bookingService = BookingService(bookingRepository)
        assertThat(bookingService.getAllBookings()).isEmpty()
    }

    @Test
    fun `Create new booking`() {
        val incomingBooking = IncomingBooking(LocalDate.now(), LocalDate.now().plusDays(2), "user")

        val booking = BookingService(bookingRepository).create(incomingBooking)

        assertThat(booking.startDate).isEqualTo(incomingBooking.startDate)
        assertThat(booking.endDate).isEqualTo(incomingBooking.endDate)
        assertThat(booking.booker).isEqualTo(incomingBooking.booker)
    }

    @Test
    fun `Dont save outdated incoming booking`() {
        val incomingBooking = IncomingBooking(LocalDate.now().minusDays(2), LocalDate.now(), "user")

        assertThatIllegalArgumentException().isThrownBy { BookingService(bookingRepository).create(incomingBooking) }
    }

    @Test
    fun `Dont save when start date is before end date`() {
        val incomingBooking = IncomingBooking(LocalDate.now(), LocalDate.now().minusDays(3), "user")

        assertThatIllegalArgumentException().isThrownBy { BookingService(bookingRepository).create(incomingBooking) }
    }

}