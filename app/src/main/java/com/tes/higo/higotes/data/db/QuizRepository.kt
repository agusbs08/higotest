package com.tes.higo.higotes.data.db

import com.tes.higo.higotes.model.db.User
import javax.inject.Inject

class QuizRepository @Inject constructor(val userDao: UserDao) {

    fun insertUser(user : User) = userDao.insertUser(user)

    fun getUsersRanking(difficult : String) = userDao.getUsersRanking(difficult)

}