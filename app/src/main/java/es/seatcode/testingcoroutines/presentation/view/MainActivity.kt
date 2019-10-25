package es.seatcode.testingcoroutines.presentation.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.seatcode.testingcoroutines.R
import kotlinx.android.synthetic.main.activity_main.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : AppCompatActivity(), MainPresenter.View {

    private var presenter: MainPresenter? = null
    private var cancellationPresenter: CancellationPresenter? = null
    private val scope = lifecycleScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        cancellationPresenter = CancellationPresenter(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initView()
    }

    private fun initView() {
        run_button.setOnClickListener {
            presenter?.accessApi(scope)
        }
        cancel_button.setOnClickListener {
            presenter?.cancelCoroutine(scope)
        }
        block_button.setOnClickListener {
            presenter?.accessApiWithLongRunningTask(scope)
        }
        parallel_button.setOnClickListener {
            presenter?.accessApiWithLongRunningTaskInParallel(scope)
        }
        clear_button.setOnClickListener {
            console.text = ""
        }
        start_coroutine.setOnClickListener {
            cancellationPresenter?.startCoroutine()
        }
        cancel_coroutine.setOnClickListener {
            cancellationPresenter?.cancelCoroutine()
        }
    }

    override fun console(text: String) {
        console.println(text)
    }
}

fun TextView.println(text: String) {
    this.append("\n $text")
}
