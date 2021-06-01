package com.mattssonj.torpet.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface BookingRepository: JpaRepository<Booking, Long> {

    fun findAllByStartDateIsAfterOrderByStartDate(startDate: LocalDate): List<Booking>
    fun findAllByStartDateIsOrderByEndDate(startDate: LocalDate = LocalDate.now()): List<Booking>

}