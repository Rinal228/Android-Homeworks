package com.example.coroutinesproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            AppScreen()
        }
    }
}

@Composable
fun AppScreen() {
    val repo = remember { WorkRepository() }
    val scope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("1") }
    var isLoading by remember { mutableStateOf(false) }
    var currentJob by remember { mutableStateOf<Job?>(null) }
    val count = inputText.toIntOrNull()
    val isValid = count != null && count in 1..10
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { newText ->
                    if (newText.all { it.isDigit() } && newText.length <= 2) {
                        inputText = newText
                    }
                },
                label = { Text("Количество корутин (1–10)") },
                isError = !isValid,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (count != null) {
                        currentJob = scope.launch {
                            isLoading = true
                            try {
                                repeat(count) {
                                    repo.haveHeavyWork()
                                }
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Запустить последовательно")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    if (count != null) {
                        currentJob = scope.launch {
                            isLoading = true
                            try {
                                val jobs = List(count) {
                                    async { repo.haveHeavyWork() }
                                }
                                jobs.awaitAll()
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Запустить параллельно")
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        Button(
            onClick = {
                currentJob?.cancel()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text("Отменить все")
        }
    }

}