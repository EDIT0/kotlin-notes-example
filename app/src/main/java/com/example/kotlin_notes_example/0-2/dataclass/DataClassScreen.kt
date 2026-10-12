package com.example.kotlin_notes_example.`0-2`.dataclass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_notes_example.ui.components.DemoCard
import com.example.kotlin_notes_example.ui.components.ResultText
import com.example.kotlin_notes_example.ui.theme.KotlinNotesExampleTheme

/**
 * notes/kotlin/0-2.kotlin-data-class.md 데모
 */
private class NormalUser(val name: String, val age: Int)
private data class DataUser(val name: String, val age: Int)

@Composable
fun DataClassScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            val normalUser1 = remember { NormalUser("Alice", 30) }
            val normalUser2 = remember { NormalUser("Alice", 30) }
            val dataUser1 = remember { DataUser("Alice", 30) }
            val dataUser2 = remember { DataUser("Alice", 30) }

            DemoCard(
                title = "1) equals() / toString()",
                description = "일반 class는 참조 비교, data class는 프로퍼티 값으로 구조적 비교를 합니다.",
            ) {
                ResultText("normalUser1 == normalUser2", "${normalUser1 == normalUser2} (참조 비교)")
                ResultText("dataUser1 == dataUser2", "${dataUser1 == dataUser2} (구조적 비교)")
                ResultText("normalUser1.toString()", normalUser1.toString())
                ResultText("dataUser1.toString()", dataUser1.toString())
            }
        }

        item {
            val dataUser = remember { DataUser("Alice", 30) }
            DemoCard(
                title = "2) 구조 분해 (componentN)",
                description = "data class는 val (name, age) = user 형태로 구조 분해할 수 있습니다.",
            ) {
                val (name, age) = dataUser
                ResultText("val (name, age) = dataUser", "name=$name, age=$age")
            }
        }

        item {
            var age by remember { mutableIntStateOf(30) }
            val dataUser = DataUser("Alice", age)
            DemoCard(
                title = "3) copy()",
                description = "버튼을 눌러 age만 바꾼 새 인스턴스를 만들어보세요. 원본은 불변이라 바뀌지 않습니다.",
            ) {
                Button(onClick = { age += 1 }) { Text("dataUser.copy(age = age + 1)") }
                ResultText("dataUser", dataUser.toString())
            }
        }

        item {
            val dataUser1 = remember { DataUser("Alice", 30) }
            val dataUser2 = remember { DataUser("Alice", 30) }
            DemoCard(
                title = "4) hashCode()",
                description = "구조적으로 같은 인스턴스는 항상 같은 해시코드를 가집니다 (HashMap/HashSet 키로 중요).",
            ) {
                ResultText(
                    "dataUser1.hashCode() == dataUser2.hashCode()",
                    "${dataUser1.hashCode() == dataUser2.hashCode()}",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DataClassScreenPreview() {
    KotlinNotesExampleTheme {
        DataClassScreen()
    }
}
