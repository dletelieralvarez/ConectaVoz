package com.example.semana02

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.semana02.ui.theme.Semana02Theme

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lint.kotlin.metadata.Visibility

import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.animateLottieCompositionAsState

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import com.example.semana02.Nav.NavRouter
import com.example.semana02.Models.RepositorioUsuario
import androidx.compose.ui.text.font.FontFamily

import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Semana02Theme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF6F0F0)
                ) {
                    NavRouter()
                }
            }
        }
    }
}


@Composable
fun LoginFacilAcceso(navController: NavController, onLoginExitoso: () -> Unit){
    val context = LocalContext.current
    var nombreUsuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mostrarPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.contact)
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

        Text(
            text = "Acceso a ConectaVoz",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            color = Color(0xFF4B4B3C)
        )

        Spacer(modifier = Modifier.height(16.dp))

        var nombreUsuario by remember { mutableStateOf("") }
        NombreConVoz(
            nombre = nombreUsuario,
            onNombreChange = { nombreUsuario = it }
        )

        //OutlinedTextField(
        //    value = nombreUsuario,
        //    onValueChange = { nombreUsuario = it },
        //    label = { Text("Nombre de Usuario") },
        //    modifier = Modifier.fillMaxWidth(),
        //    singleLine = true,
        //    colors = OutlinedTextFieldDefaults.colors(
        //        focusedBorderColor = Color(0xFFE67E22),
        //        unfocusedBorderColor = Color(0xFFB2BABB),
        //        focusedLabelColor = Color(0xFFE67E22),
        //        unfocusedLabelColor = Color(0xFF7F8C8D)
        //    )
        //)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },

            visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { mostrarPassword = !mostrarPassword }) {
                    Icon(
                        imageVector = if (mostrarPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (mostrarPassword) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE67E22),
                unfocusedBorderColor = Color(0xFFB2BABB),
                focusedLabelColor = Color(0xFFE67E22),
                unfocusedLabelColor = Color(0xFF7F8C8D)
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val usuario = RepositorioUsuario.obtenerUsuario(nombreUsuario, password)

                var tts: TextToSpeech? = null

                if (usuario != null) {
                    val mensaje = "Bienvenido ${usuario.nombreUsuario}"
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()

                    tts = TextToSpeech(context) { status ->
                        if (status == TextToSpeech.SUCCESS) {
                            tts?.speak(
                                mensaje,
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "tts_success"
                            )
                        }
                    }
                    onLoginExitoso()
                } else {
                    val mensaje = "Credenciales incorrectas"
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()

                    var tts: TextToSpeech? = null
                    tts = TextToSpeech(context) { status ->
                        if (status == TextToSpeech.SUCCESS) {
                            tts?.speak(
                                "Usuario o contraseña incorrecta",
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
                containerColor = Color(0xFFF4C542),
                contentColor = Color(0xFF2E2E2E)
            ),
            shape= RoundedCornerShape(size=6.dp)
        ) {
            Text("Iniciar Sesión", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "¿No tienes cuenta? Regístrate aquí",
                color = Color(0xFFE67E22),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.clickable {
                    navController.navigate("registro")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "¿Olvidaste tu contraseña? Recuperala aquí",
                color = Color(0xFFE67E22),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.clickable {
                    navController.navigate("recuperar_password")
                }
            )

    }
}

@Composable
fun NombreConVoz(nombre: String, onNombreChange: (String) -> Unit) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    var escuchando by remember { mutableStateOf(false) }
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }

    LaunchedEffect(Unit) {
        tts.value = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = Locale("es", "ES")
            }
        }
    }


    val recordPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Permiso de micrófono requerido", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            recordPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }


    val speechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
    }

    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
        }
    }

    DisposableEffect(Unit) {
        val listener = object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    onNombreChange(matches[0])

                    tts.value?.let { ttsInstance ->
                        ttsInstance.speak(
                            "Dijiste: ${matches[0]}. ¿Es correcto?",
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "tts_confirm"
                        )
                    }

                }
                escuchando = false
            }
            override fun onReadyForSpeech(params: Bundle?) { escuchando = true }
            override fun onEndOfSpeech() { escuchando = false }
            override fun onError(error: Int) { escuchando = false }
            override fun onBeginningOfSpeech() {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onRmsChanged(rmsdB: Float) {}
        }

        speechRecognizer.setRecognitionListener(listener)
        onDispose {
            speechRecognizer.destroy()
            tts.value?.stop()
            tts.value?.shutdown()
        }
    }

    OutlinedTextField(
        value = nombre,
        label = { Text("Nombre de usuario") },
        onValueChange = onNombreChange,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    tts.value?.speak("Por favor, diga su nombre",
                        TextToSpeech.QUEUE_FLUSH, null, "tts_prompt")
                    speechRecognizer.startListening(recognizerIntent)  // 🔹 iniciar reconocimiento al enfocar
                }
            },
        trailingIcon = {
            IconButton(onClick = { speechRecognizer.startListening(recognizerIntent) }) {
                Icon(
                    imageVector = if (escuchando) Icons.Default.Mic else Icons.Default.MicNone,
                    contentDescription = "Hablar"
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFE67E22),
            unfocusedBorderColor = Color(0xFFB2BABB),
            focusedLabelColor = Color(0xFFE67E22),
            unfocusedLabelColor = Color(0xFF7F8C8D)
        ),
        singleLine = true
    )
}

