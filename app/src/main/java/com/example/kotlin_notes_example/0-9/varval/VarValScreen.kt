package com.example.kotlin_notes_example.`0-9`.varval

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_notes_example.ui.components.DemoCard
import com.example.kotlin_notes_example.ui.components.ResultText
import com.example.kotlin_notes_example.ui.theme.KotlinNotesExampleTheme

/**
 * notes/kotlin/0-9.kotlin-var-val.md 데모
 */
private const val MAX_COUNT = 100 // 컴파일 타임 상수

@Composable
fun VarValScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var counter by remember { mutableIntStateOf(0) }
            DemoCard(
                title = "1) var: 재할당 가능한 가변 변수",
                description = "var로 선언한 counter는 몇 번이든 다시 대입할 수 있습니다.",
            ) {
                Button(onClick = { counter += 1 }) { Text("counter += 1") }
                ResultText("counter", "$counter")
            }
        }

        item {
            val numbers = remember { mutableStateListOf(1, 2, 3) }
            DemoCard(
                title = "2) val은 \"참조 불변\", 객체 자체는 가변일 수 있음",
                description = "numbers는 val이라 다른 리스트로 재할당할 수 없지만, 리스트 내용 자체는 추가/삭제로 바뀔 수 있습니다.",
            ) {
                Button(onClick = { numbers.add(numbers.size + 1) }) { Text("numbers.add(...)") }
                ResultText("numbers", numbers.toString())
            }
        }

        item {
            val currentTime = remember { System.currentTimeMillis() }
            DemoCard(
                title = "3) const val vs val",
                description = "const val은 컴파일 타임에 값이 확정되는 상수(primitive/String만), val은 런타임에 평가될 수 있는 읽기 전용 참조입니다.",
            ) {
                ResultText("MAX_COUNT (const val)", "$MAX_COUNT")
                ResultText("currentTime (val, 런타임 평가)", "$currentTime")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VarValScreenPreview() {
    KotlinNotesExampleTheme {
        VarValScreen()
    }
}
