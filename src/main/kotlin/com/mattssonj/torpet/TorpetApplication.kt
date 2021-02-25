package com.mattssonj.torpet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TorpetApplication

fun main(args: Array<String>) {
	runApplication<TorpetApplication>(*args)
}
