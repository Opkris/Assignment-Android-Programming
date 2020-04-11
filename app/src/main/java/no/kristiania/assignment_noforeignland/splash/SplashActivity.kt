package no.kristiania.assignment_noforeignland.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.GsonBuilder
import no.kristiania.assignment_noforeignland.MainActivity
import no.kristiania.assignment_noforeignland.R
import no.kristiania.assignment_noforeignland.db.PlaceDB
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity
import no.kristiania.assignment_noforeignland.models.HomeFeed
import okhttp3.*
import java.io.IOException

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // create the loading time of the "splash"
        val SPLASH_LOADING_TIME:Long = 3000 // 3.0 seconds

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val db = Room.databaseBuilder(applicationContext, PlaceDB::class.java, "ROOM_PLACE.db").build()
//        fetchJson(db)
        Handler().postDelayed({
            // This method will be executed once the timer is over
            startActivity(Intent(this, MainActivity::class.java))
            // close this activity
            finish()
        }, SPLASH_LOADING_TIME)
    }// end onCreate



    fun fetchJson(db: PlaceDB) {
        println("Attempting to Fetch JSON")

        val url = "https://www.noforeignland.com/home/api/v1/places/"
//        val urlTwo = "https://www.noforeignland.com/home/api/v1/place?id=$id"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        //enqueue make sure that every thing is going on in the background // another thread.
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                // if you need to do debug the response.body :
//                println("from Json Body: \n $body ")

                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                //*******************************************
                //*******************************************

                // if we have fetched once we will not fetch again // at this point.
                if (db.placeDao().getAllPlaces().isEmpty()) {
                    Log.d("Database", "Storing data to local from Splash")

                    //Looping through homeFeed.features list
//                    for (position in homeFeed.features.indices) {
                     homeFeed.features.forEach {

                        val thread = Thread {
                            var placeEntity = PlaceEntity()
                            placeEntity.placeId = it.properties.id
                            placeEntity.placeName = it.properties.name
                            placeEntity.placeLon = it.geometry.coordinates[0]
                            placeEntity.placeLat = it.geometry.coordinates[1]

                            db.placeDao().savePlaces(placeEntity)

                            //*******************************************
                            //*******************************************
                        }
                        thread.start()
                    }// end forloop
                }// end if statement
                else {
                    Log.d("Database", "Fetching from Local data #Splash")
                }

                Log.d("Database", "-------------- Done API 1 Splash-------------- ")
//                runOnUiThread {
//                    recyclerView_main.adapter = MainAdapter(homeFeed)
//                }
//                fetchJsonAPITwo(db)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

        })// end client.newCall
    }// end fetchJson
}
