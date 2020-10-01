package com.tes.higo.higotes.data.network

import javax.inject.Inject

class NetworkRepository
@Inject constructor(val restApi : RestAPI){

    fun getQuestions(amount : Int, difficult : String, type : String) = restApi.getQuestions(amount, difficult, type)

}