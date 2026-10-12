package com.example.kotlin_notes_example.`0-5`.enumclass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
 * notes/kotlin/0-5.kotlin-enum-class.md 데모
 */
private enum class Direction(val degrees: Int) {
    NORTH(0),
    EAST(90),
    SOUTH(180),
    WEST(270);

    fun description(): String = "Direction $name is $degrees degrees from North."
}

@Composable
fun EnumClassScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var direction by remember { mutableStateOf(Direction.NORTH) }
            DemoCard(
                title = "1) enum class: 고정된 상수 집합",
                description = "버튼으로 Direction 상수를 바꿔보면 name/ordinal/description()이 함께 바뀝니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Direction.entries.forEach { d ->
                        Button(onClick = { direction = d }) { Text(d.name) }
                    }
                }
                ResultText("direction.name", direction.name)
                ResultText("direction.ordinal", "${direction.ordinal}")
                ResultText("direction.description()", direction.description())
            }
        }

        item {
            DemoCard(
                title = "2) entries & valueOf(String)",
                description = "entries는 호출마다 배열을 새로 만들지 않는 값 목록입니다. valueOf는 이름으로 상수를 찾습니다.",
            ) {
                ResultText("Direction.entries", Direction.entries.joinToString { it.name })
                ResultText("Direction.valueOf(\"EAST\")", "${Direction.valueOf("EAST")}")
            }
        }

        item {
            var direction by remember { mutableStateOf(Direction.NORTH) }
            DemoCard(
                title = "3) when으로 완전성 보장",
                description = "handleDirection은 모든 enum 상수를 처리해야만 컴파일됩니다. 분기를 하나라도 빼먹으면 컴파일 에러가 납니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Direction.entries.forEach { d ->
                        Button(onClick = { direction = d }) { Text(d.name) }
                    }
                }
                ResultText("handleDirection(direction)", handleDirection(direction))
            }
        }
    }
}

private fun handleDirection(direction: Direction): String = when (direction) {
    Direction.NORTH -> "You are heading North."
    Direction.EAST -> "You are heading East."
    Direction.SOUTH -> "You are heading South."
    Direction.WEST -> "You are heading West."
}

@Preview(showBackground = true)
@Composable
private fun EnumClassScreenPreview() {
    KotlinNotesExampleTheme {
        EnumClassScreen()
    }
}
