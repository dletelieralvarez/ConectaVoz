package com.example.semana02.Models

import java.util.Date

data class Usuario(
    val nombreUsuario : String,
    val apellidoPatUsuario : String,
    val apellidoMatUsuario: String,
    val pais:String,
    val email:String,
    val telefono :String,
    val generoUsuario : String,
    val fechaDeNacimiento : Date,
    val password:String
)

object RepositorioUsuario{
    val listaUsuario = mutableListOf<Usuario>()

    init {
        // Agregamos 5 usuarios de ejemplo
        listaUsuario.add(
            Usuario(
                nombreUsuario = "juan",
                apellidoPatUsuario = "Perez",
                apellidoMatUsuario = "Gomez",
                pais = "Chile",
                email = "juan@example.com",
                telefono = "123456789",
                generoUsuario = "Masculino",
                fechaDeNacimiento = Date(),
                password = "1234"
            )
        )
        listaUsuario.add(
            Usuario("maria", "Lopez", "Diaz", "Perú", "maria@ejemplo.com", "987654321", "Femenino", Date(), "abcd")
        )
        listaUsuario.add(
            Usuario("carlos", "Sanchez", "Torres", "Argentina", "carlos@ejemplo.com", "555444333", "Masculino", Date(), "pass1")
        )
        listaUsuario.add(
            Usuario("lucia", "Ramirez", "Vega", "México", "lucia@ejemplo.com", "111222333", "Femenino", Date(), "pass2")
        )
        listaUsuario.add(
            Usuario("ana", "Martinez", "Ruiz", "España", "ana@ejemplo.com", "444555666", "Femenino", Date(), "pass3")
        )
    }


    fun agregarUsuario(usuario: Usuario){
        listaUsuario.add(usuario)
    }

    fun obtenerUsuario():List<Usuario>{
        return  listaUsuario.toList()
    }

    fun obtenerUsuario(nombreUsuario: String, password: String) : Usuario?{
        return listaUsuario.find { it.nombreUsuario == nombreUsuario && it.password == password }
    }
}