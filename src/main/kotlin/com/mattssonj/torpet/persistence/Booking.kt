package com.mattssonj.torpet.persistence

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
    var booker: String = "",
    var name: String = "",
    @Lob var message: String = "",
) {

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    var editedAt: LocalDateTime = LocalDateTime.now()

}