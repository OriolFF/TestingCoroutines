package es.seatcode.testingcoroutines.async.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface RetrofitWebservice {
    @GET("/api")
    suspend fun getResultsAsync(): Response<es.seatcode.testingcoroutines.async.api.model.Results>

    @GET("/api")
    fun getResultsSync(): Call<es.seatcode.testingcoroutines.async.api.model.Results>
}
