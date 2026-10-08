/**
 *crea un sistema de gestion de usuarios con data class usuario. enum rol(admin, editor, viditante). interfaz autenticable y object gestorUsuarios. usa when para validar roles
 * play.kotlinlang.org
 */
enum class Rol {
    ADMIN,
    EDITOR,
    VISITANTE
}

interface Autenticable {
    fun autenticar(): Boolean
}

data class Usuario(
    val id: Int,
    val nombre: String,
    val rol: Rol,
    val activo: Boolean = true
) : Autenticable {

    override fun autenticar(): Boolean {
        return activo
    }
}

object GestorUsuarios {

    private val usuarios = mutableListOf<Usuario>()

    fun registrar(usuario: Usuario) {
        usuarios.add(usuario)
        println("Usuario registrado: ${usuario.nombre}")
    }

    fun mostrarUsuarios() {
        println("\n--- Lista de Usuarios ---")

        usuarios.forEach { usuario ->

            val estadoAuth = if (usuario.autenticar()) {
                "Autenticado"
            } else {
                "Bloqueado/Inactivo"
            }

            println(
                "ID: ${usuario.id} | " +
                "Nombre: ${usuario.nombre} | " +
                "Rol: ${usuario.rol} | " +
                "Estado: $estadoAuth"
            )

            println(" -> Permisos: ${validarPermisos(usuario)}")
        }
    }
}

fun validarPermisos(usuario: Usuario): String {

    return when (usuario.rol) {

        Rol.ADMIN ->
            "Acceso total: puede crear, editar y eliminar recursos."

        Rol.EDITOR ->
            "Acceso de edición: puede crear y modificar contenido."

        Rol.VISITANTE ->
            "Acceso solo de lectura: únicamente puede ver el contenido."
    }
}

fun main() {

    val admin = Usuario(
        1,
        "Ana",
        Rol.ADMIN
    )

    val editor = Usuario(
        2,
        "Carlos",
        Rol.EDITOR
    )

    val visitante = Usuario(
        3,
        "Lucía",
        Rol.VISITANTE,
        activo = false
    )

    GestorUsuarios.registrar(admin)
    GestorUsuarios.registrar(editor)
    GestorUsuarios.registrar(visitante)

    GestorUsuarios.mostrarUsuarios()
}