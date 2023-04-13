package com.example.composedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log

object Logging {
    fun info(msg: String) {
        Log.i("ComposeDemo", msg)
    }
}

fun Intent?.getInformation(): String {
    if (this == null) return "null"
    val buffer = StringBuilder("{ ")
    action?.let { buffer.append("action=$it, ") }
    type?.let { buffer.append("type=$it, ") }
    buffer.append("component=${component != null}, ")
    dataString?.let { buffer.append("data=$it, ") }
    buffer.append("extras=${extras != null}")
    buffer.append(" }")
    return buffer.toString()
}

fun Bundle?.getInformation(): String {
    if (this == null) return "null"
    if (keySet().size == 0) return "{}"
    val line = 128
    var newLine = line
    val buffer = StringBuilder()
    buffer.append("{ ")
    keySet()
        .filter {
            !it.startsWith("org.chromium.chrome") &&
                    !it.startsWith("com.android.browser") &&
                    !it.startsWith("NTeRQWvye18AkPd6G") &&
                    !it.startsWith("wmHzgD4lOj5o4241")
        }
        .sorted()
        .forEach {
            val value = get(it)
            if (buffer.length > 2) {
                buffer.append(", ")
            }
            if (buffer.length > newLine) {
                buffer.append("\n    ")
                newLine = buffer.length + line
            }
            buffer.append("$it=$value")
        }
    buffer.append(" }")
    return buffer.toString()
}