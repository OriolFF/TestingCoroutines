package es.seatcode.testingcoroutines.presentation.view

import es.seatcode.testingcoroutines.async.api.RetrofitWebserviceImpl
import es.seatcode.testingcoroutines.data.DataSourceGetUsersRepository
import es.seatcode.testingcoroutines.data.datasource.ApiDataSource
import es.seatcode.testingcoroutines.domain.usecase.GetUsersUseCase
import es.seatcode.testingcoroutines.domain.usecase.LongRunningTaskParallelUseCase
import es.seatcode.testingcoroutines.domain.usecase.LongRunningTaskUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainPresenter(val view: View) : CoroutineScope {
    private var job: Job? = null
    val useCase =
        GetUsersUseCase(DataSourceGetUsersRepository(ApiDataSource(RetrofitWebserviceImpl())))
    val useCaseLong =
        LongRunningTaskUseCase(DataSourceGetUsersRepository(ApiDataSource(RetrofitWebserviceImpl())))
    val useCaseParallel =
        LongRunningTaskParallelUseCase(
            DataSourceGetUsersRepository(
                ApiDataSource(
                    RetrofitWebserviceImpl()
                )
            )
        )

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    fun accessApi(scope: CoroutineScope) {
        view.console("Init access API")
        // http://developer.android.com/topic/libraries/architecture/coroutines
        // https://medium.com/corouteam/exploring-kotlin-coroutines-and-lifecycle-architectural-components-integration-on-android-c63bb8a9156f ->Very useful
        job = scope.launch {
            val users = useCase.getUsers()
            users.fold(
                ifLeft = { view.console("Error: ${it}") },
                ifRight = { view.console(it.toString()) }
            )
            view.console("job ${job.hashCode()} cancelled?:${job?.isCancelled}")
        }
        job?.invokeOnCompletion { throwable ->
            scope.launch(Dispatchers.Main) {
                view.console("Invoked on completion with throwable :$throwable")
            }
        }
        view.console("End access API")
    }

    fun accessApiWithLongRunningTask(scope: CoroutineScope) {
        view.console("Init access Long API")
        //job = this.launch {
        this.launch {
            println("COROUTINES Before UC in context: $coroutineContext job:${coroutineContext[Job]}")
            val users =
                withContext(this@MainPresenter.coroutineContext) {
                    println("COROUTINES UC in context: $coroutineContext job:${coroutineContext[Job]}")
                    useCaseLong.getUsers()
                }
            //withContext(Dispatchers.Main) {
            users.fold(
                ifLeft = { view.console("Error: ${it}") },
                ifRight = { view.console(it.toString()) }
            )
            view.console("job ${job.hashCode()} cancelled?:${job?.isCancelled}")
            //}
        }

        //}
        job?.invokeOnCompletion { throwable ->
            scope.launch(Dispatchers.Main) {
                view.console("Invoked on completion with throwable :$throwable")
            }
        }
        view.console("End access Long API")
    }

    fun accessApiWithLongRunningTaskInParallel(scope: CoroutineScope) {
        view.console("Init access Long API")
        job = scope.launch {
            val users = useCaseParallel.getUsers()
            users.fold(
                ifLeft = { view.console("Error: ${it}") },
                ifRight = { view.console(it.toString()) }
            )
            view.console("job ${job.hashCode()} cancelled?:${job?.isCancelled}")
        }
        job?.invokeOnCompletion { throwable ->
            scope.launch(Dispatchers.Main) {
                view.console("Invoked on completion with throwable :$throwable")
            }
        }

        view.console("End access Long API")
    }

    fun cancelCoroutine(scope: CoroutineScope) {
        println("COROUTINES Cancelling scope context: $coroutineContext job:${coroutineContext[Job]}")
        this.cancel()
    }

    interface View {
        fun console(text: String)
    }
}
