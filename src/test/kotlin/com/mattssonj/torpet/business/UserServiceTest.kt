package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.ForbiddenOperationException
import com.mattssonj.torpet.controller.NoDataFoundException
import com.mattssonj.torpet.persistence.UserInformation
import com.mattssonj.torpet.persistence.UserInformationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

private const val MOCK_USERNAME = "mock_user"
private const val MOCK_CREATOR = "mock_creator"

@DataJpaTest
internal class UserServiceTest {

    @Autowired private lateinit var userInformationRepository: UserInformationRepository
    private lateinit var sut: UserService

    @BeforeEach
    private fun setup() {
        sut = UserService(userInformationRepository)
    }

    @Test
    fun `Get user information by username`() {
        val persisted = persistMockUser()

        assertThat(sut.getUserInformation(persisted.username)).isEqualTo(persisted)
    }

    @Test
    fun `No user information is found for username throws exception`() {
        assertThrows<NoDataFoundException> { sut.getUserInformation(MOCK_USERNAME) }
    }

    @Test
    fun `Get all users user information`() {
        persistMockUser()

        assertThat(sut.getAllUserInformation()).hasSize(1)
    }

    @Test
    fun `Create new user information`() {
        val new = sut.createUserInformation(MOCK_USERNAME, MOCK_CREATOR)

        assertThat(userInformationRepository.findById(MOCK_USERNAME)).contains(new)
    }

    @Test
    fun `Update user information email is saved`() {
        val preStored = persistMockUser()

        val updated = sut.updateUserInformation(MOCK_USERNAME, preStored.copy(email = "abc@test.se"))

        assertThat(userInformationRepository.findById(MOCK_USERNAME)).contains(updated)
    }

    @Test
    fun `Update user information phone is saved`() {
        val preStored = persistMockUser()

        val updated = sut.updateUserInformation(MOCK_USERNAME, preStored.copy(phone = "0000000000"))

        assertThat(userInformationRepository.findById(MOCK_USERNAME)).contains(updated)
    }

    @Test
    fun `Update user information name is saved`() {
        val preStored = persistMockUser()

        val updated = sut.updateUserInformation(MOCK_USERNAME, preStored.copy(name = "first last"))

        assertThat(userInformationRepository.findById(MOCK_USERNAME)).contains(updated)
    }

    @Test
    fun `Update user information where user information username does not match provided username throws exception`() {
        val preStored = persistMockUser()

        assertThrows<ForbiddenOperationException> { sut.updateUserInformation("OTHER", preStored) }
    }

    private fun persistMockUser() = userInformationRepository.save(UserInformation(MOCK_USERNAME, MOCK_CREATOR))

}