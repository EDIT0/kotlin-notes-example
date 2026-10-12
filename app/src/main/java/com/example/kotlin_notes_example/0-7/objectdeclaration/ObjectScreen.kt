package com.example.kotlin_notes_example.`0-7`.objectdeclaration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_notes_example.ui.components.DemoCard
import com.example.kotlin_notes_example.ui.components.ResultText
import com.example.kotlin_notes_example.ui.theme.KotlinNotesExampleTheme

/**
 * notes/kotlin/0-7.kotlin-object.md 데모
 */
private object Logger {
    private val logs = mutableListOf<String>()
    fun log(message: String): List<String> {
        logs += message
        return logs.toList()
    }
}

private data object Configuration {
    val appName: String = "Kotlin Notes"
    val version: String = "1.0.0"
}

private interface ClickListener {
    fun onClick(): String
}

@Composable
fun ObjectScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var logs by remember { mutableStateOf(listOf<String>()) }
            DemoCard(
                title = "1) object 선언: 싱글톤 Logger",
                description = "Logger는 앱 전체에서 단 하나의 인스턴스만 존재합니다. 여러 번 호출해도 같은 인스턴스가 로그를 계속 누적합니다.",
            ) {
                Button(onClick = { logs = Logger.log("Log #${logs.size + 1}") }) { Text("Logger.log(...)") }
                ResultText("logs", logs.toString())
            }
        }

        item {
            var result by remember { mutableStateOf<String?>(null) }
            DemoCard(
                title = "2) object 표현식: 익명 일회성 인스턴스",
                description = "object 선언과 달리, object 표현식은 호출될 때마다 새로운 익명 객체를 만듭니다.",
            ) {
                Button(onClick = {
                    val listener = object : ClickListener {
                        override fun onClick(): String = "Clicked at ${System.nanoTime()}"
                    }
                    result = listener.onClick()
                }) { Text("object : ClickListener { ... }") }
                result?.let { ResultText("listener.onClick()", it) }
            }
        }

        item {
            DemoCard(
                title = "3) data object: 싱글톤 + 자동 생성 메서드",
                description = "일반 object와 달리, data object는 toString()이 자동으로 생성됩니다. 싱글톤이므로 항상 동일한 인스턴스입니다.",
            ) {
                ResultText("Configuration", Configuration.toString())
                ResultText("Configuration === Configuration", "${Configuration === Configuration}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ObjectScreenPreview() {
    KotlinNotesExampleTheme {
        ObjectScreen()
    }
}
