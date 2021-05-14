package com.mattssonj.torpet.business

import com.mattssonj.torpet.controller.ForbiddenOperationException
import com.mattssonj.torpet.controller.NoDataFoundException
import com.mattssonj.torpet.persistence.UserInformation
import com.mattssonj.torpet.persistence.UserInformationRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userInformationRepository: UserInformationRepository) {

    fun getUserInformation(username: String): UserInformation {
        return userInformationRepository
            .findById(username)
            .orElseThrow { NoDataFoundException("Unable to find user information for $username") }
    }

    fun getAllUserInformation(): List<UserInformation> =
        userInformationRepository.findAll()

    fun createUserInformation(username: String, creator: String) =
        userInformationRepository.save(UserInformation(username, creator))

    fun updateUserInformation(username: String, userInformation: UserInformation): UserInformation {
        if (!username.equals(userInformation.username, false))
            throw ForbiddenOperationException("$username cannot update UserInformation for ${userInformation.username}")
        return userInformationRepository.save(userInformation)
    }

}