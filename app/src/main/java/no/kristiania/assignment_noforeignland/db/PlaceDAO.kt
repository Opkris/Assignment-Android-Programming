package no.kristiania.assignment_noforeignland.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity

@Dao
interface PlaceDAO
{
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun savePlaces(place: PlaceEntity)

    @Query(value = "Select * from PlaceEntity")
    fun getAllPlaces() : List<PlaceEntity>
}