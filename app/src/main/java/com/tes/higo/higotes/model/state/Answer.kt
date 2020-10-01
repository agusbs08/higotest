package com.tes.higo.higotes.model.state

import java.io.Serializable

data class Answer(
    val trueAnswerCount : Int?,
    val falseAnswerCount :Int?,
    val skipAnswerCount : Int?,
    val score : Int?,
    val difficult : String?
) : Serializable