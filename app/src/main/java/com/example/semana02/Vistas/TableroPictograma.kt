package com.example.semana02.Vistas

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.semana02.Models.Pictograma
import com.example.semana02.Vistas.ui.theme.Semana02Theme
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.semana02.Nav.NavRoutes
class TableroPictograma : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Semana02Theme {
                tablero(onBack = { finish() })
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun tablero(onBack: () -> Unit){
    val context = LocalContext.current

    //modo oscuro
    var esModoOscuro by remember { mutableStateOf(false) }

    // Lectura de los iconos
    val tts = remember { TextToSpeech(context) { } }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    val pictogramas = listOf(
        Pictograma("Comer", Icons.Default.Restaurant, Color(0xFFE67E22)),
        Pictograma("Beber", Icons.Default.LocalDrink, Color(0xFF3498DB)),
        Pictograma("Dormir", Icons.Default.Hotel, Color(0xFF9B59B6)),
        Pictograma("Jugar", Icons.Default.SportsEsports, Color(0xFF2ECC71)),
        Pictograma("Baño", Icons.Default.Wc, Color(0xFFF1C40F)),
        Pictograma("Ayuda", Icons.Default.Help, Color(0xFFE74C3C))
    )

    MaterialTheme(colorScheme = if(esModoOscuro) darkColorScheme() else lightColorScheme()){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tablero de Pictogramas") },
                actions = {
                    IconButton(onClick = {
                        esModoOscuro = !esModoOscuro
                        speak(if (esModoOscuro) "Modo oscuro activado" else "Modo claro activado")
                    }) {
                        Icon(
                            imageVector = if (esModoOscuro) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (esModoOscuro) "Modo Claro" else "Modo Oscuro",
                            modifier = Modifier.size(32.dp),
                            tint = if (esModoOscuro) Color.Yellow else Color.Black
                        )
                    }
                    IconButton(
                        onClick = { onBack()
                            speak("Volver al inicio")
                        }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(if (esModoOscuro) Color(0xFF121212) else Color(0xFFFDFDFD))
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pictogramas) { pictograma ->
                PictogramaCard(pictograma, tts)
            }

        }
    }

    }
}

@Composable
fun PictogramaCard(pictograma: Pictograma, tts: TextToSpeech){
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .aspectRatio(1f)
            .clickable{
                tts.speak(
                    pictograma.titulo,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = pictograma.color),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                imageVector = pictograma.icono,
                contentDescription=pictograma.titulo,
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier=Modifier.height(8.dp))
            Text(
                pictograma.titulo,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}




