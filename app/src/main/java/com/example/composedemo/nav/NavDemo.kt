package com.example.composedemo.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composedemo.Logging

class NavViewModel(initParam: Int) : ViewModel() {

    val param = MutableLiveData(initParam)
    val result = MutableLiveData(initParam)
}

@Preview
@Composable
fun PreviewNavPage1() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = Color.White)
    ) {
        NavPage1(viewModel = NavViewModel(2), onClick = {})
    }
}

@Composable
fun NavPage1(
    viewModel: NavViewModel,
    onClick: (Int) -> Unit
) {
    Logging.info("NavPage1")
    val initParam = viewModel.param.observeAsState()
    val resultValue = viewModel.result.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Logging.info("NavPage1 -> Column")
        Row {
            Logging.info("NavPage1 -> Column -> Row(初始参数)")
            Text(text = "初始参数")
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "${initParam.value}", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { onClick.invoke(resultValue.value!!) }) {
            Logging.info("NavPage1 -> Column -> Go")
            Text(text = "Go")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Logging.info("NavPage1 -> Column -> Row(返回参数)")
            Text(text = "返回参数")
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "${resultValue.value}", fontWeight = FontWeight.Bold)
        }
    }
}

@Preview
@Composable
fun PreviewNavPage2() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color = Color.White)
    ) {
        NavPage2(param = 2, onClick = {})
    }
}

@Composable
fun NavPage2(
    param: Int,
    onClick: (Int) -> Unit
) {
    Logging.info("NavPage2")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Logging.info("NavPage2 -> Column")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Logging.info("NavPage2 -> Column -> Row(接收到的参数)")
            Text(text = "接收到的参数")
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "$param", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Logging.info("NavPage2 -> Column -> Row(按钮)")
            Button(onClick = {
                onClick.invoke(param)
            }) {
                Logging.info("NavPage2 -> Column -> Button(back)")
                Text(text = "Back")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = {
                onClick.invoke(param + 1)
            }) {
                Logging.info("NavPage2 -> Column -> Button(+1)")
                Text(text = "+1")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = {
                onClick.invoke(param - 1)
            }) {
                Logging.info("NavPage2 -> Column -> Button(-1)")
                Text(text = "-1")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Button(onClick = {
                onClick.invoke(param * 2)
            }) {
                Logging.info("NavPage2 -> Column -> Button(x2)")
                Text(text = "x2")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = {
                onClick.invoke(param / 2)
            }) {
                Logging.info("NavPage2 -> Column -> Button(/2)")
                Text(text = "/2")
            }
        }
    }
}