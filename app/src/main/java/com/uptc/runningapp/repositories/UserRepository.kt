package com.uptc.runningapp.repositories

import com.uptc.runningapp.data.DatabaseConfig
import java.sql.SQLException

object UserRepository {
    /**
     * Valida las credenciales de inicio de sesión de un usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña ingresada por el usuario.
     * @return `true` si las credenciales son correctas, `false` en caso contrario.
     */
    fun validateLogin(email: String, password: String): Boolean {
        val connection = DatabaseConfig.getConnection()
        var isValid = false

        try {
            val query = "SELECT password FROM Usuarios WHERE email = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, email)

            val resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val storedPassword = resultSet.getString("password")
                isValid = storedPassword == password
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return isValid
    }
}
