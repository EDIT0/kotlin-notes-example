package com.example.kotlin_notes_example.`0-8`.companionobject

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
 * notes/kotlin/0-8.kotlin-companion-object.md 데모
 */
private interface Creator {
    fun printFactoryInfo(): String
}

private class User private constructor(private val name: String) {
    fun greet() = "Hello, my name is $name"

    companion object Factory : Creator {
        private val createdUsers = mutableListOf<User>()

        fun create(name: String): User {
            val user = User(name)
            createdUsers.add(user)
            return user
        }

        fun listAllUsers(): List<String> = createdUsers.map { it.name }

        override fun printFactoryInfo(): String = "User factory created ${createdUsers.size} user(s)."
    }
}

@Composable
fun CompanionObjectScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var name by remember { mutableStateOf("") }
            var users by remember { mutableStateOf(listOf<String>()) }
            DemoCard(
                title = "1) 팩토리 메서드: private 생성자 + companion object",
                description = "User의 생성자는 private이라 User.create(name)으로만 인스턴스를 만들 수 있습니다.",
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("name") },
                )
                Button(onClick = {
                    if (name.isNotBlank()) {
                        User.create(name)
                        users = User.listAllUsers()
                        name = ""
                    }
                }) { Text("User.create(name)") }
                ResultText("User.listAllUsers()", users.toString())
            }
        }

        item {
            DemoCard(
                title = "2) companion object는 실제 객체: 인터페이스 구현 가능",
                description = "User.Factory는 Creator 인터페이스를 구현한 실제 객체라서, Java의 static과 달리 다형적으로 다룰 수 있습니다.",
            ) {
                ResultText("User.printFactoryInfo()", User.printFactoryInfo())
                val creator: Creator = User.Factory
                ResultText("(creator as Creator).printFactoryInfo()", creator.printFactoryInfo())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CompanionObjectScreenPreview() {
    KotlinNotesExampleTheme {
        CompanionObjectScreen()
    }
}
