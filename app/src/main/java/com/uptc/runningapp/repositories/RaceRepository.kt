package com.uptc.runningapp.repositories

import com.uptc.runningapp.data.DatabaseConfig
import com.uptc.runningapp.model.Race
import java.sql.SQLException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RaceRepository {

    /**
     * Inserta una nueva carrera en la base de datos.
     * @param race Objeto `Race` que contiene la información de la carrera.
     * @return `true` si la inserción fue exitosa, `false` en caso contrario.
     */
    suspend fun addRace(race: Race): Boolean = withContext(Dispatchers.IO) {
        val connection = DatabaseConfig.getConnection()
        var isInserted = false

        try {
            val query = """
            INSERT INTO Carreras (userId, raceName, distance, duration, date, profileImage) 
            VALUES (?, ?, ?, ?, ?, ?)
        """
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, race.userId)
            preparedStatement.setString(2, race.raceName)
            preparedStatement.setFloat(3, race.distance)
            preparedStatement.setLong(4, race.duration)
            preparedStatement.setString(5, race.date)
            preparedStatement.setString(6, race.profileImage)

            val rowsInserted = preparedStatement.executeUpdate()
            isInserted = rowsInserted > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return@withContext isInserted
    }


    /**
     * Obtiene una carrera específica por su ID.
     * @param raceId Identificador único de la carrera.
     * @return Objeto `Race` si se encuentra la carrera, o `null` si no existe.
     */
    fun getRaceById(raceId: String): Race? {
        val connection = DatabaseConfig.getConnection()
        var race: Race? = null

        try {
            val query = "SELECT * FROM Carreras WHERE raceId = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, raceId)

            val resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                race = Race(
                    raceId = resultSet.getInt("raceId"),
                    userId = resultSet.getInt("userId"),
                    raceName = resultSet.getString("raceName"),
                    distance = resultSet.getFloat("distance"),
                    duration = resultSet.getLong("duration"),
                    date = resultSet.getString("date"),
                    profileImage = resultSet.getString("profileImage"),
                )
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return race
    }

    /**
     * Lista todas las carreras de la base de datos.
     * @return Una lista de objetos `Race`.
     */
    suspend fun getAllRaces(): List<Race> {
        return withContext(Dispatchers.IO) {
            val connection = DatabaseConfig.getConnection()
            val races = mutableListOf<Race>()

            try {
                if (connection == null || connection.isClosed) {
                    throw SQLException("No se pudo establecer una conexión válida con la base de datos.")
                }

                val query = "SELECT * FROM Carreras ORDER BY raceId DESC"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(query)

                while (resultSet.next()) {
                    val race = Race(
                        raceId = resultSet.getInt("raceId"),
                        userId = resultSet.getInt("userId"),
                        raceName = resultSet.getString("raceName"),
                        distance = resultSet.getFloat("distance"),
                        duration = resultSet.getLong("duration"),
                        date = resultSet.getString("date"),
                        profileImage = resultSet.getString("profileImage"),
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
                    val raceId = resultSet.getInt("raceId")
                    val raceName = resultSet.getString("raceName")
                    val distance = resultSet.getFloat("distance")
                    val duration = resultSet.getLong("duration")
                    val date = resultSet.getString("date")
                    val profileImage = resultSet.getString("profileImage")

                    val race = Race(
                        raceId = raceId,
                        userId = userId,
                        raceName = raceName,
                        distance = distance,
                        duration = duration,
                        date = date,
                        profileImage = profileImage
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
