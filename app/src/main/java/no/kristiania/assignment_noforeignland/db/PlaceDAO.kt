package no.kristiania.assignment_noforeignland.db

import androidx.room.*
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity

@Dao
interface PlaceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlaces(place: PlaceEntity)

    @Query(value = "SELECT * FROM PlaceEntity")
    fun getAllPlaces(): List<PlaceEntity>

    @Query(value = "SELECT * FROM PlaceEntity WHERE placeId LIKE :id")
    fun getPlacesById(id : Long): List<PlaceEntity>

    @Query(value = "SELECT placeId FROM PlaceEntity")
    fun getAllPlacesId(): List<Long>


    // the Update convenience method modifies a set of entities, given as parameters, in the database.
    // it uses a query that matches against the primary key of each entity.
    @Update
    fun updatePlace(vararg place: PlaceEntity)

    @Query(value = "UPDATE PlaceEntity SET placeId = :newId WHERE placeId LIKE :id")
    fun updatePlaceWithQuery(id: Long, newId: Long)

    @Query(value = "UPDATE PlaceEntity SET Banner = :newBanner WHERE placeId LIKE :id")
    fun updatePlaceBanner(id: Long, newBanner: String )

    @Query(value = "UPDATE PlaceEntity SET Comment = :newComment WHERE placeId LIKE :id")
    fun updatePlaceComment(id: Long, newComment: String)

}
