package com.mattssonj.torpet.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface EventStoreRepository: JpaRepository<EventStore, Long>