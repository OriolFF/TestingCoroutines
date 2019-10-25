package es.seatcode.testingcoroutines.async.api.model

import com.squareup.moshi.Json


data class Results(
    @field:Json(name="results")
    val users: List<es.seatcode.testingcoroutines.async.api.model.UserApi>,
    val info: es.seatcode.testingcoroutines.async.api.model.Info
)


data class Info(val speed: String?, val resutls: Int, val page: Int, val version: String)
