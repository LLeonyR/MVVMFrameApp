package com.leonyr.mvvm.net

data class Result<T : Any>(val code: Int, var `data`: T? = null, val error: String = ""){

    companion object{
        const val SUCCESS = 0
    }

    fun  isSuccess() :Boolean{
        return code == SUCCESS
    }

}
