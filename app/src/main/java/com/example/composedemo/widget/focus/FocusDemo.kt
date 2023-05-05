package com.example.composedemo.widget.focus

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.Logging
import kotlinx.coroutines.launch

@Composable
fun FocusDemo() {
    Column(modifier = Modifier.fillMaxSize()) {
        val lazyRowWidth = remember { mutableStateOf(0) }
        val itemWidth = remember { mutableStateOf(0) }
        val focusPositionState = remember { mutableStateOf(-1) }
        val list = remember { (1..100).map { it.toString() } }
        val listState = rememberLazyListState(0, -(lazyRowWidth.value - itemWidth.value) / 2)
        val coroutineScope = rememberCoroutineScope()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .onSizeChanged {
                    lazyRowWidth.value = it.width
                },
            state = listState,
            content = {
                itemsIndexed(list) { index, item ->
                    val focused = focusPositionState.value == index
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .fillMaxHeight()
                            .onSizeChanged {
                                itemWidth.value = it.width
                            }
                            .border(width = 1.dp, shape = RoundedCornerShape(10), color = Color.Gray)
                            .background(color = if (focused) Color.Red else Color.Transparent, shape = RoundedCornerShape(10))
                            .onFocusChanged { focusState ->
                                if (focusState.hasFocus) {
                                    focusPositionState.value = index
                                    Logging.info("onFocusChanged: $index")
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(index, -(lazyRowWidth.value - itemWidth.value) / 2)
                                    }
                                }
                            }
                            .focusable(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            })
    }
}

@Preview
@Composable
fun FocusDemoPreview() {
    FocusDemo()
}
