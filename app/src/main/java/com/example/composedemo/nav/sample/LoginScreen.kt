package com.example.composedemo.nav.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.widget.modifier.CommonDivider
import com.example.composedemo.widget.modifier.CommonSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(callback: (userName: String, password: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(6.dp)
    ) {
        val userNameState = remember { mutableStateOf("") }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "UserName")
            CommonSpacer()
            TextField(value = userNameState.value, onValueChange = {
                userNameState.value = it
            })
        }
        CommonDivider()
        val passwordState = remember { mutableStateOf("") }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Password")
            CommonSpacer()
            TextField(value = passwordState.value, onValueChange = {
                passwordState.value = it
            })
        }
        CommonDivider()
        Button(onClick = {
            callback.invoke(userNameState.value, passwordState.value)
        }) {
            Text(text = "LogIn")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LogInScreen { userName, password -> }
}