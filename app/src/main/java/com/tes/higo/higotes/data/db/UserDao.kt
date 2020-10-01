package com.tes.higo.higotes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tes.higo.higotes.model.db.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Completable

    @Query("SELECT * from user where difficult = :difficult order by score DESC")
    fun getUsersRanking(difficult : String): Single<List<User>>
}