package com.rahultyagi.users

import com.rahultyagi.model.User

interface UserDao {

    suspend fun insert(params:SignUpParams):User?
    suspend fun findByEmail(email:String):User?

}