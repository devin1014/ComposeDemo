package com.example.composedemo.framework

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PreviewContentLayout() {
    ContentLayoutDemo(viewModel = AppViewModel())
}

@Composable
fun ContentLayoutDemo(viewModel: AppViewModel) {
    Column {
        Row {
            Button(onClick = { viewModel.load() }) {
                Text("Load")
            }
            Button(onClick = { viewModel.result = "Success" }) {
                Text("Success")
            }
            Button(onClick = { viewModel.result = null }) {
                Text("Error")
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            ContentLayout(viewModel = viewModel) { data ->
                Text(text = data)
            }
        }
    }
}