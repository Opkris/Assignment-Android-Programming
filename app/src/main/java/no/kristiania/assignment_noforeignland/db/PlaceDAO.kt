package no.kristiania.assignment_noforeignland.db

import androidx.room.*
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity

@Dao
interface PlaceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlaces(place: PlaceEntity)

    @Query(value = "SELECT * FROM PlaceEntity")
    fun getAllPlaces(): List<PlaceEntity>

    @Query(value = "SELECT * FROM PlaceEntity WHERE Id LIKE :id")
    fun getPlaceById(id : Long): List<PlaceEntity>

    @Delete
    fun deleteDatabase(place: PlaceEntity)

}
