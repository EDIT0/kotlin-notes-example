package com.example.kotlin_notes_example.`0-20`.extension

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
 * notes/kotlin/0-20.kotlin-extension.md 데모
 */
private fun Int.isEven(): Boolean = this % 2 == 0
private val String.firstChar: Char
    get() = this[0]
private fun List<Int>.average2(): Double = if (isEmpty()) 0.0 else sum().toDouble() / size
private fun String.isValidEmail(): Boolean = contains("@") && contains(".")

@Composable
fun ExtensionScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var number by remember { mutableIntStateOf(4) }
            DemoCard(
                title = "1) 확장 함수: Int.isEven()",
                description = "Int 클래스를 직접 수정하지 않고도 새 함수를 추가했습니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { number++ }) { Text("number + 1") }
                }
                ResultText("number", "$number")
                ResultText("number.isEven()", "${number.isEven()}")
            }
        }

        item {
            var text by remember { mutableStateOf("Hello") }
            DemoCard(
                title = "2) 확장 프로퍼티: String.firstChar",
                description = "확장 프로퍼티는 상태를 저장할 수 없고, getter에 대한 문법적 편의일 뿐입니다.",
            ) {
                OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("text") })
                ResultText("text.firstChar", if (text.isNotEmpty()) "${text.firstChar}" else "(빈 문자열)")
            }
        }

        item {
            var email by remember { mutableStateOf("user@example.com") }
            DemoCard(
                title = "3) 표준 타입에 도메인 기능 추가",
                description = "List<Int>.average2()와 String.isValidEmail() 모두 원본 클래스를 건드리지 않고 추가한 확장입니다.",
            ) {
                ResultText("listOf(1,2,3).average2()", "${listOf(1, 2, 3).average2()}")
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("email") })
                ResultText("email.isValidEmail()", "${email.isValidEmail()}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExtensionScreenPreview() {
    KotlinNotesExampleTheme {
        ExtensionScreen()
    }
}
