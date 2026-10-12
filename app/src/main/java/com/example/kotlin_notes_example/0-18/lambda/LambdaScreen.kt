package com.example.kotlin_notes_example.`0-18`.lambda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
 * notes/kotlin/0-18.kotlin-lambda.md 데모
 */
@Composable
fun LambdaScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var name by remember { mutableStateOf("skydoves") }
            DemoCard(
                title = "1) 람다 표현식: 값으로 취급되는 익명 함수",
                description = "greet은 변수에 저장된 람다입니다. { 매개변수 -> 본문 } 형태로 작성합니다.",
            ) {
                val greet = { n: String -> "Hello, $n" }
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("name") })
                ResultText("greet(name)", greet(name))
            }
        }

        item {
            DemoCard(
                title = "2) 고차 함수와 함께: map/filter",
                description = "매개변수가 하나뿐이면 암시적 it으로 생략할 수 있습니다.",
            ) {
                val numbers = listOf(1, 2, 3, 4, 5)
                ResultText("numbers.map { it * 2 }", "${numbers.map { it * 2 }}")
                ResultText("numbers.filter { it % 2 == 0 }", "${numbers.filter { it % 2 == 0 }}")
            }
        }

        item {
            var counter by remember { mutableIntStateOf(0) }
            DemoCard(
                title = "3) 변수 캡처: 람다는 외부 스코프를 기억함",
                description = "increment 람다는 counter를 캡처해서 호출할 때마다 바깥의 counter 값을 직접 바꿉니다.",
            ) {
                val increment = { counter++ }
                Button(onClick = { increment() }) { Text("increment()") }
                ResultText("counter", "$counter")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LambdaScreenPreview() {
    KotlinNotesExampleTheme {
        LambdaScreen()
    }
}
