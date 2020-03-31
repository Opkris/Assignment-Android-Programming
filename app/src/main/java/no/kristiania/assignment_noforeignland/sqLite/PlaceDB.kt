package no.kristiania.assignment_noforeignland.sqLite
import androidx.room.Database
import androidx.room.RoomDatabase
import no.kristiania.assignment_noforeignland.sqLite.model.PlaceEntity

@Database (entities = [(PlaceEntity::class)],version = 1)
abstract class PlaceDB : RoomDatabase(){

    abstract fun placeDao(): PlaceDAO

}