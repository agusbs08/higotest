package com.tes.higo.higotes.data.network

import com.tes.higo.higotes.model.network.QuestionResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestAPI {
    @GET("api.php")
    fun getQuestions(@Query("amount") amount : Int, @Query("difficulty") difficulty : String, @Query("type") type : String) : Single<QuestionResponse>
}