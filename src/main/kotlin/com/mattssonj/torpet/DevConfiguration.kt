package com.mattssonj.torpet

import com.mattssonj.torpet.business.AdminService
import com.mattssonj.torpet.business.UserService
import com.mattssonj.torpet.controller.IncomingNewUser
import com.mattssonj.torpet.persistence.Booking
import com.mattssonj.torpet.persistence.BookingRepository
import com.mattssonj.torpet.persistence.Message
import com.mattssonj.torpet.persistence.MessageRepository
import com.mattssonj.torpet.security.Roles
import com.mattssonj.torpet.security.encode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.annotation.PostConstruct

/**
 * This file is used to generate dev data to test things locally
 */

private const val exampleMessage =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis massa rutrum consectetur."

@Component
@Profile("dev | demo")
class DevConfiguration(
    private val messageRepository: MessageRepository,
    private val bookingRepository: BookingRepository,
    private val adminService: AdminService,
) {

    @PostConstruct
    fun generateDevData() {
        addMessage()
        addBookings()
        addUsers()
    }

    private fun addMessage() {
        (1..10)
            .toList()
            .map { Message(message = exampleMessage, writer = "writer") }
            .also { messageRepository.saveAll(it) }
    }

    private fun addBookings() {
        val old =
            Booking(startDate = LocalDate.now().minusMonths(1), endDate = LocalDate.now().minusMonths(1).plusDays(3))
        val current = Booking(
            startDate = LocalDate.now().minusDays(2),
            endDate = LocalDate.now().plusDays(1),
            booker = "user",
            name = "Midsommarfest",
            message = "Vi tänkte vara ca 15 pers och ha riktig brakfest. Andra får gärna komma upp samtidigt om de vill men det blir sena och högljudda kvällar"
        )
        val upcoming1 = Booking(
            startDate = LocalDate.now().plusDays(3),
            endDate = LocalDate.now().plusDays(5),
            booker = "TestUser2",
            name = "Test bokning 1",
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis massa rutrum consectetur."
        )
        val upcoming2 = Booking(
            startDate = LocalDate.now().plusDays(4),
            endDate = LocalDate.now().plusDays(6),
            booker = "TestUser1",
            name = "Lorem ipsum",
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis massa rutrum consectetur."
        )

        bookingRepository.save(old)
        bookingRepository.save(current)
        bookingRepository.save(upcoming1)
        bookingRepository.save(upcoming2)
    }

    private fun addUsers() {
        adminService.createUser(IncomingNewUser("TestUser1", "password"), "Developer")
        adminService.createUser(IncomingNewUser("TestUser2", "password"), "Developer")
        adminService.createUser(IncomingNewUser("TestUser3", "password"), "Developer")
        adminService.createUser(IncomingNewUser("utanadmin", "password"), "Developer")
    }

    @Autowired
    private fun addDevAdminUser(userDetailsManager: UserDetailsManager, userService: UserService) {
        val defaultUser = User.builder()
            .username("user")
            .password("password".encode())
            .roles(Roles.USER, Roles.DEV).build()

        userDetailsManager.createUser(defaultUser)
        userService.createUserInformation(defaultUser.username, "Developer")
    }

}