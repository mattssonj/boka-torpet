package com.mattssonj.torpet

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.zaxxer.hikari.HikariDataSource
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class DataSourceMockConfiguration {
    /**
     * We need to return HikariDataSource instead of DataSource because of bug in mockk framework
     * See issue: https://github.com/mockk/mockk/issues/280
     */
    @Bean fun mockDataSource(): HikariDataSource = mockk(relaxed = true)
}

internal fun Any.toJson(): String = ObjectMapper()
    .registerKotlinModule()
    .registerModule(JavaTimeModule())
    .writeValueAsString(this)