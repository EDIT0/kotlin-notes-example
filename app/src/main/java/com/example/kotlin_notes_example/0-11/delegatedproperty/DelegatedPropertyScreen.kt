package com.example.kotlin_notes_example.`0-11`.delegatedproperty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import kotlin.properties.Delegates

/**
 * notes/kotlin/0-11.kotlin-delegated-property.md 데모
 */
private class ObservableHolder {
    val history = mutableListOf<String>()
    var value: String by Delegates.observable("Initial value") { _, old, new ->
        history += "$old → $new"
    }
}

private class VetoableHolder {
    var value: Int by Delegates.vetoable(0) { _, old, new -> new > old }
}

private class MapUser(map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

@Composable
fun DelegatedPropertyScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var accessCount by remember { mutableIntStateOf(0) }
            val lazyValue by remember {
                lazy {
                    accessCount++
                    "Hello, Kotlin!"
                }
            }
            var revealed by remember { mutableStateOf(false) }
            DemoCard(
                title = "1) lazy: 처음 접근할 때만 초기화",
                description = "버튼을 여러 번 눌러도 초기화 람다는 단 한 번만 실행되고, 이후엔 캐시된 값을 반환합니다.",
            ) {
                Button(onClick = { revealed = true }) { Text("lazyValue 접근") }
                if (revealed) ResultText("lazyValue", lazyValue)
                ResultText("람다 실행 횟수", "$accessCount")
            }
        }

        item {
            val holder = remember { ObservableHolder() }
            var tick by remember { mutableIntStateOf(0) }
            DemoCard(
                title = "2) Delegates.observable: 값이 바뀔 때마다 콜백",
                description = "observableValue를 바꿀 때마다 변경 기록이 누적됩니다.",
            ) {
                Button(onClick = { holder.value = "New value ${tick + 1}"; tick++ }) {
                    Text("observableValue = ...")
                }
                ResultText("observableValue", holder.value)
                ResultText("변경 기록", holder.history.joinToString())
            }
        }

        item {
            val holder = remember { VetoableHolder() }
            var tick by remember { mutableIntStateOf(0) }
            DemoCard(
                title = "3) Delegates.vetoable: 조건부 거부",
                description = "새 값이 이전 값보다 커야만 변경이 허용됩니다. 작은 값으로 바꾸려는 시도는 조용히 무시됩니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { holder.value += 5; tick++ }) { Text("value + 5 (허용)") }
                    Button(onClick = { holder.value -= 100; tick++ }) { Text("value - 100 (거부됨)") }
                }
                ResultText("vetoableValue", "${holder.value}")
            }
        }

        item {
            val user = remember { MapUser(mapOf("name" to "John Doe", "age" to 30)) }
            DemoCard(
                title = "4) Map 위임: JSON 파싱에 유용",
                description = "name/age 프로퍼티 값을 map에서 바로 읽어옵니다.",
            ) {
                ResultText("user.name", user.name)
                ResultText("user.age", "${user.age}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DelegatedPropertyScreenPreview() {
    KotlinNotesExampleTheme {
        DelegatedPropertyScreen()
    }
}
