package com.tes.higo.higotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tes.higo.higotes.model.db.User

@Database(
    entities = [User::class], // Tell the database the entries will hold data of this type
    version = 1
)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}