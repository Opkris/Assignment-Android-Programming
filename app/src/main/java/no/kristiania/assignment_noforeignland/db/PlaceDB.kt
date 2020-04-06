package no.kristiania.assignment_noforeignland.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity

@Database (entities = [(PlaceEntity::class)],version = 1)
abstract class PlaceDB : RoomDatabase(){

    abstract fun placeDao(): PlaceDAO


//    companion object{
//        @Volatile
//        private var INSTANCE: PlaceDAO? = null
//
//        fun getDatabase(context: Context): PlaceDB{
//            if(INSTANCE == null){
//                synchronized(this){
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        PlaceDAO::class.java,
//                        "places.db"
//                    ).build()
//                }
//            }
//            return INSTANCE!!
//        }
//    }

}