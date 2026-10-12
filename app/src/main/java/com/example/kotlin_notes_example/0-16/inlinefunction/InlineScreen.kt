package com.example.kotlin_notes_example.`0-16`.inlinefunction

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
 * notes/kotlin/0-16.kotlin-inline.md 데모
 */
private inline fun findFirst(numbers: List<Int>, predicate: (Int) -> Boolean): Int? {
    for (number in numbers) {
        if (predicate(number)) return number
    }
    return null
}

private inline fun <T> measureAndRun(block: () -> T): Pair<T, Long> {
    val start = System.nanoTime()
    val result = block()
    return result to (System.nanoTime() - start)
}

@Composable
fun InlineScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            DemoCard(
                title = "1) inline + non-local return",
                description = "predicate 안의 return이 findFirst 함수 전체를 끝내버립니다. inline이라 코드가 호출 지점에 그대로 삽입되기 때문입니다.",
            ) {
                val numbers = listOf(1, 2, 3, 4, 5)
                ResultText("findFirst(numbers) { it % 2 == 0 }", "${findFirst(numbers) { it % 2 == 0 }}")
            }
        }

        item {
            var result by remember { mutableStateOf<Pair<String, Long>?>(null) }
            DemoCard(
                title = "2) inline: 람다 객체 생성 없이 코드 삽입",
                description = "measureAndRun은 inline이라 전달한 람다가 그대로 복사돼, 별도 객체 생성 없이 실행 시간을 측정합니다.",
            ) {
                Button(onClick = {
                    val (value, nanos) = measureAndRun { (1..1000).sumOf { it * it } }
                    result = "$value" to nanos
                }) { Text("measureAndRun { ... }") }
                result?.let { (value, nanos) -> ResultText("결과", "value=$value, ${nanos}ns") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InlineScreenPreview() {
    KotlinNotesExampleTheme {
        InlineScreen()
    }
}
