package com.mattssonj.torpet.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface UserInformationRepository: JpaRepository<UserInformation, String>