package com.example.kotlin_notes_example.`0-12`.lazylateinit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
 * notes/kotlin/0-12.kotlin-lazy-vs-lateinit.md 데모
 */
private class MessageHolder {
    lateinit var message: String
    fun setup() {
        message = "Hello, Lateinit"
    }
    fun isMessageInitialized(): Boolean = this::message.isInitialized
}

@Composable
fun LazyLateinitScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var computeCount by remember { mutableIntStateOf(0) }
            val lazyValue by remember {
                lazy {
                    computeCount++
                    "Hello, Lazy"
                }
            }
            var revealed by remember { mutableStateOf(false) }
            DemoCard(
                title = "1) lazy: val 전용, 접근 시 1회만 초기화",
                description = "버튼을 여러 번 눌러도 초기화 로직은 한 번만 실행됩니다.",
            ) {
                Button(onClick = { revealed = true }) { Text("lazyValue 접근") }
                if (revealed) ResultText("lazyValue", lazyValue)
                ResultText("초기화 실행 횟수", "$computeCount")
            }
        }

        item {
            val holder = remember { MessageHolder() }
            var initialized by remember { mutableStateOf(false) }
            var error by remember { mutableStateOf<String?>(null) }
            DemoCard(
                title = "2) lateinit: var 전용, 나중에 외부에서 초기화",
                description = "초기화 전에 접근하면 UninitializedPropertyAccessException이 발생합니다. 먼저 '초기화 전 접근'을 눌러보세요.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        error = try {
                            holder.message
                            null
                        } catch (e: UninitializedPropertyAccessException) {
                            "UninitializedPropertyAccessException 발생!"
                        }
                    }) { Text("초기화 전 접근") }
                    Button(onClick = { holder.setup(); initialized = true; error = null }) { Text("setup()") }
                }
                ResultText("::message.isInitialized", "${holder.isMessageInitialized()}")
                error?.let { ResultText("결과", it) }
                if (initialized) ResultText("holder.message", holder.message)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LazyLateinitScreenPreview() {
    KotlinNotesExampleTheme {
        LazyLateinitScreen()
    }
}
