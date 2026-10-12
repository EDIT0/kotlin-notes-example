package com.example.kotlin_notes_example.`0-0`.nullsafety

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
 * notes/kotlin/0-0.kotlin-null-safety.md 데모
 */
private data class User(val name: String)

@Composable
fun NullSafetyScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var nickname by remember { mutableStateOf("") }
            // nickname이 빈 문자열이면 "null"로 간주해서 ?./?: 동작을 직접 확인
            val nullableNickname: String? = nickname.ifBlank { null }

            DemoCard(
                title = "1) 안전 호출(?.) & 엘비스(?:)",
                description = "입력값을 비워두면 null로 취급됩니다. nickname?.length와 nickname ?: \"Guest\"를 직접 확인해보세요.",
            ) {
                OutlinedTextField(
                    value = nickname,
                    onValueChange = { nickname = it },
                    label = { Text("nickname (비우면 null)") },
                )
                ResultText("nickname?.length", "${nullableNickname?.length}")
                ResultText("nickname ?: \"Guest\"", nullableNickname ?: "Guest")
            }
        }

        item {
            var result by remember { mutableStateOf<String?>(null) }
            DemoCard(
                title = "2) not-null 단언 (!!)",
                description = "값이 있으면 그대로 반환하지만, null이면 런타임에 NullPointerException이 발생합니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        val user: User? = User("Alice")
                        result = "성공: ${user!!.name}"
                    }) { Text("user!!.name (값 있음)") }

                    Button(onClick = {
                        result = try {
                            val nullUser: User? = null
                            nullUser!!.name
                        } catch (e: NullPointerException) {
                            "NullPointerException 발생! (의도된 크래시 데모)"
                        }
                    }) { Text("nullUser!!.name (null)") }
                }
                result?.let { ResultText("결과", it) }
            }
        }

        item {
            var input by remember { mutableStateOf<User?>(null) }
            DemoCard(
                title = "3) 스마트 캐스트",
                description = "null 체크를 하고 나면, 그 블록 안에서는 컴파일러가 자동으로 non-null 타입으로 취급해줍니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { input = User("Bob") }) { Text("user = User(\"Bob\")") }
                    Button(onClick = { input = null }) { Text("user = null") }
                }
                val foundUser = input
                val message = if (foundUser != null) {
                    "Welcome, ${foundUser.name}" // 스마트 캐스트로 바로 .name 접근 가능
                } else {
                    "No user"
                }
                ResultText("welcomeMessage", message)
            }
        }

        item {
            DemoCard(
                title = "4) 안전한 캐스팅 (as?)",
                description = "실패하면 예외 대신 null을 반환합니다.",
            ) {
                val anyValue: Any = "not a number"
                val number: Int? = anyValue as? Int
                ResultText("(\"not a number\" as? Int)", "$number")
            }
        }

        item {
            var name by remember { mutableStateOf("") }
            DemoCard(
                title = "5) 조합: greet(user)",
                description = "?. + takeIf + ?: 를 함께 써서 null까지 한 줄로 처리합니다. 공백만 입력하면 \"Guest\"가 나옵니다.",
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("name") },
                )
                ResultText("greet(User(name))", greet(if (name.isEmpty()) null else User(name)))
            }
        }
    }
}

private fun greet(user: User?): String {
    return user?.name?.takeIf { it.isNotBlank() } ?: "Guest"
}

@Preview(showBackground = true)
@Composable
private fun NullSafetyScreenPreview() {
    KotlinNotesExampleTheme {
        NullSafetyScreen()
    }
}
