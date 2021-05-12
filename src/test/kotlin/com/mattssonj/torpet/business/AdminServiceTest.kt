package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.IncomingNewUser
import com.mattssonj.torpet.persistence.UserInformationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.provisioning.UserDetailsManager

private const val ADMIN = "tester"
private const val USERNAME = "newuser"

@SpringBootTest
internal class AdminServiceTest {

    @Autowired lateinit var userDetailsManager: UserDetailsManager
    @Autowired lateinit var userInformationRepository: UserInformationRepository
    @Autowired lateinit var sut: AdminService

    @BeforeEach
    fun prepare() {
        userDetailsManager.deleteUser(USERNAME)
        userInformationRepository.deleteAll()
    }

    @Test
    fun `Created user is saved`() {
        val incomingNewUser = IncomingNewUser(USERNAME, "password")

        sut.createUser(incomingNewUser, ADMIN)

        assertThat(userDetailsManager.userExists(incomingNewUser.username))
    }

    @Test
    fun `Created user got empty user information`() {
        val incomingNewUser = IncomingNewUser(USERNAME, "password")

        sut.createUser(incomingNewUser, ADMIN)

        assertThat(userInformationRepository.findById(incomingNewUser.username)).isNotEmpty
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "     "])
    fun `Username cannot be empty or blank`(username: String) {
        val incomingNewUser = IncomingNewUser(username, "password")

        assertThrows<IllegalArgumentException> { sut.createUser(incomingNewUser, ADMIN) }
    }

    @Test
    fun `Username should not contain any spaces`() {
        val incomingNewUser = IncomingNewUser("förnamn efternamn", "password")

        assertThrows<IllegalArgumentException> { sut.createUser(incomingNewUser, ADMIN) }
    }

    @Test
    fun `New user username already exists`() {
        val incomingNewUser = IncomingNewUser(USERNAME, "password")

        sut.createUser(incomingNewUser, ADMIN)

        assertThrows<UsernameAlreadyExistsException> { sut.createUser(incomingNewUser, ADMIN) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["asdas", "", "           ", ])
    fun `Password need to fill certain criteria`(badPassword: String) {
        val incomingNewUser = IncomingNewUser(USERNAME, badPassword)

        assertThrows<IllegalArgumentException> { sut.createUser(incomingNewUser, ADMIN) }
    }

    @Test
    fun `Get all registered users`() {
        val incomingNewUser = IncomingNewUser(USERNAME, "password")
        sut.createUser(incomingNewUser, ADMIN)

        val registeredUsers = sut.getAllRegisteredUsers(ADMIN)

        assertThat(registeredUsers).hasSize(1)
        assertThat(registeredUsers.first().username).isEqualTo(USERNAME)
    }

    @Test
    fun `Able to delete users that specific admin created`() {
        val incomingNewUser = IncomingNewUser(USERNAME, "password")
        sut.createUser(incomingNewUser, ADMIN)

        val registeredUser = sut.getAllRegisteredUsers(ADMIN).first()

        assertThat(registeredUser.ableToDelete).isTrue
    }

    @Test
    fun `Able to delete is false for other admins`() {
        val incomingNewUser = IncomingNewUser(USERNAME, "password")
        sut.createUser(incomingNewUser, ADMIN)

        val registeredUser = sut.getAllRegisteredUsers("OtherAdmin").first()

        assertThat(registeredUser.ableToDelete).isFalse
    }

}