package com.mattssonj.torpet.persistence

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class EventStore(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var generator: String,
    @Lob var json: String
) {
    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now()
}