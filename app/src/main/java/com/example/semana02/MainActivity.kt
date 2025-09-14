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
import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.core.content.ContextCompat
import java.util.Locale
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Modo fullscreen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }

        setContent {
            Semana02Theme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background
                    //color = Color(0xFFF6F0F0)
                ) {
                    NavRouter()
                }
            }
        }
    }
}


@Composable
fun LoginFacilAcceso(navController: NavController, onLoginExitoso: () -> Unit){
    //val context = LocalContext.current
    var nombreUsuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mostrarPassword by remember { mutableStateOf(false) }

    //estados para hacer la personalización, modo oscuro y aumento de la fuente
    var esModoOscuro by remember {mutableStateOf(false)}
    var escalaDeFuente by remember {mutableStateOf(1f)}
    //1f tamaño normal de la fuente

    //defino colores dinamicos
    val colors = if (esModoOscuro){
        darkColorScheme(
            primary = Color(0xFFE67E22),
            onPrimary = Color.White,
            background = Color(0xFF121212),
            onBackground = Color.White
        )
    }else{
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

    //inicia TTS global
    val context = LocalContext.current
    val tts = remember {
        TextToSpeech(context) {status -> }
    }

    //función para leer al usuario
    fun speak(text: String){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    MaterialTheme (colorScheme = colors, typography = typography){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //animación de Lottie
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
            fontSize = (28.sp * escalaDeFuente),
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            //color = Color(0xFF4B4B3C)
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        NombreConVoz(
            nombre = nombreUsuario,
            onNombreChange = { nombreUsuario = it }
        )

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
                    Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                    speak(mensaje)
                    onLoginExitoso()
                } else {
                    val mensaje = "Usuario o contraseña incorrecta"
                    Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                    speak(mensaje)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
                //containerColor = Color(0xFFF4C542),
                //contentColor = Color(0xFF2E2E2E)
            ),
            shape= RoundedCornerShape(size=6.dp)
        ) {
            Text(
                "Iniciar Sesión",
                fontSize = (20.sp * escalaDeFuente),
                color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "¿No tienes cuenta? Regístrate aquí",
                //color = Color(0xFFE67E22),
                color = Color(0xFF359F0B),
                fontWeight = FontWeight.Bold,
                fontSize = (12.sp * escalaDeFuente),
                modifier = Modifier.clickable {
                    navController.navigate("registro")
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "¿Olvidaste tu contraseña? Recuperala aquí",
                color = Color(0xFF359F0B),
                //color = Color(0xFFE67E22),
                fontWeight = FontWeight.Bold,
                fontSize = (12.sp * escalaDeFuente),
                modifier = Modifier.clickable {
                    navController.navigate("recuperar_password")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            //botones para la accesibilidad

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
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
                        imageVector = Icons.Default.TextFields, // Icono que representa texto
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
    }
    DisposableEffect(Unit) {
        onDispose {
            tts.stop()
            tts.shutdown()
        }
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

