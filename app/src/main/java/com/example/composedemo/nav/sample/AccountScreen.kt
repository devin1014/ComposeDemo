package com.example.composedemo.nav.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.widget.modifier.CommonDivider
import com.example.composedemo.widget.modifier.CommonSpacer

@Composable
fun AccountScreen(
    userName: String,
    password: String,
    callback: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(6.dp)
    ) {
        Row {
            Text(text = "UserName")
            CommonSpacer()
            Text(text = userName)
        }
        CommonDivider()
        Row {
            Text(text = "Password")
            CommonSpacer()
            Text(text = password)
        }
        CommonDivider()
        Button(onClick = { callback.invoke() }) {
            Text(text = "Back")
        }
    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreen(userName = "liu", password = "wei") {}
}