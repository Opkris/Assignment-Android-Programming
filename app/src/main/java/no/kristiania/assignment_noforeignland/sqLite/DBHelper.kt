package no.kristiania.assignment_noforeignland.sqLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.RuntimeException

class DBHelper (context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER){


    companion object{
        private val DATABASE_NAME = "PLACESDETAIL.db"
        private val DATABASE_VER = 1

        //Table
        private val TABLE_NAME = "Place"
        private val COL_ID = "Id"
        private val COL_NAME = "Name"

        private val CREATE_TABLE_PLACE = ("CREATE TABLE "
                + TABLE_NAME + "(" + COL_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " TEXT NOT NULL UNIQUE );")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_PLACE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //CRUD

    val allPlaces:ArrayList<String>
        get(){
            val lstPlace = ArrayList<String>()
            var name = ""
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val c = db.rawQuery(selectQuery,null)
            if(c.moveToFirst()){
                do {
                    name = c.getString(c.getColumnIndex(COL_NAME))
                    lstPlace.add(name)
                } while (c.moveToNext())
                Log.d("array", lstPlace.toString())
            }
            return lstPlace
        }



//    fun addPlace(id: Int, name: String){
    fun addPlace( name: String){
        val db = this.writableDatabase
        val values = ContentValues()
//        values.put(COL_ID, id)
        values.put(COL_NAME, name)


    try {
        // some code
        db.insertOrThrow(TABLE_NAME, null, values)
    }
    catch (e: RuntimeException) {
        // handler
        Log.d("Array", "duplicate name ")
    }
        db.close()
    }

    fun updatePlace (place: Place): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, place.id)
        values.put(COL_NAME, place.name)

        return db.update(TABLE_NAME, values, "$COL_NAME=?", arrayOf(place.id.toString()))
    }

    fun DeletePlace(place: Place){

        val db = this.writableDatabase

        db.delete(TABLE_NAME, COL_NAME, arrayOf(place.id.toString()))
        db.close()
    }

}