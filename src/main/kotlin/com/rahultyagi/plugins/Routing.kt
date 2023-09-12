package com.rahultyagi.plugins

import com.rahultyagi.route.authRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        authRouting()
    }
}
/*

routing {
    authRouting()
}*/
