package com.mattssonj.torpet.event

import com.mattssonj.torpet.persistence.EventStore
import com.mattssonj.torpet.persistence.EventStoreRepository
import com.mattssonj.torpet.toJson
import org.springframework.stereotype.Component


@Component
class PersistenceUserEventHandler(private val repository: EventStoreRepository): UserEventHandler{

    override fun handle(event: UserEvent) {
        val entity = event.toEventStore()
        repository.save(entity)
    }

    private fun UserEvent.toEventStore(): EventStore = EventStore(generator = this.user, json = this.toJson())

}