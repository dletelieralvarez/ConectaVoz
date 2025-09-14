package com.example.semana02.Vistas

import android.app.DatePickerDialog
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.semana02.Models.Usuario
import java.util.Calendar
import java.util.Date

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import java.util.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.semana02.Nav.NavRouter
import com.example.semana02.Models.RepositorioUsuario
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.TextStyle

class RegistroUser : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavRouter()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroDeUsuario(navController: NavController) {
    val context = LocalContext.current
    //estados para hacer la personalización, modo oscuro y aumento de la fuente
    var esModoOscuro by remember { mutableStateOf(false) }
    var escalaDeFuente by remember { mutableStateOf(1f) }
    //1f tamaño normal de la fuente

    // Lectura de los iconos
    val tts = remember { TextToSpeech(context) { } }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

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

    var nombre by remember { mutableStateOf("") }
    var apellidoPat by remember { mutableStateOf("") }
    var apellidoMat by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val listaPaises = listOf("Chile", "Perú", "Argentina", "España", "México")

    var expanded by remember { mutableStateOf(false) }

    MaterialTheme(colorScheme = if (esModoOscuro) darkColorScheme() else lightColorScheme()) {
        val scrollState = rememberScrollState()
        Scaffold(
            modifier = Modifier.fillMaxSize()
        )
        { padding ->
            MaterialTheme(colorScheme = colors, typography = typography) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(scrollState)
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Registro de Usuario",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xFF4B4B3C)
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE67E22),
                        unfocusedBorderColor = Color(0xFFB2BABB),
                        focusedLabelColor = Color(0xFFE67E22),
                        unfocusedLabelColor = Color(0xFF7F8C8D)
                    )
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = apellidoPat,
                    onValueChange = { apellidoPat = it },
                    label = { Text("Apellido Paterno") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE67E22),
                        unfocusedBorderColor = Color(0xFFB2BABB),
                        focusedLabelColor = Color(0xFFE67E22),
                        unfocusedLabelColor = Color(0xFF7F8C8D)
                    )
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = apellidoMat,
                    onValueChange = { apellidoMat = it },
                    label = { Text("Apellido Materno") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE67E22),
                        unfocusedBorderColor = Color(0xFFB2BABB),
                        focusedLabelColor = Color(0xFFE67E22),
                        unfocusedLabelColor = Color(0xFF7F8C8D)
                    )
                )

                Spacer(Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = pais,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("País") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(), // 👈 IMPORTANTE
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE67E22),
                            unfocusedBorderColor = Color(0xFFB2BABB),
                            focusedLabelColor = Color(0xFFE67E22),
                            unfocusedLabelColor = Color(0xFF7F8C8D)
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listaPaises.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    pais = opcion
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

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

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE67E22),
                        unfocusedBorderColor = Color(0xFFB2BABB),
                        focusedLabelColor = Color(0xFFE67E22),
                        unfocusedLabelColor = Color(0xFF7F8C8D)
                    )
                )

                Spacer(Modifier.height(8.dp))

                Text("Género", color = Color(0xFF7F8C8D))
                Row {
                    listOf("Masculino", "Femenino").forEach { opcion ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            RadioButton(
                                selected = (genero == opcion),
                                onClick = { genero = opcion },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFE67E22),   // Naranja cuando está seleccionado
                                    unselectedColor = Color(0xFF7F8C8D) // Gris cuando no
                                )
                            )
                            Text(
                                opcion,
                                color = if (genero == opcion) Color(0xFFE67E22) else Color(
                                    0xFF7F8C8D
                                ) // 👈 aquí controlas
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                FechaNacimientoField(
                    fechaNacimiento = fechaNacimiento,
                    onDateSelected = { fechaNacimiento = it }
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE67E22),
                        unfocusedBorderColor = Color(0xFFB2BABB),
                        focusedLabelColor = Color(0xFFE67E22),
                        unfocusedLabelColor = Color(0xFF7F8C8D)
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {

                        if (nombre.isBlank() || apellidoPat.isBlank() || apellidoMat.isBlank() ||
                            pais.isBlank() || email.isBlank() || telefono.isBlank() ||
                            genero.isBlank() || fechaNacimiento.isBlank() || password.isBlank()
                        ) {

                            val mensaje = "Todos los campos son obligatorios"
                            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()

                            var tts: TextToSpeech? = null
                            tts = TextToSpeech(context) { status ->
                                if (status == TextToSpeech.SUCCESS) {
                                    tts?.speak(
                                        mensaje,
                                        TextToSpeech.QUEUE_FLUSH,
                                        null,
                                        "tts_error"
                                    )
                                }
                            }
                        } else {

                            val usuario = Usuario(
                                nombreUsuario = nombre,
                                apellidoPatUsuario = apellidoPat,
                                apellidoMatUsuario = apellidoMat,
                                pais = pais,
                                email = email,
                                telefono = telefono,
                                generoUsuario = genero,
                                fechaDeNacimiento = Date(),
                                password = password
                            )
                            RepositorioUsuario.agregarUsuario(usuario)

                            var tts: TextToSpeech? = null
                            val mensaje = "Usuario registrado: ${usuario.nombreUsuario}"
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

                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF4C542),
                        contentColor = Color(0xFF2E2E2E)
                    ),
                    shape = RoundedCornerShape(size = 6.dp)
                ) {
                    Text("Registrar", fontSize = 20.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

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
                        Text(
                            "Aumentar letra",
                            fontSize = (14.sp * escalaDeFuente),
                            color = Color.White
                        )
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
    }
        }
    Spacer(modifier = Modifier.height(32.dp))
}
@Composable
fun FechaNacimientoField(
    fechaNacimiento: String,
    onDateSelected: (String) -> Unit
) {
    OutlinedTextField(
        value = fechaNacimiento,
        onValueChange = { nuevoTexto ->
            val soloNumeros = nuevoTexto.filter { it.isDigit() }

            val formateado = buildString {
                for (i in soloNumeros.indices) {
                    append(soloNumeros[i])
                    if (i == 1 || i == 3) append("/")
                }
            }

            if (formateado.length <= 10) {
                onDateSelected(formateado)
            }
        },
        label = { Text("Fecha de Nacimiento (dd/mm/yyyy)") },
        placeholder = { Text("dd/mm/yyyy") },
        modifier = Modifier
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFE67E22),
            unfocusedBorderColor = Color(0xFFB2BABB),
            focusedLabelColor = Color(0xFFE67E22),
            unfocusedLabelColor = Color(0xFF7F8C8D),
            focusedTextColor = Color(0xFF2E2E2E),
            unfocusedTextColor = Color(0xFF2E2E2E)
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )
}