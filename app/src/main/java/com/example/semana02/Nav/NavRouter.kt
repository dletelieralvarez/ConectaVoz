package com.example.semana02.Nav
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.semana02.Vistas.RecuperacionPassword
import com.example.semana02.Vistas.RegistroDeUsuario
import com.example.semana02.Vistas.Iniciar
import com.example.semana02.LoginFacilAcceso

@Composable
fun NavRouter() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.Login.route) {

        composable(NavRoutes.Login.route) {
            LoginFacilAcceso(
                navController = navController,
                onLoginExitoso = {
                    navController.navigate(NavRoutes.Inicio.route)
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