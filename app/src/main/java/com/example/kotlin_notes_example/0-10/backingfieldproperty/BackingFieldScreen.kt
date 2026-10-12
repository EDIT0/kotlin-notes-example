package com.example.kotlin_notes_example.`0-10`.backingfieldproperty

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
 * notes/kotlin/0-10.kotlin-backing-field-property.md 데모
 */
private class NameHolder {
    var name: String = "Default"
        get() = field.uppercase()
        set(value) {
            field = value.trim()
        }
}

private class AgeHolder {
    private var _age: Int = 0
    var age: Int
        get() = _age
        set(value) {
            if (value >= 0) _age = value
        }
}

@Composable
fun BackingFieldScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            val holder = remember { NameHolder() }
            var input by remember { mutableStateOf("") }
            DemoCard(
                title = "1) backing field: field 키워드",
                description = "field는 Kotlin이 암시적으로 만들어주는 저장소입니다. setter에서 trim, getter에서 uppercase로 가공합니다.",
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = {
                        input = it
                        holder.name = it
                    },
                    label = { Text("name 입력") },
                )
                ResultText("holder.name", holder.name)
            }
        }

        item {
            val holder = remember { AgeHolder() }
            var age by remember { mutableIntStateOf(0) }
            DemoCard(
                title = "2) backing property: _age",
                description = "_age는 개발자가 직접 선언한 저장소입니다. 음수를 넣으려 하면 setter가 조용히 거부합니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { holder.age += 1; age = holder.age }) { Text("age + 1") }
                    Button(onClick = { holder.age = -100; age = holder.age }) { Text("age = -100 시도") }
                }
                ResultText("holder.age", "${holder.age}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BackingFieldScreenPreview() {
    KotlinNotesExampleTheme {
        BackingFieldScreen()
    }
}
