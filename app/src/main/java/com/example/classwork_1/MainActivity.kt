package com.example.classwork_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

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
                clickOnPlanet = { planetIndex ->
                    navController.navigate("second_screen/$planetIndex")
                }
            )
        }
        composable("second_screen/{planetIndex}") { backStackEntry ->
            val planetIndex = backStackEntry.arguments?.getString("planetIndex") ?: "0"
            SecondScreen(modifier = modifier, planetIndex = planetIndex)
        }
    }
}
@Composable
fun PlanetCard(
    modifier: Modifier = Modifier,
    planet: Planet,
    clickOnPlanet: (Planet) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        onClick = { clickOnPlanet(planet) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = planet.imageUrl,
                contentDescription = planet.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = planet.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = planet.description,
                fontSize = 16.sp
            )
        }
    }
}
//
@Composable
fun FirstScreen(modifier: Modifier = Modifier, clickOnPlanet: (Int) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(planets.size) { index ->
            PlanetCard(
                planet = planets[index],
                clickOnPlanet = { clickOnPlanet(index) }
            )
        }
    }
}
@Composable
fun SecondScreen(modifier: Modifier = Modifier, planetIndex: String) {
    val planet: Planet = planets[planetIndex.toInt()]
    PlanetCard(modifier = modifier, planet = planet, clickOnPlanet = {})
}