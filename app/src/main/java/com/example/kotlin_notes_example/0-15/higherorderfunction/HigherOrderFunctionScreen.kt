package com.example.kotlin_notes_example.`0-15`.higherorderfunction

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
 * notes/kotlin/0-15.kotlin-higher-order-function.md 데모
 */
private fun higherOrderFunction(input: Int, operation: (Int) -> Int): Int = operation(input)
private fun double(x: Int) = x * 2

private fun operation(type: String): (Int, Int) -> Int = when (type) {
    "add" -> { a, b -> a + b }
    "multiply" -> { a, b -> a * b }
    else -> { _, _ -> 0 }
}

private fun retry(times: Int, action: () -> Boolean): Boolean {
    repeat(times) {
        if (action()) return true
    }
    return false
}

@Composable
fun HigherOrderFunctionScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            DemoCard(
                title = "1) 함수를 매개변수로: operation: (Int) -> Int",
                description = "higherOrderFunction은 어떤 (Int) -> Int 함수든 받아서 적용합니다.",
            ) {
                ResultText("higherOrderFunction(5, ::double)", "${higherOrderFunction(5, ::double)}")
            }
        }

        item {
            var type by remember { mutableStateOf("add") }
            DemoCard(
                title = "2) 함수를 반환: operation(type)",
                description = "type에 따라 다른 (Int, Int) -> Int 함수를 반환합니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { type = "add" }) { Text("add") }
                    Button(onClick = { type = "multiply" }) { Text("multiply") }
                }
                val op = operation(type)
                ResultText("operation(\"$type\")(3, 4)", "${op(3, 4)}")
            }
        }

        item {
            DemoCard(
                title = "3) 컬렉션의 고차 함수: map/filter",
                description = "map/filter도 고차 함수입니다. 각 원소에 람다를 적용한 새 리스트를 돌려줍니다.",
            ) {
                val numbers = listOf(1, 2, 3, 4)
                ResultText("numbers.map { it * 2 }", "${numbers.map { it * 2 }}")
            }
        }

        item {
            var attempts by remember { mutableIntStateOf(0) }
            var success by remember { mutableStateOf<Boolean?>(null) }
            DemoCard(
                title = "4) retry(times, action): 재사용 가능한 뼈대",
                description = "action은 매번 다르게 꽂아 넣을 수 있는 동작입니다. 여기서는 3번째 시도에만 성공하도록 해봤습니다.",
            ) {
                Button(onClick = {
                    attempts = 0
                    success = retry(3) { attempts++; attempts == 3 }
                }) { Text("retry(3) { ... }") }
                ResultText("attempts", "$attempts")
                success?.let { ResultText("success", "$it") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HigherOrderFunctionScreenPreview() {
    KotlinNotesExampleTheme {
        HigherOrderFunctionScreen()
    }
}
