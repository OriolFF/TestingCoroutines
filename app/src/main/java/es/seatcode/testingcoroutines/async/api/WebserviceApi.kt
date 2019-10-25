package es.seatcode.testingcoroutines.async.api

import retrofit2.Response


interface WebserviceApi {
    suspend fun getResultsAsync(): Response<es.seatcode.testingcoroutines.async.api.model.Results>

    suspend fun getResultsSync(): es.seatcode.testingcoroutines.async.api.model.Results
}
