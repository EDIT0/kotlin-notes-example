package com.example.kotlin_notes_example.`0-19`.functionalinterface

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
 * notes/kotlin/0-19.kotlin-functional-interface.md 데모
 */
private fun interface Greeter {
    fun greet(name: String): String
}

private fun interface Validator<T> {
    fun isValid(value: T): Boolean
}

private fun <T> validateAll(items: List<T>, validator: Validator<T>): Boolean = items.all { validator.isValid(it) }

@Composable
fun FunctionalInterfaceScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var name by remember { mutableStateOf("Kotlin") }
            DemoCard(
                title = "1) fun interface: 람다로 바로 구현",
                description = "추상 메서드가 하나뿐인 Greeter를 익명 클래스 없이 람다로 구현합니다.",
            ) {
                val greeter = Greeter { n -> "Hello, $n from skydoves!" }
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("name") })
                ResultText("greeter.greet(name)", greeter.greet(name))
            }
        }

        item {
            var result by remember { mutableStateOf<String?>(null) }
            DemoCard(
                title = "2) SAM 변환: Java의 Runnable도 람다로",
                description = "Kotlin이 정의한 인터페이스뿐 아니라 Java의 함수형 인터페이스에도 동일하게 적용됩니다.",
            ) {
                Button(onClick = {
                    val runnable = Runnable { result = "Running with SAM conversion!" }
                    runnable.run()
                }) { Text("Runnable { ... }.run()") }
                result?.let { ResultText("결과", it) }
            }
        }

        item {
            DemoCard(
                title = "3) 제네릭 SAM: Validator<T>",
                description = "validateAll은 Validator<T>를 받는데, 람다로 바로 구현을 전달할 수 있습니다.",
            ) {
                val allPositive = validateAll(listOf(1, 2, 3)) { it > 0 }
                val hasNegative = validateAll(listOf(1, -2, 3)) { it > 0 }
                ResultText("validateAll(listOf(1,2,3)) { it > 0 }", "$allPositive")
                ResultText("validateAll(listOf(1,-2,3)) { it > 0 }", "$hasNegative")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FunctionalInterfaceScreenPreview() {
    KotlinNotesExampleTheme {
        FunctionalInterfaceScreen()
    }
}
