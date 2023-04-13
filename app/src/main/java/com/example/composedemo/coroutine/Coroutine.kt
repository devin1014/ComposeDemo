package com.example.composedemo.coroutine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun Greeting(name: String) {
    var state by remember { mutableStateOf(1) }
    var resp by remember { mutableStateOf("hello $name!") }
    LaunchedEffect(state) {
        delay(400)
        resp = "state:${state}\n这个block执行在协程${Thread.currentThread().name}中"
    }
    Column {
        Text(text = resp)
        Button(
            onClick = { ++state },
            modifier = Modifier
                .height(50.dp)
                .width(100.dp)
        ) {
            Text(text = "点一点")
        }
    }
}