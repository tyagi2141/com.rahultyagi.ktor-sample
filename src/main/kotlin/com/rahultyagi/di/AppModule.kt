package com.rahultyagi.di


import com.rahultyagi.repository.user.UserRepository
import com.rahultyagi.repository.user.UserRepositoryImpl
import com.rahultyagi.users.UserDao
import com.rahultyagi.users.UserDaoImpl
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserDao> { UserDaoImpl() }
}