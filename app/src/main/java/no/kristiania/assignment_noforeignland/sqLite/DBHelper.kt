package no.kristiania.assignment_noforeignland.sqLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import no.kristiania.assignment_noforeignland.sqLite.model.Place
import java.lang.RuntimeException
import kotlin.collections.ArrayList

class DBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER){

    companion object{
        private val DATABASE_NAME = "PLACES_DETAIL.db"
        private val DATABASE_VER = 1

        //Table
        private val TABLE_NAME = "Place"
        private val COL_ID = "Id"
        private val COL_ID_FROM_WEB = "Web_Id"
        private val COL_NAME = "Name"
        private val COL_COMMENT = "Comment"
        private val COL_BANNERURL = "BannerUrl"

    }

    override fun onCreate(db: SQLiteDatabase?) {
         val CREATE_TABLE_PLACE = ("CREATE TABLE " +
                 TABLE_NAME + "(" +
                 COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                 COL_ID_FROM_WEB + " TEXT," +
                 COL_NAME + " TEXT NOT NULL UNIQUE " +
//                 COL_COMMENT + " TEXT, " +
//                 COL_BANNERURL + " TEXT " +
                 ");")
        db?.execSQL(CREATE_TABLE_PLACE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //CRUD

    val allPlaces:ArrayList<Place>
        get(){
            val lstPlace = ArrayList<Place>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val c = db.rawQuery(selectQuery,null)
            if(c.moveToFirst()){
                do {
                    var place =
                        Place()
                    place.id = c.getString(c.getColumnIndex(COL_ID))
                    place.WebId = c.getString(c.getColumnIndex(COL_ID_FROM_WEB))
                    place.name = c.getString(c.getColumnIndex(COL_NAME))
//                    place.comment = c.getString(c.getColumnIndex(COL_COMMENT))

                    lstPlace.add(place)

                } while (c.moveToNext())
                Log.d("Array", lstPlace.toString())
            }
            return lstPlace
        }

    fun addPlace(webId: String, name: String){
//    fun addPlace(place: PlaceClass){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID_FROM_WEB, webId)
        values.put(COL_NAME, name)
//        values.put(COL_COMMENT, place.comment)
//        values.put(COL_BANNERURL, place.bannerUrl)
//        values.put(COL_COMMENT, comment)

    try {
        // some code
        db.insertOrThrow(TABLE_NAME, null, values)
    }
    catch (e: RuntimeException) {
        // handler
        Log.d("Array", "duplicate name: $name")
    }
//        db.close()
    }// end Add Place

    fun updatePlace (places: Place): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, places.id)
        values.put(COL_NAME, places.name)

        return db.update(TABLE_NAME, values, "$COL_NAME=?", arrayOf(places.id.toString()))
    }

    fun DeletePlace(places: Place){

        val db = this.writableDatabase

        db.delete(TABLE_NAME, COL_NAME, arrayOf(places.id.toString()))
        db.close()
    }

}