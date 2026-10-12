package com.example.kotlin_notes_example.`0-13`.visibilitymodifiers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_notes_example.ui.components.DemoCard
import com.example.kotlin_notes_example.ui.components.ResultText
import com.example.kotlin_notes_example.ui.theme.KotlinNotesExampleTheme

/**
 * notes/kotlin/0-13.kotlin-visibility-modifiers.md 데모
 */
private class VisibilityExample {
    private val secret = "Hidden from the outside"
    fun revealSecret() = secret
}

private open class Parent {
    protected fun protectedMethod() = "Accessible in subclasses"
}

private class Child : Parent() {
    fun useProtected() = protectedMethod()
}

internal class InternalExample {
    internal fun internalFunction() = "Available within the module"
}

@Composable
fun VisibilityModifiersScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            val example = remember { VisibilityExample() }
            DemoCard(
                title = "1) private: 클래스 내부에서만",
                description = "secret은 private이라 클래스 밖에서 직접 접근할 수 없고, revealSecret() 같은 public 메서드를 거쳐야 합니다.",
            ) {
                ResultText("example.revealSecret()", example.revealSecret())
            }
        }

        item {
            val child = remember { Child() }
            DemoCard(
                title = "2) protected: 서브클래스에서만",
                description = "protectedMethod()는 Parent 밖에서 직접 호출할 수 없지만, 서브클래스 Child 안에서는 접근 가능합니다.",
            ) {
                ResultText("child.useProtected()", child.useProtected())
            }
        }

        item {
            val example = remember { InternalExample() }
            DemoCard(
                title = "3) internal: 같은 모듈 안에서만",
                description = "같은 모듈(이 앱 모듈)에서는 자유롭게 접근할 수 있습니다. 다른 모듈에서는 컴파일 에러가 납니다.",
            ) {
                ResultText("example.internalFunction()", example.internalFunction())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VisibilityModifiersScreenPreview() {
    KotlinNotesExampleTheme {
        VisibilityModifiersScreen()
    }
}
