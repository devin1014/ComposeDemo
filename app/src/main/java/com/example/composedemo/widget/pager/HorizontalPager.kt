package com.example.composedemo.widget.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
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

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
fun HorizontalPagerPreview() {
    HorizontalPagerDemo()
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HorizontalPagerDemo() {
    val pageCount = 10
    Box(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(1)
        LaunchedEffect(key1 = pagerState, block = {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                Logging.info("current page: $page")
            }
        })
        HorizontalPager(
            count = pageCount,
            state = pagerState
        ) {
            Logging.info("HorizontalPager page: $it, ${pagerState.isScrollInProgress}")
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .background(color = Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Pager $it", fontSize = 32.sp)
                }
                ContentList()
            }
        }
    }
}

private val list = (1..30).map { it.toString() }

@Composable
private fun ContentList() {
    LazyColumn(
        contentPadding = PaddingValues(start = 12.dp, top = 12.dp, end = 12.dp),
        content = {
            Logging.info("LazyColumn")
            items(list) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
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
