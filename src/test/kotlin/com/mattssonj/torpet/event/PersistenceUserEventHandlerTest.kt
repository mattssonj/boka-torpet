package com.mattssonj.torpet.event

import com.mattssonj.torpet.persistence.EventStoreRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class PersistenceUserEventHandlerTest {

    @Test
    fun `UserEvents are stored in db`() {
        val mockPersistence = mockk<EventStoreRepository>()
        every { mockPersistence.save(any()) } returns mockk()

        val handler = PersistenceUserEventHandler(mockPersistence)
        handler.handle(UserEvent("", MethodExecution("", emptyList(), "")))

        verify(exactly = 1) { mockPersistence.save(any()) }
    }

}