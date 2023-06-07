package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composedemo.resources.getItemColor

private val list = (1..30).toList()

@Composable
fun RowScrollableDemo() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            val horizontalScrollState = rememberScrollState()
            val scrollState2 = rememberScrollState()
            val scrollState3 = rememberScrollState()
            val scrollState4 = rememberScrollState()
            LaunchedEffect(key1 = horizontalScrollState, block = {
                snapshotFlow { horizontalScrollState.value }.collect {
                    scrollState2.scrollTo(it)
                    scrollState3.scrollTo(it)
                    scrollState4.scrollTo(it)
                }
            })
            Row(
                modifier = Modifier
                    .height(100.dp)
                    .horizontalScroll(horizontalScrollState)
            ) {
                list.forEach {
                    ListItem(
                        modifier = Modifier
                            .size(100.dp)
                            .background(color = getItemColor(it)), data = it.toString()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .height(100.dp)
                    .horizontalScroll(scrollState2)
            ) {
                list.forEach {
                    ListItem(
                        modifier = Modifier
                            .size(100.dp)
                            .background(color = getItemColor(it)), data = it.toString()
                    )
                }
            }
            //disable row
            Row(
                modifier = Modifier
                    .height(100.dp)
//                    .horizontalScroll(scrollState3, enabled = false)
            ) {
//                list.forEach {
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(color = getItemColor(0)), data = "666"
                )
//                }
            }
            Row(
                modifier = Modifier
                    .height(100.dp)
                    .horizontalScroll(scrollState4)
            ) {
                list.forEach {
                    ListItem(
                        modifier = Modifier
                            .size(100.dp)
                            .background(color = getItemColor(it)), data = it.toString()
                    )
                }
            }
        }
    }
}