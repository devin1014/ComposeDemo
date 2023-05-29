package com.example.composedemo.nav.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composedemo.widget.modifier.CommonDivider

enum class ActionType(value: Int) {
    Login(1), Account(2), Welcome(0)
}

@Composable
fun WelcomeScreen(callback: (ActionType) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { callback.invoke(ActionType.Welcome) }) {
            Text(text = "欢迎")
        }
        CommonDivider()
        Button(onClick = { callback.invoke(ActionType.Login) }) {
            Text(text = "登入")
        }
        CommonDivider()
        Button(onClick = { callback.invoke(ActionType.Account) }) {
            Text(text = "账户")
        }
        CommonDivider()
    }
}

@Preview
@Composable
fun WelcomePreview() {
    WelcomeScreen(callback = {})
}