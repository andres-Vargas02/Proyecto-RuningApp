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

    /**
     * Registra un nuevo usuario en la base de datos.
     * @param name Nombre completo del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return `true` si el registro fue exitoso, `false` en caso de error.
     */
    fun registerUser(name: String, email: String, password: String): Boolean {
        val connection = DatabaseConfig.getConnection()
        var isRegistered = false

        try {
            val checkQuery = "SELECT email FROM Usuarios WHERE email = ?"
            val checkStatement = connection.prepareStatement(checkQuery)
            checkStatement.setString(1, email)

            val checkResult = checkStatement.executeQuery()
            if (checkResult.next()) {
                return false
            }

            val insertQuery = "INSERT INTO Usuarios (name, email, password) VALUES (?, ?, ?)"
            val insertStatement = connection.prepareStatement(insertQuery)
            insertStatement.setString(1, name)
            insertStatement.setString(2, email)
            insertStatement.setString(3, password)

            val rowsInserted = insertStatement.executeUpdate()
            isRegistered = rowsInserted > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return isRegistered
    }
}
