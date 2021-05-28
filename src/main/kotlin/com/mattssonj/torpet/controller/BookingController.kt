package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.BookingService
import com.mattssonj.torpet.persistence.Booking
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/bookings")
class BookingController(private val bookingService: BookingService) {

    @GetMapping
    fun getAllBookings(): List<Booking> = bookingService.getAllBookings()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewBooking(
        @RequestBody incomingBooking: IncomingBooking,
        @AuthenticationPrincipal user: User
    ): Booking {
        return bookingService.create(incomingBooking, user.username)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBooking(
        @PathVariable id: Long,
        @RequestBody incomingBooking: IncomingBooking,
        @AuthenticationPrincipal user: User
    ): Booking {
        return bookingService.update(id, incomingBooking, user.username)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBooking(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: User
    ) {
        bookingService.delete(id, user.username)
    }

}

data class IncomingBooking(val startDate: LocalDate, val endDate: LocalDate, val name: String, val message: String)