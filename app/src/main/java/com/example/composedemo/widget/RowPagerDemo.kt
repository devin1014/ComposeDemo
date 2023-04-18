package com.example.composedemo.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composedemo.Logging
import com.example.composedemo.MyRowCombineViewModel

@Composable
fun RowPagerDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
        val viewModel: MyRowCombineViewModel = viewModel(
            factory = object : Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MyRowCombineViewModel(
                        list = (1..100).map { it.toString() },
                        initPosition = 100 / 2,
                    ) as T
                }
            }
        )
        Logging.info("NavGraphBuilder.rowCombinePager, position=${viewModel.position.collectAsState().value}")
        RowCombinePagerAdapter(
            viewModel = viewModel,
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
            pagerItem = { position, item ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Cyan)
                ) {
                    Text(
                        position.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
        // anchor view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.Center)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .align(Alignment.Center)
                .background(Color.Red)
        )
    }
}
