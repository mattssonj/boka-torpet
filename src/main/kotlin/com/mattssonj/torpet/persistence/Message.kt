package com.mattssonj.torpet.persistence

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Message(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var message: String,
    var writer: String
) {

    @CreationTimestamp
    var createdTimestamp: LocalDateTime = LocalDateTime.now()
    @UpdateTimestamp
    var editedTimestamp: LocalDateTime = LocalDateTime.now()

}