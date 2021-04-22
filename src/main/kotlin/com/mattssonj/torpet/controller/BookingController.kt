package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.BookingService
import com.mattssonj.torpet.persistence.Booking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/bookings")
class BookingController(private val bookingService: BookingService) {

    @GetMapping
    fun getAllBookings(): List<Booking> = bookingService.getAllBookings()

}