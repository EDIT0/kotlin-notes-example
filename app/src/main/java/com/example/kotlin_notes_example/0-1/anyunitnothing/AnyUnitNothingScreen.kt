package com.example.kotlin_notes_example.`0-1`.anyunitnothing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
 * notes/kotlin/0-1.any-unit-nothing.md 데모
 */
private data class User(val name: String)

private interface Processor<T> {
    fun process(): T
}

private class UnitProcessor : Processor<Unit> {
    override fun process() {
        // 암시적으로 Unit 반환
    }
}

@Composable
fun AnyUnitNothingScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var result by remember { mutableStateOf<String?>(null) }
            DemoCard(
                title = "1) Any: 모든 타입을 담을 수 있음",
                description = "Any는 Kotlin의 모든 클래스가 암시적으로 상속하는 최상위 타입입니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { result = describe("Hello, Kotlin") }) { Text("String") }
                    Button(onClick = { result = describe(123) }) { Text("Int") }
                    Button(onClick = { result = describe(User("skydoves")) }) { Text("User") }
                }
                result?.let { ResultText("describe(value)", it) }
            }
        }

        item {
            val processor: Processor<Unit> = remember { UnitProcessor() }
            DemoCard(
                title = "2) Unit: 실제로 존재하는 타입",
                description = "void와 달리 Unit은 싱글턴 object라서 제네릭 타입 인자로도 사용할 수 있습니다.",
            ) {
                ResultText("processor.process()", "${processor.process()}")
            }
        }

        item {
            var result by remember { mutableStateOf<String?>(null) }
            DemoCard(
                title = "3) Nothing: 절대 정상 반환하지 않는 함수",
                description = "fail()은 항상 예외를 던지므로 반환 타입이 Nothing입니다.",
            ) {
                Button(onClick = {
                    result = try {
                        fail("강제로 예외를 던지는 함수 호출")
                    } catch (e: IllegalArgumentException) {
                        "IllegalArgumentException 발생: ${e.message}"
                    }
                }) { Text("fail() 호출") }
                result?.let { ResultText("결과", it) }
            }
        }

        item {
            DemoCard(
                title = "4) Nothing: 모든 타입의 서브타입 (bottom 타입)",
                description = "List<Nothing>은 요소가 하나도 없음이 보장되므로 어떤 List<T>에도 안전하게 대입할 수 있습니다.",
            ) {
                val emptyStrings: List<String> = emptyList()
                ResultText("emptyList() as List<String>", "$emptyStrings")

                val condition = true
                val x: String = if (condition) "ok" else throw RuntimeException("unreachable")
                ResultText("if (true) \"ok\" else throw ...", x)
            }
        }

        item {
            var value by remember { mutableStateOf("") }
            DemoCard(
                title = "5) requireNonEmpty(value): error()도 Nothing을 반환",
                description = "값이 비어있으면 error()가 IllegalStateException을 던집니다. 입력을 비워서 확인해보세요.",
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    label = { Text("value") },
                )
                val outcome = try {
                    requireNonEmpty(value)
                } catch (e: IllegalStateException) {
                    "IllegalStateException 발생: ${e.message}"
                }
                ResultText("requireNonEmpty(value)", outcome)
            }
        }
    }
}

// Any: 어떤 타입이든 받는 범용 함수
private fun describe(value: Any): String = "The value is: $value"

// Nothing: 항상 예외를 던지는 함수
private fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}

private fun requireNonEmpty(value: String?): String =
    value?.takeIf { it.isNotEmpty() } ?: error("value must not be empty") // error()는 Nothing을 반환

@Preview(showBackground = true)
@Composable
private fun AnyUnitNothingScreenPreview() {
    KotlinNotesExampleTheme {
        AnyUnitNothingScreen()
    }
}
