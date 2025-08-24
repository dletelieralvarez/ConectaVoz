package com.example.semana02.Vistas

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.semana02.Models.RepositorioUsuario
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
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 35.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Recuperar Contraseña",
            style = MaterialTheme.typography.headlineSmall,
            color=Color(0xFF4B4B3C),
            fontSize = 28.sp,
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
                val usuario = RepositorioUsuario.listaUsuario.find { it.email == email }
                if (usuario != null) {
                    val mensaje ="Tu contraseña es: ${usuario.password}"
                    Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()

                    var tts: TextToSpeech? = null
                    tts = TextToSpeech(context) { status ->
                        if (status == TextToSpeech.SUCCESS) {
                            tts?.speak(
                                mensaje,
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1"
                            )
                        } else {
                            Toast.makeText(context, "Error en TTS", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    val mensaje ="Email no encontrado"
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()

                    var tts: TextToSpeech? = null
                    tts = TextToSpeech(context) { status ->
                        if (status == TextToSpeech.SUCCESS) {
                            tts?.speak(
                                mensaje,
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts1"
                            )
                        } else {
                            Toast.makeText(context, "Error en TTS", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF85855E),
                contentColor = Color(0xFFF5F1F1)
            ),
            shape= RoundedCornerShape(size=6.dp)
        ) {
            Text("Recuperar contraseña", fontSize = 20.sp, color = Color.White)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4C542),
                contentColor = Color(0xFF2E2E2E)
            ),
            shape= RoundedCornerShape(size=6.dp)
        ) {
            Text("Volver", fontSize = 20.sp, color = Color.White)
        }
    }
}