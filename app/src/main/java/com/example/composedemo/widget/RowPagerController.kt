package com.example.composedemo.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.composedemo.MyRowCombineViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

abstract class RowCombineViewModel<T> : ViewModel() {

    abstract val list: List<T>
    abstract val initPosition: Int
    val firstVisibleItemScrollOffset: Int = 0
    val totalSize: Int
        get() = list.size
    val position by lazy { MutableStateFlow(initPosition) }
}

@Composable
fun Dp.toPix(): Int {
    return (LocalContext.current.resources.displayMetrics.density * this.value).roundToInt()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <T> RowCombinePagerAdapter(
    viewModel: RowCombineViewModel<T>,
    horizontalItem: @Composable (position: Int, item: T, onClick: () -> Unit) -> Unit,
    pagerItem: @Composable (position: Int, item: T) -> Unit
) {
    val position = viewModel.position.collectAsState().value
    val rowState = rememberLazyListState(
        initialFirstVisibleItemIndex = position,
        initialFirstVisibleItemScrollOffset = viewModel.firstVisibleItemScrollOffset
    )
    val pagerState = rememberPagerState(position)
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            viewModel.position.value = it
            rowState.scrollToItem(it, viewModel.firstVisibleItemScrollOffset)
        }
    }
    val coroutineScope = rememberCoroutineScope()
    Column {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = rowState,
            content = {
                itemsIndexed(viewModel.list) { index, item ->
                    horizontalItem(index, item) {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                }
            })
        HorizontalPager(
            count = viewModel.totalSize,
            state = pagerState
        ) {
            pagerItem(it, viewModel.list[it])
        }
    }
}

@Preview
@Composable
fun PreviewRowCombinePagerAdapter() {
    Box(modifier = Modifier.background(Color.White)) {
        RowCombinePagerAdapter(
            viewModel = MyRowCombineViewModel(list = listOf("1", "2", "3"), initPosition = 10),
            horizontalItem = { position, item, onClick ->
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(64.dp)
                        .padding(2.dp)
                        .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(24.dp))
                        .clickable { onClick.invoke() }
                ) {
                    Text(
                        position.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp
                    )
                }
            },
            pagerItem = { index, _ ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Cyan)
                ) {
                    Text(
                        index.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}