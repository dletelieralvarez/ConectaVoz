package com.example.semana02.Nav

sealed class NavRoutes(val route: String) {
    object Inicio : NavRoutes("inicio")
    object Login : NavRoutes("login")
    object Registro : NavRoutes("registro")
    object RecuperarPassword : NavRoutes("recuperar_password")
}