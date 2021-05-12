package com.mattssonj.torpet.persistence

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserInformation(
    @Id val username: String,
    val createdBy: String,
    var email: String = "",
    var phone: String = "",
    var name: String = "",
) {

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now()
    @UpdateTimestamp
    var editedAt: LocalDateTime = LocalDateTime.now()
}