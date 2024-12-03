package com.uptc.runningapp.repositories

import com.uptc.runningapp.data.DatabaseConfig
import com.uptc.runningapp.model.Race
import java.sql.SQLException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RaceRepository {

    fun addRace(race: Race): Boolean {
        val connection = DatabaseConfig.getConnection()
        var isInserted = false

        try {
            val query = """
                INSERT INTO Carreras (distance, duration, date) 
                VALUES (?, ?, ?)
            """
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setFloat(1, race.distance)
            preparedStatement.setLong(2, race.duration)
            preparedStatement.setString(3, race.date)

            val rowsInserted = preparedStatement.executeUpdate()
            isInserted = rowsInserted > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return isInserted
    }

    /**
     * Obtiene una carrera específica por su ID.
     * @param raceId Identificador único de la carrera (Int).
     * @return Objeto `Race` si se encuentra la carrera, o `null` si no existe.
     */
    fun getRaceById(raceId: Int): Race? { // Cambiado de String a Int
        val connection = DatabaseConfig.getConnection()
        var race: Race? = null

        try {
            val query = "SELECT * FROM Carreras WHERE raceId = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, raceId) // Cambiado a setInt

            val resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                race = Race(
                    raceId = resultSet.getInt("raceId"),
                    userId = resultSet.getInt("userId"),
                    raceName = resultSet.getString("raceName"),
                    distance = resultSet.getFloat("distance"),
                    duration = resultSet.getLong("duration"),
                    date = resultSet.getString("date")
                )
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return race
    }

    suspend fun getAllRaces(): List<Race> {
        return withContext(Dispatchers.IO) {
            val connection = DatabaseConfig.getConnection()
            val races = mutableListOf<Race>()

            try {
                if (connection == null || connection.isClosed) {
                    throw SQLException("No se pudo establecer una conexión válida con la base de datos.")
                }

                val query = "SELECT * FROM Carreras"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(query)

                while (resultSet.next()) {
                    val race = Race(
                        raceId = resultSet.getInt("raceId"),
                        userId = resultSet.getInt("userId"),
                        raceName = resultSet.getString("raceName"),
                        distance = resultSet.getFloat("distance"),
                        duration = resultSet.getLong("duration"),
                        date = resultSet.getString("date")
                    )
                    races.add(race)
                }
            } catch (e: SQLException) {
                println("Error al consultar la base de datos: ${e.message}")
                e.printStackTrace()
            } finally {
                connection?.close()
            }

            return@withContext races
        }
    }

    private suspend fun getRacesByUser(userId: Int): List<Race> {
        return withContext(Dispatchers.IO) {
            val connection = DatabaseConfig.getConnection()
            val races = mutableListOf<Race>()

            try {
                val query = "SELECT * FROM Carreras WHERE userId = ?"
                val preparedStatement = connection.prepareStatement(query)
                preparedStatement.setInt(1, userId)

                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    val race = Race(
                        raceId = resultSet.getInt("raceId"),
                        userId = userId,
                        raceName = resultSet.getString("raceName"),
                        distance = resultSet.getFloat("distance"),
                        duration = resultSet.getLong("duration"),
                        date = resultSet.getString("date"),
                    )
                    races.add(race)
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                connection.close()
            }

            races
        }
    }

}
