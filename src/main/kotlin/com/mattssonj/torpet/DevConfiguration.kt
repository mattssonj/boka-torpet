package com.mattssonj.torpet

import com.mattssonj.torpet.persistence.Booking
import com.mattssonj.torpet.persistence.BookingRepository
import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.annotation.PostConstruct

/**
 * This file is used to generate dev data to test things locally
 */

@Component
@Profile("dev")
class DevConfiguration(private val messageRepository: MessageRepository,
                       private val bookingRepository: BookingRepository) {

    @PostConstruct
    fun generateDevData() {
        addMessage()
        addBookings()
    }

    private fun addMessage() {
        val message = Message(message = "Torpet är rent och prydligt. Fortsätta hålla det så!")
        messageRepository.save(message)
    }

    private fun addBookings() {
        val old = Booking(startDate = LocalDate.now().minusMonths(1), endDate = LocalDate.now().minusMonths(1).plusDays(3))
        val current = Booking(startDate = LocalDate.now().minusDays(2), endDate = LocalDate.now().plusDays(1))
        val upcoming1 = Booking(startDate = LocalDate.now().plusDays(3), endDate = LocalDate.now().plusDays(5), booker = "Joakim")
        val upcoming2 = Booking(startDate = LocalDate.now().plusDays(4), endDate = LocalDate.now().plusDays(6), booker = "Joakim")

        bookingRepository.save(old)
        bookingRepository.save(current)
        bookingRepository.save(upcoming1)
        bookingRepository.save(upcoming2)
    }
}