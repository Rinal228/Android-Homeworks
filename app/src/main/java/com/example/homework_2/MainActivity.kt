package com.example.homework_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.homework_2.ui.theme.Homework_2Theme
import org.intellij.lang.annotations.Pattern

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MainScreen(modifier = Modifier.fillMaxSize())

        }
    }
}
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "first_screen") {
        composable("first_screen") {
            FirstScreen(
                modifier = modifier,
                onNavigateToSecond = { generatedPassword, password ->
                    navController.navigate("second_Screen/$generatedPassword/$password")
                }
            )
        }
        composable("second_screen/{generatedPassword}/{password}") { getText ->
            val generatedPassword = getText.arguments?.getString("generatedPassword") ?: ""
            val password = getText.arguments?.getString("password") ?: ""
            SecondScreen(
                modifier = modifier,
                generatedPassword = generatedPassword,
                password = password,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
fun isValidPassword(password: String): Boolean {
    val isEnoughLength = password.length >= 8
    val onlyLatinLetters = password.all { it.isDigit() || it in 'a'..'z' || it in 'A'..'Z' }
    return isEnoughLength && onlyLatinLetters
}
fun generatePassword(): String {
    val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val digits = "0123456789"
    val list = mutableListOf(letters.random(), digits.random())
    val allChars = letters + digits
    repeat(6) {
        list.add(allChars.random())
    }
    return list.joinToString("")
}
@Composable
fun FirstScreen(modifier: Modifier = Modifier, onNavigateToSecond: (String, String) -> Unit) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val textErrorForEmailField = remember { mutableStateOf("") }
    val textErrorForPasswordField = remember { mutableStateOf("") }
    val isErrorForEmail = remember { mutableStateOf(false) }
    val isErrorForPassword = remember { mutableStateOf(false) }
    val onClick = {
        if (email.value.isEmpty() && password.value.isEmpty()) {
            isErrorForEmail.value = true
            textErrorForEmailField.value = "Email field is empty"
            isErrorForPassword.value = true
            textErrorForPasswordField.value = "Password field is empty"
        } else if (!email.value.isEmpty() && password.value.isEmpty()) {
            isErrorForEmail.value = false
            isErrorForPassword.value = true
        }
        else if(!isValidEmail(email.value) ) {
            isErrorForEmail.value = true
            textErrorForEmailField.value = "Email is not valid"
        } else if(!isValidPassword(password.value)) {
            isErrorForPassword.value = true
            textErrorForPasswordField.value = "Password field is not valid"
        } else {
            isErrorForEmail.value = false
            isErrorForPassword.value = false
            onNavigateToSecond(generatePassword(), password.value)
        }
    }
    Column(modifier = modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = email.value,
            label = {Text("Enter the email")},
            onValueChange = { enteredEmail ->
                email.value = enteredEmail
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = isErrorForEmail.value,
            supportingText = {
                if (isErrorForEmail.value) {
                    Text(textErrorForEmailField.value)
                }
            }

        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value = password.value,
            label = {Text("Enter the password")},
            onValueChange = { enteredPassword ->
                password.value = enteredPassword
            },
            isError = isErrorForPassword.value,
            supportingText = {
                if (isErrorForPassword.value) {
                    Text(textErrorForPasswordField.value)
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onClick() }
            )

        )
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = onClick
        ) {
            Text("Check")
        }
    }

}
@Composable
fun SecondScreen(modifier: Modifier = Modifier, generatedPassword: String, password: String, onNavigateBack: () -> Unit) {
    val copyPassword = LocalClipboardManager.current
    var passwordVisibility = remember { mutableStateOf(false) }
    var forCheckСopying = remember { mutableStateOf("") }
    val imageUrl = if (generatedPassword.equals(password)) {
        "https://cdn-icons-png.flaticon.com/512/190/190411.png"
    } else {
        "https://cdn-icons-png.flaticon.com/512/190/190406.png"
    }
    Column(modifier = modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            onValueChange = {},
            value = generatedPassword,
            visualTransformation = if (passwordVisibility.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation('*')
            },
            trailingIcon = {
                val image = if (passwordVisibility.value) {
                    Icons.Default.Visibility
                } else {
                    Icons.Default.VisibilityOff
                }
                IconButton(
                    onClick = {passwordVisibility.value = !passwordVisibility.value}
                ) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            onValueChange = {text ->
                forCheckСopying.value = text },
            value = forCheckСopying.value,
            label = {Text("For check copying")}

        )
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = onNavigateBack
        ) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = { copyPassword.setText(AnnotatedString(generatedPassword)) }
        ) {
            Text("Copy password")
        }
    }

}

