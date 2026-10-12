package com.example.kotlin_notes_example.`0-17`.reified

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_notes_example.ui.components.DemoCard
import com.example.kotlin_notes_example.ui.components.ResultText
import com.example.kotlin_notes_example.ui.theme.KotlinNotesExampleTheme

/**
 * notes/kotlin/0-17.kotlin-reified.md 데모
 */
private inline fun <reified T> isType(value: Any): Boolean = value is T
private inline fun <reified T> className(): String = T::class.java.simpleName
private inline fun <reified T> List<Any>.filterByType(): List<T> = this.filterIsInstance<T>()

@Composable
fun ReifiedScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            DemoCard(
                title = "1) reified: 런타임에 T로 is 검사",
                description = "평범한 제네릭 함수에서는 value is T가 컴파일 에러지만, inline + reified에서는 가능합니다.",
            ) {
                ResultText("isType<String>(\"Hello\")", "${isType<String>("Hello")}")
                ResultText("isType<Int>(\"Hello\")", "${isType<Int>("Hello")}")
            }
        }

        item {
            DemoCard(
                title = "2) reified: T::class 직접 접근",
                description = "Class<T>를 매개변수로 넘기지 않아도 타입 이름을 바로 조회할 수 있습니다.",
            ) {
                ResultText("className<String>()", className<String>())
                ResultText("className<Int>()", className<Int>())
            }
        }

        item {
            DemoCard(
                title = "3) reified 확장 함수: filterByType",
                description = "섞여 있는 리스트에서 원하는 타입만 걸러낼 수 있습니다.",
            ) {
                val mixedList = listOf<Any>(1, "Kotlin", 2.5, "Programming")
                ResultText("mixedList.filterByType<String>()", "${mixedList.filterByType<String>()}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReifiedScreenPreview() {
    KotlinNotesExampleTheme {
        ReifiedScreen()
    }
}
