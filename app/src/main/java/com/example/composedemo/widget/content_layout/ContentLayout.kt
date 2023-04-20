package com.example.composedemo.widget.content_layout

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedemo.widget.content_layout.State.ERROR
import com.example.composedemo.widget.content_layout.State.INIT
import com.example.composedemo.widget.content_layout.State.LOADING
import com.example.composedemo.widget.content_layout.State.NO_DATA
import com.example.composedemo.widget.content_layout.State.REFRESH
import com.example.composedemo.widget.content_layout.State.SUCCESS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun <T : Any> ContentLayout(
    viewModel: BaseViewModel<T>,
    loading: @Composable (() -> Unit) = { Loading() },
    error: @Composable ((Exception?) -> Unit) = { Text("Error") },
    noData: @Composable (() -> Unit) = { Text(text = "noData") },
    content: @Composable (data: T) -> Unit
) {
    val state = viewModel.state.observeAsState()
    Box {
        when (state.value) {
            REFRESH -> {} // do nothing.
            LOADING -> loading()
            ERROR -> error(viewModel.exception.value)
            NO_DATA -> noData()
            SUCCESS -> {
                val data = remember { mutableStateOf(viewModel.responseData.value) }
                if (data.value == null) {
                    noData()
                } else {
                    content(data.value!!)
                }
            }
            else -> Text(text = "unKnown")
        }
    }
}

abstract class BaseViewModel<T> : ViewModel() {
    val state = MutableLiveData(INIT)
    val exception: MutableLiveData<Exception?> = MutableLiveData(null)
    val responseData: MutableLiveData<T?> = MutableLiveData(null)

    protected fun loadData(requestData: suspend () -> T?) {
        viewModelScope.launch {
            state.value = LOADING
            val response = requestData()
            if (response != null) {
                if (isEmptyData(response)) {
                    state.value = NO_DATA
                } else {
                    state.value = SUCCESS
                    responseData.value = response
                }
            } else {
                state.value = ERROR
                exception.value = Exception("error")
            }
        }
    }

    open fun isEmptyData(t: T): Boolean = t == null
}

class AppViewModel : BaseViewModel<String>() {

    var result: String? = null

    fun load() {
        loadData {
            Log.i("TestLog", "loadData...")
            delay(3000L)
            result
        }
    }
}

enum class State(val value: Int) {
    INIT(0),
    LOADING(1),
    SUCCESS(2),
    NO_DATA(3),
    ERROR(4),
    REFRESH(5)
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            Modifier.size(40.dp, 40.dp),
            MaterialTheme.colorScheme.primary,
        )
    }
}