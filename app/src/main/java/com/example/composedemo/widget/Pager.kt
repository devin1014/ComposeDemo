package com.example.composedemo.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.Logging
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Preview
@Composable
fun PreviewViewPager() {
    HorizontalPagerDemo()
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HorizontalPagerDemo() {
    val pageCount = 10
    val list = (1..30).map { it.toString() }
    Box(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(pageCount / 2 - 1)
        LaunchedEffect(key1 = pagerState, block = {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                Logging.info("current page: $page")
            }
        })
        HorizontalPager(
            count = pageCount,
            state = pagerState
        ) {
//            Logging.info("HorizontalPager page: $it")
            Logging.info("HorizontalPager page: $it, ${pagerState.isScrollInProgress}")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                val rememberRefreshState = rememberPullRefreshState(false, onRefresh = {
                    Logging.info("onRefresh...")
                })
                Box(modifier = Modifier.pullRefresh(rememberRefreshState)) {
                    PullRefreshIndicator(false, rememberRefreshState)
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Pager $it", fontSize = 32.sp)
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            content = {
                                Logging.info("LazyColumn")
                                items(list) { item ->
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp)
                                            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(text = item, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(6.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}