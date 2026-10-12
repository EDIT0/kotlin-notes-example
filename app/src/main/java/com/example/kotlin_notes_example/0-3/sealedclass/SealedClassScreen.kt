package com.example.kotlin_notes_example.`0-3`.sealedclass

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
 * notes/kotlin/0-3.kotlin-sealed-class.md 데모
 */
// sealed class: 생성자가 있어 공통 프로퍼티·로직을 부모에 둘 수 있음
private sealed class Result {
    data class Success(val data: String) : Result()
    data class Error(val errorMessage: String) : Result()
    data object Loading : Result()
}

// sealed interface: 공통 상태 없이 "이 중 하나"라는 마커 역할
private sealed interface NetworkState {
    data object Idle : NetworkState
    data class Success(val data: String) : NetworkState
    data class Error(val message: String) : NetworkState
}

@Composable
fun SealedClassScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            var result by remember { mutableStateOf<Result>(Result.Loading) }
            DemoCard(
                title = "1) sealed class + 완전한 when",
                description = "버튼으로 상태를 바꿔보면 when이 모든 분기(Success/Error/Loading)를 강제로 처리합니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { result = Result.Success("payload") }) { Text("Success") }
                    Button(onClick = { result = Result.Error("network timeout") }) { Text("Error") }
                    Button(onClick = { result = Result.Loading }) { Text("Loading") }
                }
                ResultText("handleResult(result)", handleResult(result))
            }
        }

        item {
            var state by remember { mutableStateOf<NetworkState>(NetworkState.Idle) }
            DemoCard(
                title = "2) sealed interface + 완전한 when",
                description = "sealed interface는 생성자가 없어 공통 로직 공유는 못하지만, 다중 구현이 가능한 마커로 쓰기 좋습니다.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { state = NetworkState.Idle }) { Text("Idle") }
                    Button(onClick = { state = NetworkState.Success("books loaded") }) { Text("Success") }
                    Button(onClick = { state = NetworkState.Error("404 Not Found") }) { Text("Error") }
                }
                ResultText("render(state)", render(state))
            }
        }
    }
}

// 모든 분기를 안 채우면 컴파일 에러 (when 표현식은 반드시 완전해야 함)
private fun handleResult(result: Result): String = when (result) {
    is Result.Success -> "Data: ${result.data}"
    is Result.Error -> "Error: ${result.errorMessage}"
    Result.Loading -> "Loading..."
}

private fun render(state: NetworkState): String = when (state) {
    NetworkState.Idle -> "대기 중"
    is NetworkState.Success -> "성공: ${state.data}"
    is NetworkState.Error -> "에러: ${state.message}"
}

@Preview(showBackground = true)
@Composable
private fun SealedClassScreenPreview() {
    KotlinNotesExampleTheme {
        SealedClassScreen()
    }
}
