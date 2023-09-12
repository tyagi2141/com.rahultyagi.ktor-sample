package com.rahultyagi.repository.user

import com.rahultyagi.plugins.generateToken
import com.rahultyagi.security.hashPassword
import com.rahultyagi.users.*
import com.rahultyagi.util.Response
import io.ktor.http.*

class UserRepositoryImpl(val userDao: UserDao) : UserRepository {
    override suspend fun signUp(params: SignUpParams): Response<AuthResponse> {
        return if (userAlreadyExist(params.email)) {
            Response.Error(
                code = HttpStatusCode.Conflict, data = AuthResponse(
                    errorMessage = "A user with this email already exists!"
                )
            )
        } else {
            val insertedUser = userDao.insert(params = params)
            if (insertedUser == null) {
                Response.Error(
                    code = HttpStatusCode.InternalServerError,
                    data = AuthResponse(errorMessage = "unable to insert signup data")
                )
            } else {
                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = insertedUser.id,
                            name = insertedUser.name,
                            bio = insertedUser.bio,
                            avatar = insertedUser.avatar,
                            token = generateToken(params.email)
                        )
                    )
                )
            }

        }
    }

    override suspend fun signIn(params: SignInParams): Response<AuthResponse> {
        val user = userDao.findByEmail(params.email)

        return if (user == null) {
            Response.Error(code = HttpStatusCode.NotFound, data = AuthResponse(errorMessage = "No email found"))
        } else {

            val hashPassword = hashPassword(params.password)
            if (user.password == hashPassword) {
                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = user.id, name = user.name, bio = user.bio, token = generateToken(params.email)
                        )
                    )
                )
            } else {
                Response.Error(code = HttpStatusCode.NotFound, data = AuthResponse(errorMessage = "Password not match"))

            }
        }
    }

    private suspend fun userAlreadyExist(email: String): Boolean {
        return userDao.findByEmail(email = email) != null
    }
}