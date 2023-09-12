package com.rahultyagi.route

import com.rahultyagi.repository.user.UserRepository
import com.rahultyagi.users.AuthResponse

import com.rahultyagi.users.SignInParams
import com.rahultyagi.users.SignUpParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.authRouting() {
    val repository by inject<UserRepository>()

    route("/") {
        get {
            call.respondText { "Hello tyagi" }
        }
    }

    route(path = "/signup") {
        post {

            val params = call.receiveNullable<SignUpParams>()

            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )

                return@post
            }

            val result = repository.signUp(params = params)
            call.respond(
                status = result.code,
                message = result.data
            )

        }
    }

    route("/signIn") {
        post {
            val params = call.receiveNullable<SignInParams>()

            if (params == null) {
                call.respond(status = HttpStatusCode.BadRequest, message = "invalid credential")
                return@post
            } else {
                val result = repository.signIn(params)
                call.respond(status = HttpStatusCode.OK, message = result.data)
            }
        }
    }


}