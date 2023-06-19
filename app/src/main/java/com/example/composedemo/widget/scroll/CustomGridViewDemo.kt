package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomGridViewDemo() {
    val factory = CustomGridViewFactoryImpl
    CustomGridView(
        factory = factory,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 40.dp),
        item = { modifier, row, maxRow, column, maxColumn, data ->
            var itemData = data.toString()
            val m2: Modifier = if (column != 0 && !factory.isRowScrollable(row)) {
                itemData = "No Data"
                modifier.background(color = Color.LightGray)
            } else if (factory.isStickHeader(row)) {
                modifier.background(color = Color.Cyan)
            } else {
                modifier
            }
            ListItem(
                modifier = m2,
                data = itemData
            )
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CustomGridViewDemoPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        CustomGridViewDemo()
    }
}

object CustomGridViewFactoryImpl : CustomGridViewFactory {
    override fun getColumnWidth(column: Int): Int = if (column == 0) 100 else 50
    override fun getColumnSize(): Int = 20
    override fun getRowHeight(): Int = 50
    override fun getRowSize(): Int = 20
    override fun getItem(row: Int, column: Int): Any = "${row}_$column"
    override fun isStickHeader(row: Int): Boolean = row % 5 == 0
    override fun isRowScrollable(row: Int): Boolean = row != 7 && row != 8
}
