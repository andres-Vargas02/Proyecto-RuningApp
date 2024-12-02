import com.uptc.runningapp.data.DatabaseConfig
import com.uptc.runningapp.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.SQLException

object LocationsRepository {

    private suspend fun getLocationsForRace(raceId: Int): List<Location> {
        return withContext(Dispatchers.IO) {
            val connection = DatabaseConfig.getConnection()
            val locations = mutableListOf<Location>()

            try {
                val query = "SELECT latitude, longitude FROM Locations WHERE raceId = ?"
                val preparedStatement = connection.prepareStatement(query)
                preparedStatement.setInt(1, raceId)

                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    val locationId = resultSet.getInt("locationId")
                    val latitude = resultSet.getDouble("latitude")
                    val longitude = resultSet.getDouble("longitude")
                    locations.add(Location(locationId = locationId, latitude = latitude, longitude = longitude))
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                connection.close()
            }

            locations
        }
    }
}
