package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.UserDao
import com.example.weatherapp.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun loginUser(email: String, password: String): UserEntity? {
        return userDao.getUser(email, password)
    }

}