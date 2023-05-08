package com.example.composedemo.widget.command

import android.annotation.SuppressLint
import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.R.drawable
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader


@SuppressLint("PrivateResource")
@Composable
fun CommandDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column {
            val focusPositionState = remember { mutableStateOf(-1) }
            Row {
                val itemModifier = Modifier.size(80.dp)
                Box(modifier = itemModifier
                    .background(color = if (focusPositionState.value == 0) Color.Magenta else Color.Gray)
                    .onFocusChanged {
                        if (it.isFocused) focusPositionState.value = 0
                    }
                    .focusable())
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = itemModifier
                    .background(color = if (focusPositionState.value == 1) Color.Magenta else Color.Gray)
                    .onFocusChanged {
                        if (it.isFocused) focusPositionState.value = 1
                    }
                    .focusable())
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = itemModifier
                    .background(color = if (focusPositionState.value == 2) Color.Magenta else Color.Gray)
                    .onFocusChanged {
                        if (it.isFocused) focusPositionState.value = 2
                    }
                    .focusable())
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = itemModifier
                    .background(color = if (focusPositionState.value == 3) Color.Magenta else Color.Gray)
                    .onFocusChanged {
                        if (it.isFocused) focusPositionState.value = 3
                    }
                    .focusable())
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                val itemModifier = Modifier.size(80.dp)
                Box(modifier = itemModifier
                    .background(color = if (focusPositionState.value == 4) Color.Magenta else Color.Gray)
                    .onFocusChanged {
                        if (it.isFocused) focusPositionState.value = 4
                    }
                    .focusable())
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = itemModifier
                    .background(color = if (focusPositionState.value == 5) Color.Magenta else Color.Gray)
                    .onFocusChanged {
                        if (it.isFocused) focusPositionState.value = 5
                    }
                    .focusable())
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = itemModifier
                    .background(color = if (focusPositionState.value == 6) Color.Magenta else Color.Gray)
                    .onFocusChanged {
                        if (it.isFocused) focusPositionState.value = 6
                    }
                    .focusable())
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = itemModifier
                    .background(color = if (focusPositionState.value == 7) Color.Magenta else Color.Gray)
                    .onFocusChanged {
                        if (it.isFocused) focusPositionState.value = 7
                    }
                    .focusable())
            }
        }
        Box(
            modifier = Modifier
                .size(192.dp)
                .align(alignment = Alignment.BottomEnd)
        ) {
            Button(
                onClick = { inputKeyEvent(KeyEvent.KEYCODE_DPAD_UP) },
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.TopCenter)
                    .focusable(false)
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .focusable(false),
                    painter = painterResource(id = drawable.mtrl_ic_arrow_drop_up),
                    contentDescription = ""
                )
            }
            Button(
                onClick = { inputKeyEvent(KeyEvent.KEYCODE_DPAD_DOWN) },
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.BottomCenter)
                    .focusable(false)
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .focusable(false),
                    painter = painterResource(id = drawable.mtrl_ic_arrow_drop_down),
                    contentDescription = ""
                )
            }
            Button(
                onClick = { inputKeyEvent(KeyEvent.KEYCODE_DPAD_LEFT) },
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterStart)
                    .focusable(false)
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .focusable(false),
                    painter = painterResource(id = drawable.material_ic_keyboard_arrow_left_black_24dp),
                    contentDescription = ""
                )
            }
            Button(
                onClick = { inputKeyEvent(KeyEvent.KEYCODE_DPAD_RIGHT) },
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterEnd)
                    .focusable(false)
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .focusable(false),
                    painter = painterResource(id = drawable.material_ic_keyboard_arrow_right_black_24dp),
                    contentDescription = ""
                )
            }
        }
    }
}

private fun inputKeyEvent(event: Int) {
    //exeShell("adb shell input keyevent $event")
    exeShellBinSh("adb shell input keyevent $event")
}

private fun exeShell(command: String) {
    try {
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val buffer = StringBuffer()
        val array = CharArray(1024)
        var ch: Int
        while (reader.read(array).also { ch = it } != -1) {
            buffer.append(array, 0, ch)
        }
        reader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

private fun exeShellBinSh(cmd: String) {
    try {
        val ps = Runtime.getRuntime().exec("/system/bin/sh")
        val dos = DataOutputStream(ps.outputStream)
        dos.writeBytes("$cmd\n")
        dos.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Preview
@Composable
fun CommandDemoPreview() {
    CommandDemo()
}
