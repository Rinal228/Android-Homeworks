package com.example.homework_3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras != null && intent.hasExtra("EXTRA_TITLE")) {
            val title = intent.getStringExtra("EXTRA_TITLE")
            Toast.makeText(this, "Закрыто: Заголовок = $title", Toast.LENGTH_LONG).show()
        }
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "screen_1"
                ) {
                    composable("screen_1") { Screen1(navController) }
                    composable("screen_2") { Screen2(navController) }
                }
            }
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.extras != null && intent.hasExtra("EXTRA_TEXT")) {
            val text = intent.getStringExtra("EXTRA_TEXT")
            Toast.makeText(this, "Запущено: Текст = $text", Toast.LENGTH_LONG).show()
        }
    }
}