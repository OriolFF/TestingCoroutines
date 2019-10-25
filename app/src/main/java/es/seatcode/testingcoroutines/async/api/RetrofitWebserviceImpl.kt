package es.seatcode.testingcoroutines.async.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException


class RetrofitWebserviceImpl : es.seatcode.testingcoroutines.async.api.WebserviceApi {

    private val webService: es.seatcode.testingcoroutines.async.api.RetrofitWebservice

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()
        val moshi = Moshi.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://randomuser.me")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        webService = retrofit.create(es.seatcode.testingcoroutines.async.api.RetrofitWebservice::class.java)
    }

    override suspend fun getResultsAsync(): Response<es.seatcode.testingcoroutines.async.api.model.Results> {
        Thread.sleep(1000)
        return webService.getResultsAsync()
    }

    override suspend fun getResultsSync(): es.seatcode.testingcoroutines.async.api.model.Results {
        Thread.sleep(1000)
        return execute { webService.getResultsSync() }
    }

    private fun <T> execute(f: () -> Call<T>): T {
        val body: T
        try {
            body = f().execute().body()!!
        } catch (error: IOException) {
            throw Exception(error.message)
        } catch (error: KotlinNullPointerException) {
            throw java.lang.Exception(error.message)
        }
        return body
    }
}
