package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.IncomingBooking
import com.mattssonj.torpet.persistence.Booking
import com.mattssonj.torpet.persistence.BookingRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BookingService(private val bookingRepository: BookingRepository) {

    fun getAllBookings(): List<Booking> = bookingRepository.findAllByStartDateIsAfterOrderByStartDate(LocalDate.now().minusDays(1))
    fun create(incomingBooking: IncomingBooking, booker: String): Booking {
        verify(incomingBooking)
        val booking = Booking(null, incomingBooking.startDate, incomingBooking.endDate, booker)
        return bookingRepository.save(booking)
    }

    private fun verify(incomingBooking: IncomingBooking) {
        if (incomingBooking.startDate.isBefore(LocalDate.now())) throw IllegalArgumentException("StartDate cannot be earlier then today")
        if (incomingBooking.endDate.isBefore(incomingBooking.startDate)) throw IllegalArgumentException("StartDate needs to be before EndDate")
    }

}