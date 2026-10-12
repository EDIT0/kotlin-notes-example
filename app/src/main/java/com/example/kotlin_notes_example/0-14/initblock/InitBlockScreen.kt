package com.example.kotlin_notes_example.`0-14`.initblock

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
 * notes/kotlin/0-14.kotlin-init-block.md 데모
 */
private class User(val name: String, val age: Int) {
    val isAdult: Boolean

    init {
        isAdult = age >= 18
    }
}

private class Order(val items: List<String>) {
    val totalCount: Int

    init {
        require(items.isNotEmpty()) { "Order must contain at least one item." }
        totalCount = items.size
    }
}

private class Traced {
    val log = mutableListOf<String>()
    init { log += "first init block" }
    init { log += "second init block" }
}

@Composable
fun InitBlockScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var age by remember { mutableIntStateOf(20) }
            val user = User("John Doe", age)
            DemoCard(
                title = "1) init: 생성자 매개변수로 프로퍼티 계산",
                description = "age를 바꾸면 매번 새 User가 만들어지고, init 블록이 isAdult를 다시 계산합니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { age = 10 }) { Text("age = 10") }
                    Button(onClick = { age = 20 }) { Text("age = 20") }
                }
                ResultText("User(name, age).isAdult", "${user.isAdult}")
            }
        }

        item {
            val traced = remember { Traced() }
            DemoCard(
                title = "2) 여러 init 블록: 선언 순서대로 실행",
                description = "한 클래스에 여러 init 블록을 둘 수 있고, 위에서부터 선언 순서대로 실행됩니다.",
            ) {
                ResultText("traced.log", traced.log.joinToString(" → "))
            }
        }

        item {
            var result by remember { mutableStateOf<String?>(null) }
            DemoCard(
                title = "3) init + require(): 생성 시점 검증",
                description = "빈 리스트로 Order를 만들면 init 블록의 require()가 즉시 예외를 던집니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        result = try {
                            "totalCount=${Order(listOf("apple", "banana")).totalCount}"
                        } catch (e: IllegalArgumentException) {
                            "IllegalArgumentException: ${e.message}"
                        }
                    }) { Text("Order(listOf(\"apple\",\"banana\"))") }
                    Button(onClick = {
                        result = try {
                            "totalCount=${Order(emptyList()).totalCount}"
                        } catch (e: IllegalArgumentException) {
                            "IllegalArgumentException: ${e.message}"
                        }
                    }) { Text("Order(emptyList())") }
                }
                result?.let { ResultText("결과", it) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InitBlockScreenPreview() {
    KotlinNotesExampleTheme {
        InitBlockScreen()
    }
}
