import com.uptc.runningapp.data.DatabaseConfig
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.SQLException

object UserRepository {

    suspend fun registerUser(name: String, email: String, password: String, profileImage: String): Boolean {
        return withContext(Dispatchers.IO) {
            val connection = DatabaseConfig.getConnection()
            var isRegistered = false

            try {
                val checkQuery = "SELECT email FROM Usuarios WHERE email = ?"
                val checkStatement = connection.prepareStatement(checkQuery)
                checkStatement.setString(1, email)

                val checkResult = checkStatement.executeQuery()
                if (checkResult.next()) {
                    return@withContext false
                }

                val insertQuery = "INSERT INTO Usuarios (name, email, password, profileImage) VALUES (?, ?, ?, ?)"
                val insertStatement = connection.prepareStatement(insertQuery)
                insertStatement.setString(1, name)
                insertStatement.setString(2, email)
                insertStatement.setString(3, password)
                insertStatement.setString(4, profileImage)

                val rowsInserted = insertStatement.executeUpdate()
                isRegistered = rowsInserted > 0
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                connection.close()
            }

            return@withContext isRegistered
        }
    }

    suspend fun validateLogin(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
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

            isValid
        }
    }

    suspend fun getUserById(userId: Int): User? {
        return withContext(Dispatchers.IO) {
            val connection = DatabaseConfig.getConnection()
            var user: User? = null

            try {
                val query = """
                    SELECT userId, name, email, profileImage, createdAt 
                    FROM Usuarios 
                    WHERE userId = ?
                """
                val preparedStatement = connection.prepareStatement(query)
                preparedStatement.setInt(1, userId)

                val resultSet = preparedStatement.executeQuery()

                if (resultSet.next()) {
                    val id = resultSet.getString("userId")
                    val name = resultSet.getString("name")
                    val email = resultSet.getString("email")
                    val profileImage = resultSet.getString("profileImage") // Base64 o URL
                    val createAt = resultSet.getDate("createdAt")

                    user = User(
                        userId = id,
                        name = name,
                        email = email,
                        profileImage = profileImage,
                        createAt = createAt,
                    )
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                connection.close()
            }

            user
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return withContext(Dispatchers.IO) {
            val connection = DatabaseConfig.getConnection()
            var user: User? = null

            try {
                val query = """
                SELECT userId, name, email, profileImage, createdAt 
                FROM Usuarios 
                WHERE email = ?
            """
                val preparedStatement = connection.prepareStatement(query)
                preparedStatement.setString(1, email)

                val resultSet = preparedStatement.executeQuery()

                if (resultSet.next()) {
                    val id = resultSet.getString("userId")
                    val name = resultSet.getString("name")
                    val userEmail = resultSet.getString("email")
                    val profileImage = resultSet.getString("profileImage") // Base64 o URL
                    val createAt = resultSet.getDate("createdAt")

                    user = User(
                        userId = id,
                        name = name,
                        email = userEmail,
                        profileImage = profileImage,
                        createAt = createAt,
                    )
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                connection.close()
            }

            user
        }
    }

    private suspend fun getUserRaces(userId: Int): List<Race> {
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

                    val race = Race(
                        raceId = raceId,
                        userId = userId,
                        raceName = raceName,
                        distance = distance,
                        duration = duration,
                        date = date,
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
