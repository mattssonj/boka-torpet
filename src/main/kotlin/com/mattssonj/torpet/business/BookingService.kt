package com.mattssonj.torpet.business

import com.mattssonj.torpet.persistence.Booking
import com.mattssonj.torpet.persistence.BookingRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BookingService(private val bookingRepository: BookingRepository) {

    fun getAllBookings(): List<Booking> = bookingRepository.findAllByStartDateIsAfterOrderByStartDate(LocalDate.now().minusDays(1))

}