package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.ForbiddenOperationException
import com.mattssonj.torpet.controller.IncomingBooking
import com.mattssonj.torpet.controller.NoDataFoundException
import com.mattssonj.torpet.persistence.Booking
import com.mattssonj.torpet.persistence.BookingRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BookingService(private val bookingRepository: BookingRepository) {

    fun getAllBookings(): List<Booking> =
        bookingRepository.findAllByStartDateIsAfterOrderByStartDate(LocalDate.now().minusDays(1))

    fun create(incomingBooking: IncomingBooking, booker: String): Booking {
        verify(incomingBooking)

        val booking = Booking(
            id = null,
            booker = booker,
            name = incomingBooking.name,
            message = incomingBooking.message,
            startDate = incomingBooking.startDate,
            endDate = incomingBooking.endDate,
        )

        return bookingRepository.save(booking)
    }

    fun update(id: Long, incomingBooking: IncomingBooking, booker: String): Booking {
        verify(incomingBooking)
        val current = findBookingByIdOrThrow(id)
        if (current.booker != booker) throw ForbiddenOperationException("$booker is not allowed to update booking $id")
        val updated = current.apply {
            startDate = incomingBooking.startDate
            endDate = incomingBooking.endDate
            name = incomingBooking.name
            message = incomingBooking.message
        }
        return bookingRepository.save(updated)
    }

    fun delete(bookingId: Long, booker: String) {
        val booking = findBookingByIdOrThrow(bookingId)
        if (booking.booker != booker) throw ForbiddenOperationException("$booker is not allowed to update booking $bookingId")
        bookingRepository.delete(booking)
    }

    private fun verify(incomingBooking: IncomingBooking) {
        if (incomingBooking.startDate.isBefore(LocalDate.now())) throw IllegalArgumentException("StartDate cannot be earlier then today")
        if (incomingBooking.endDate.isBefore(incomingBooking.startDate)) throw IllegalArgumentException("StartDate needs to be before EndDate")
    }

    private fun findBookingByIdOrThrow(bookingId: Long) = bookingRepository.findById(bookingId)
        .orElseThrow { NoDataFoundException("Unable to find Booking with id = $bookingId") }
}