package no.kristiania.assignment_noforeignland.sqLite.Data


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log




class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        var DATABASE_NAME = "places"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME = "place"
        private val KEY_ID = "id"
        private val KEY_PLACE_NAME = "name"
        private val KEY_COMMENTS = "comments"
        private val KEY_BANNER_LINK = "banner"
        private val KEY_LAT = "lat"
        private val KEY_LON = "lon"


//        omPlaceId(val place: Place)
//
//        class Place(val name: String, val comments: String, val banner: String, val lat: Double, val lon: Double)

        /*CREATE TABLE  ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/
        private val CREATE_TABLE_PLACES = ("CREATE TABLE "+
                TABLE_NAME + "(" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_PLACE_NAME + " TEXT UNIQUE NOT NULL,"+ // if the place have no name, i don't want it.
                KEY_COMMENTS + " TEXT," +
                KEY_BANNER_LINK+ " TEXT," +
                KEY_LAT + "DOUBLE, " +
                KEY_LON + "DOUBLE);")
    }

    // looping through all rows and adding to list
    // adding to place list
    val allPlaces: ArrayList<String>
        get() {
            val placeArrayList = ArrayList<String>()
            var name = ""
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if (c.moveToFirst()) {
                do {
                    name = c.getString(c.getColumnIndex(KEY_PLACE_NAME))
                    placeArrayList.add(name)
                } while (c.moveToNext())
                Log.d("array", placeArrayList.toString())
            }
            return placeArrayList
        }

    init {

        Log.d("DatabaseHandler", CREATE_TABLE_PLACES)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_PLACES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_NAME'")
        onCreate(db)
    }

    fun addGroceryDetail(place: String): Long {
        val db = this.writableDatabase
        // Creating content values
        val values = ContentValues()
        values.put(KEY_PLACE_NAME, place)
        // insert row in grocery table

        return db.insert(TABLE_NAME, null, values)
    }
}