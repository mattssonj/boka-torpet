package com.mattssonj.torpet.controller

import com.mattssonj.torpet.business.UserService
import com.mattssonj.torpet.persistence.UserInformation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping("/current")
    fun getCurrentUser(@AuthenticationPrincipal user: User): UserInformation {
        return userService.getUserInformation(user.username)
    }

    @PutMapping("/current")
    fun updateCurrentUserUserInformation(
        @AuthenticationPrincipal user: User,
        @RequestBody userInformation: UserInformation,
    ): UserInformation {
        return userService.updateUserInformation(user.username, userInformation)
    }

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): UserInformation {
        return userService.getUserInformation(username)
    }



}