package es.seatcode.testingcoroutines.presentation.view

import es.seatcode.testingcoroutines.async.api.RetrofitWebserviceImpl
import es.seatcode.testingcoroutines.data.DataSourceGetUsersRepository
import es.seatcode.testingcoroutines.data.datasource.ApiDataSource
import es.seatcode.testingcoroutines.domain.usecase.LongRunningTaskUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class CancellationPresenter(val view: MainPresenter.View) : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()
    private val now: Long
        get() {
            return System.currentTimeMillis()
        }
    private val oneSecond = 1000

    fun startCoroutine() {
        println("COROUTINES Before Start  context: $coroutineContext job:${coroutineContext[Job]}")
        this.launch() {
            doSomething()
        }
    }

    fun cancelCoroutine() {
        println("COROUTINES Cancelling  context: $coroutineContext job:${coroutineContext[Job]}")
        this.cancel()
    }


    private suspend fun doSomething() {
        var dontExit = true
        var ticker = now + oneSecond
        withContext(Dispatchers.Default) {
            while (dontExit) {
                if (!isActive) {
                    dontExit = false
                }
                if (now > ticker) {
                    println("COROUTINES doing work")
                    ticker += oneSecond
                }
            }
        }

    }
}

class MyRepository() {
    val useCaseLong =
        LongRunningTaskUseCase(DataSourceGetUsersRepository(ApiDataSource(RetrofitWebserviceImpl())))
    private var isActive: Boolean = true
    fun getUsers(): List<String> {
        //val list1 = useCaseLong.getUsers().flatMap { }
        return listOf()
    }

    fun cancel() {

    }
}

fun CoroutineScope.doSomething2() {
    val now: Long =
        System.currentTimeMillis()

    val oneSecond = 1000
    var exit = false
    var ticker = now + oneSecond

    while (!exit) {
        if (!isActive) {
            exit = true
        }

        if (now > ticker) {
            println("COROUTINES doing work context:$coroutineContext")
            ticker += oneSecond
        }
    }
}
