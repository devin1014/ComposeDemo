package com.example.composedemo.widget.refresh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.composedemo.Logging
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RefreshDemo() {
//    SwipeRefreshDemo()
    PullRefreshDemo()
}

@Composable
private fun SwipeRefreshDemo() {
    val refreshing = remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(refreshing.value)
    val coroutineScope = rememberCoroutineScope()
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            refreshing.value = true
            Logging.info("onRefresh... start")
            coroutineScope.launch {
                delay(3 * 1000L)
                Logging.info("onRefresh...end")
                refreshing.value = false
            }
        },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Swipe Refresh", fontSize = 32.sp)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PullRefreshDemo() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val refreshing = remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val rememberRefreshState = rememberPullRefreshState(
            //TODO
            refreshing.value,
            onRefresh = {
                refreshing.value = true
                Logging.info("onRefresh... start")
                coroutineScope.launch {
                    delay(3 * 1000L)
                    Logging.info("onRefresh...end")
                    refreshing.value = false
                }
            }
        )
        Box(
            modifier = Modifier.pullRefresh(rememberRefreshState)
        ) {
            PullRefreshIndicator(
                refreshing.value,
                rememberRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "PullRefresh", fontSize = 32.sp)
            }
        }
    }
}