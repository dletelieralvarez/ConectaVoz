package com.example.semana02.Vistas

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.semana02.Vistas.ui.theme.Semana02Theme
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.semana02.Models.RepositorioUsuario
import com.example.semana02.R
import com.example.semana02.ui.theme.ui.theme.Amarillo
import com.example.semana02.ui.theme.ui.theme.GrisOscuro
import com.example.semana02.ui.theme.ui.theme.Naranja

class RecuperarPassword : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecuperacionPassword(onBack = { finish() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuperacionPassword(onBack: () -> Unit) {
    //val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    //estados para hacer la personalización, modo oscuro y aumento de la fuente
    var esModoOscuro by remember { mutableStateOf(false) }
    var escalaDeFuente by remember { mutableStateOf(1f) }
    //1f tamaño normal de la fuente

    //defino colores dinamicos
    val colors = if (esModoOscuro) {
        darkColorScheme(
            primary = Color(0xFFE67E22),
            onPrimary = Color.White,
            background = Color(0xFF121212),
            onBackground = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFFE67E22),
            onPrimary = Color.Black,
            background = Color(0xFFFFFFFF),
            onBackground = Color.Black
        )
    }

    //tamaño de fuente escalable
    val typography = Typography(
        bodyLarge = TextStyle(
            fontSize = (16.sp * escalaDeFuente),
            fontFamily = FontFamily.SansSerif
        ),
        titleLarge = TextStyle(
            fontSize = (28.sp * escalaDeFuente),
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
    )

    val context = LocalContext.current
    val tts = remember { TextToSpeech(context) { } }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    MaterialTheme(colorScheme = colors, typography = typography) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
                .padding(top = 35.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            //animación de Lottie
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(R.raw.password)
            )
            val progress by animateLottieCompositionAsState(
                composition,
                iterations= LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = composition,
                progress={ progress },
                modifier=Modifier
                    .fillMaxWidth()
                    .height(height = 320.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Recuperar Contraseña",
                style = MaterialTheme.typography.headlineSmall,
                color = if (esModoOscuro) Color(0xFFFDE9A8) else Color(0xFF807027),
                fontSize = (28.sp * escalaDeFuente),
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE67E22),
                    unfocusedBorderColor = Color(0xFFB2BABB),
                    focusedLabelColor = Color(0xFFE67E22),
                    unfocusedLabelColor = Color(0xFF7F8C8D)
                )
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val mensaje = RepositorioUsuario.listaUsuario
                        .find { it.email == email }
                        ?.let { "Tu contraseña es: ${it.password}" }
                        ?: "Email no encontrado"
                    Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                    speak(mensaje)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE67E22),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                    //containerColor = Color(0xFF85855E),
                    //contentColor = Color(0xFFF5F1F1)
                ),
                shape = RoundedCornerShape(size = 6.dp)
            ) {
                Text(
                    "Recuperar contraseña",
                    fontSize = (20.sp * escalaDeFuente),
                    color = Color.White
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { onBack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF9E79F),
                    contentColor = Color(0xFF807027)
                    //containerColor = Color(0xFFF4C542),
                    //contentColor = Color(0xFF2E2E2E)
                ),
                shape = RoundedCornerShape(size = 6.dp)
            ) {
                Text("Volver", fontSize = (20.sp * escalaDeFuente), color = Color(0xFF807027), fontWeight = FontWeight.ExtraBold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            //botones para la accesibilidad

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        escalaDeFuente += 0.1f
                        val mensaje = "Has aumentado el tamaño de la letra"
                        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                        speak(mensaje)
                    },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.TextFields,
                        contentDescription = "Aumentar letra",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Aumentar letra", fontSize = (14.sp * escalaDeFuente), color = Color.White)
                }

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
            }
        }
        DisposableEffect(Unit) {
            onDispose {
                tts.stop()
                tts.shutdown()
            }
        }
    }
}