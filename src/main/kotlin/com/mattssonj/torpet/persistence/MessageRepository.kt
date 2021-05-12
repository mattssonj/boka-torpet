package com.mattssonj.torpet.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long> {
    fun findFirstByOrderByCreatedAtDesc(): Message?
}