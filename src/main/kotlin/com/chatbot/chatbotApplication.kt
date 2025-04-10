package com.chatbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class chatbotApplication

fun main(args: Array<String>) {
    runApplication<chatbotApplication>(*args)
}
