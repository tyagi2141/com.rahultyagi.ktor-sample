package com.rahultyagi

import com.rahultyagi.dao.DatabaseFactory
import com.rahultyagi.di.configureDi
import com.rahultyagi.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    DatabaseFactory.init()
    configureSecurity()
    configureDi()
    configureRouting()
}
