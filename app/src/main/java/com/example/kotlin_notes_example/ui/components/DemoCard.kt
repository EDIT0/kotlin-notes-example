package com.example.kotlin_notes_example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_notes_example.ui.theme.KotlinNotesExampleTheme

/**
 * 노트 하나의 예제를 보여주는 공통 카드 레이아웃.
 * title/description은 노트 내용을 설명하고, content 슬롯에는 실제 Composable 데모를 넣는다.
 */
@Composable
fun DemoCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
            content()
        }
    }
}

/** 코드 실행 결과를 monospace로 보여주는 공통 텍스트. */
@Composable
fun ResultText(label: String, value: String, modifier: Modifier = Modifier) {
    Text(
        text = "$label -> $value",
        modifier = modifier,
        fontFamily = FontFamily.Monospace,
        style = MaterialTheme.typography.bodySmall,
    )
}

@Preview(showBackground = true)
@Composable
private fun DemoCardPreview() {
    KotlinNotesExampleTheme {
        DemoCard(
            title = "예제 제목",
            description = "이 카드는 노트 내용을 설명하고, 아래에 실제 데모 Composable을 보여줍니다.",
        ) {
            ResultText("result", "42")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultTextPreview() {
    KotlinNotesExampleTheme {
        ResultText(label = "nickname?.length", value = "null")
    }
}
