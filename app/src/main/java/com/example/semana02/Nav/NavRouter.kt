package com.example.semana02.Nav
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.semana02.Vistas.RecuperacionPassword
import com.example.semana02.Vistas.RegistroDeUsuario
import com.example.semana02.Vistas.Iniciar
import com.example.semana02.LoginFacilAcceso
import com.example.semana02.Vistas.TableroPictograma
@Composable
fun NavRouter() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.Login.route) {

        composable(NavRoutes.Login.route) {
            val context = LocalContext.current
            LoginFacilAcceso(
                navController = navController,
                onLoginExitoso = {
                    //navController.navigate(NavRoutes..route)
                    val intent = Intent(context, TableroPictograma::class.java)
                    context.startActivity(intent)
                }
            )
        }

        composable(NavRoutes.Inicio.route) {
            Iniciar (
                onBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.Registro.route) {
            RegistroDeUsuario (
                navController = navController
            )
        }

        composable(NavRoutes.RecuperarPassword.route) {
            RecuperacionPassword (
                onBack = { navController.popBackStack() }
            )
        }

    }
}