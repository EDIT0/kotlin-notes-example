package com.example.kotlin_notes_example.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_notes_example.`0-0`.nullsafety.NullSafetyScreen
import com.example.kotlin_notes_example.`0-1`.anyunitnothing.AnyUnitNothingScreen
import com.example.kotlin_notes_example.`0-2`.dataclass.DataClassScreen
import com.example.kotlin_notes_example.`0-3`.sealedclass.SealedClassScreen
import com.example.kotlin_notes_example.`0-4`.innerclass.InnerClassScreen
import com.example.kotlin_notes_example.`0-5`.enumclass.EnumClassScreen
import com.example.kotlin_notes_example.`0-7`.objectdeclaration.ObjectScreen
import com.example.kotlin_notes_example.`0-8`.companionobject.CompanionObjectScreen
import com.example.kotlin_notes_example.`0-9`.varval.VarValScreen
import com.example.kotlin_notes_example.`0-10`.backingfieldproperty.BackingFieldScreen
import com.example.kotlin_notes_example.`0-11`.delegatedproperty.DelegatedPropertyScreen
import com.example.kotlin_notes_example.`0-12`.lazylateinit.LazyLateinitScreen
import com.example.kotlin_notes_example.`0-13`.visibilitymodifiers.VisibilityModifiersScreen
import com.example.kotlin_notes_example.`0-14`.initblock.InitBlockScreen
import com.example.kotlin_notes_example.`0-15`.higherorderfunction.HigherOrderFunctionScreen
import com.example.kotlin_notes_example.`0-16`.inlinefunction.InlineScreen
import com.example.kotlin_notes_example.`0-17`.reified.ReifiedScreen
import com.example.kotlin_notes_example.`0-18`.lambda.LambdaScreen
import com.example.kotlin_notes_example.`0-19`.functionalinterface.FunctionalInterfaceScreen
import com.example.kotlin_notes_example.`0-20`.extension.ExtensionScreen
import com.example.kotlin_notes_example.ui.theme.KotlinNotesExampleTheme

private data class Topic(val id: String, val title: String)

private val topics = listOf(
    Topic("0-0", "Kotlin Null 안전성"),
    Topic("0-1", "Kotlin Any, Unit, Nothing"),
    Topic("0-2", "Kotlin data class"),
    Topic("0-3", "Kotlin sealed class"),
    Topic("0-4", "Kotlin inner class"),
    Topic("0-5", "Kotlin enum class"),
    Topic("0-7", "Kotlin object"),
    Topic("0-8", "Kotlin companion object"),
    Topic("0-9", "Kotlin var / val"),
    Topic("0-10", "Kotlin backing field vs property"),
    Topic("0-11", "Kotlin 위임 프로퍼티"),
    Topic("0-12", "Kotlin lazy vs lateinit"),
    Topic("0-13", "Kotlin 가시성 수정자"),
    Topic("0-14", "Kotlin init 블록"),
    Topic("0-15", "Kotlin 고차 함수"),
    Topic("0-16", "Kotlin inline"),
    Topic("0-17", "Kotlin reified"),
    Topic("0-18", "Kotlin 람다 표현식"),
    Topic("0-19", "Kotlin 함수형(SAM) 인터페이스"),
    Topic("0-20", "Kotlin 확장(Extension)"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KotlinNotesApp() {
    var selectedTopicId by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            val selectedTitle = topics.firstOrNull { it.id == selectedTopicId }?.title
            TopAppBar(
                title = { Text(selectedTitle?.let { "$selectedTopicId. $it" } ?: "Kotlin Notes Example") },
                navigationIcon = {
                    if (selectedTopicId != null) {
                        IconButton(onClick = { selectedTopicId = null }) {
                            Text("←")
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        when (selectedTopicId) {
            null -> TopicListScreen(
                modifier = Modifier.padding(innerPadding),
                onTopicClick = { selectedTopicId = it },
            )
            "0-0" -> NullSafetyScreen(modifier = Modifier.padding(innerPadding))
            "0-1" -> AnyUnitNothingScreen(modifier = Modifier.padding(innerPadding))
            "0-2" -> DataClassScreen(modifier = Modifier.padding(innerPadding))
            "0-3" -> SealedClassScreen(modifier = Modifier.padding(innerPadding))
            "0-4" -> InnerClassScreen(modifier = Modifier.padding(innerPadding))
            "0-5" -> EnumClassScreen(modifier = Modifier.padding(innerPadding))
            "0-7" -> ObjectScreen(modifier = Modifier.padding(innerPadding))
            "0-8" -> CompanionObjectScreen(modifier = Modifier.padding(innerPadding))
            "0-9" -> VarValScreen(modifier = Modifier.padding(innerPadding))
            "0-10" -> BackingFieldScreen(modifier = Modifier.padding(innerPadding))
            "0-11" -> DelegatedPropertyScreen(modifier = Modifier.padding(innerPadding))
            "0-12" -> LazyLateinitScreen(modifier = Modifier.padding(innerPadding))
            "0-13" -> VisibilityModifiersScreen(modifier = Modifier.padding(innerPadding))
            "0-14" -> InitBlockScreen(modifier = Modifier.padding(innerPadding))
            "0-15" -> HigherOrderFunctionScreen(modifier = Modifier.padding(innerPadding))
            "0-16" -> InlineScreen(modifier = Modifier.padding(innerPadding))
            "0-17" -> ReifiedScreen(modifier = Modifier.padding(innerPadding))
            "0-18" -> LambdaScreen(modifier = Modifier.padding(innerPadding))
            "0-19" -> FunctionalInterfaceScreen(modifier = Modifier.padding(innerPadding))
            "0-20" -> ExtensionScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun TopicListScreen(onTopicClick: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(topics) { topic ->
            ListItem(
                headlineContent = { Text("${topic.id}. ${topic.title}") },
                modifier = Modifier.clickable { onTopicClick(topic.id) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KotlinNotesAppPreview() {
    KotlinNotesExampleTheme {
        KotlinNotesApp()
    }
}

@Preview(showBackground = true)
@Composable
private fun TopicListScreenPreview() {
    KotlinNotesExampleTheme {
        TopicListScreen(onTopicClick = {})
    }
}
