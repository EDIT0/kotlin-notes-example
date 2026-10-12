package com.example.kotlin_notes_example.`0-4`.innerclass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
 * notes/kotlin/0-4.kotlin-inner-class.md 데모
 */
private class OuterClass(val name: String) {
    private val secret = "Secret Code"

    // nested class (inner 없음): outer 참조가 없어 outer 멤버에 접근 불가
    class NestedClass {
        fun show() = "No access to outer members"
    }

    // inner class: outer 인스턴스 참조를 유지해 private 멤버까지 접근 가능
    inner class InnerClass {
        fun reveal() = "Outer name: $name, Secret: $secret"
        fun showOuterRef() = this@OuterClass.name
    }
}

@Composable
fun InnerClassScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var outerName by remember { mutableStateOf("OuterName") }
            val outer = OuterClass(outerName)
            val inner = outer.InnerClass() // outer 인스턴스 없이는 생성 불가

            DemoCard(
                title = "1) inner class: outer의 private 멤버까지 접근",
                description = "outer 이름을 바꿔보면 inner.reveal()의 결과도 함께 바뀝니다.",
            ) {
                OutlinedTextField(
                    value = outerName,
                    onValueChange = { outerName = it },
                    label = { Text("outer.name") },
                )
                ResultText("inner.reveal()", inner.reveal())
                ResultText("inner.showOuterRef() (this@OuterClass)", inner.showOuterRef())
            }
        }

        item {
            val nested = remember { OuterClass.NestedClass() } // outer 인스턴스 없이 생성 가능
            DemoCard(
                title = "2) nested class: outer 참조 없음",
                description = "inner 키워드가 없으면 Java의 static nested class처럼 동작해, outer 인스턴스 없이 바로 생성할 수 있습니다.",
            ) {
                ResultText("OuterClass.NestedClass().show()", nested.show())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InnerClassScreenPreview() {
    KotlinNotesExampleTheme {
        InnerClassScreen()
    }
}
