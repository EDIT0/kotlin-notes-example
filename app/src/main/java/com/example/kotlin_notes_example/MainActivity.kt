package com.example.kotlin_notes_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kotlin_notes_example.ui.KotlinNotesApp
import com.example.kotlin_notes_example.ui.theme.KotlinNotesExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinNotesExampleTheme {
                KotlinNotesApp()
            }
        }
    }
}
