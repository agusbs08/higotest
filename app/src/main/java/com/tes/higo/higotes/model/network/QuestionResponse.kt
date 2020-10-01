package com.tes.higo.higotes.model.network
import com.google.gson.annotations.SerializedName


data class QuestionResponse(
    @SerializedName("response_code")
    val responseCode: Int?,
    @SerializedName("results")
    val results: MutableList<Question>?
)