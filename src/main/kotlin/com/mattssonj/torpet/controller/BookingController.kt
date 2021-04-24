package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.BookingService
import com.mattssonj.torpet.business.IncomingBooking
import com.mattssonj.torpet.persistence.Booking
import org.springframework.format.annotation.DateTimeFormat
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
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate?,
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate,
        @AuthenticationPrincipal user: User
    ): Booking {
        val incomingBooking = IncomingBooking(startDate ?: LocalDate.now(), endDate, user.username)
        return bookingService.create(incomingBooking)
    }

}