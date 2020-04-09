package no.kristiania.assignment_noforeignland

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import no.kristiania.assignment_noforeignland.adapters.MainAdapter
import no.kristiania.assignment_noforeignland.db.PlaceDB
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity
import no.kristiania.assignment_noforeignland.models.HomeFeed
import no.kristiania.assignment_noforeignland.models.secondModel.FromPlaceId
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(applicationContext, PlaceDB::class.java, "ROOM_PLACE.db").build()


        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        button_to_db_test.setOnClickListener{
            println("hello World!")
//            fetchJsonAPITwo(db)
        }

        fetchJson(db)

        /** Testing **/
//        getPlaceById(db)
//        getPlaceByIdReturnOnlyName(db)
//        updateWithQuery(db)
//        fetchAllFromDB(db)


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
                    Log.d("Database", "Storing data to local")

                    //Looping through homeFeed.features list
                    for (position in homeFeed.features.indices) {

                        val feature = homeFeed.features.get(position)
                        val thread = Thread {
                            var placeEntity = PlaceEntity()
                            placeEntity.placeId = feature.properties.id
                            placeEntity.placeName = feature.properties.name
                            placeEntity.placeLon = feature.geometry.coordinates[0]
                            placeEntity.placeLat = feature.geometry.coordinates[1]

                            db.placeDao().savePlaces(placeEntity)

                //*******************************************
                //*******************************************
                        }
                        thread.start()
                    }// end forloop
                }// end if statement
                else {
                    Log.d("Database", "Fetching from Local data")
                }
                Log.d("Database", "-------------- Done API 1-------------- ")
                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(homeFeed)
                }
//                fetchJsonAPITwo(db)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

        })// end client.newCall
    }// end fetchJson

    fun fetchAllFromDB(db: PlaceDB) {

        val thread = Thread {
            db.placeDao().getAllPlaces().forEach()
            {
                Log.i(
                    "Database",
                    "\nId: ${it.placeId}" + " Name: ${it.placeName}" + " Lon: ${it.placeLon}" + " Lat: ${it.placeLat}"
                )
            }
            print("\n\n----------- Done Fetching all -----------")
        }
        thread.start()
    }

    private fun getPlaceById(db: PlaceDB) {

        val thread = Thread {
            db.placeDao().getPlacesById(6753295460728832).forEach()
            {
                Log.i(
                    "Database",
                    "Trying to Fetch Zaton Soline Lon : " +
                            "\nName: ${it.placeName}" + " \nId: ${it.placeId}" + " \nLon: ${it.placeLon}" + " \nLat: ${it.placeLat}"
                )
            }
        }
        thread.start()
    }

    private fun getPlaceByIdReturnOnlyName(db: PlaceDB) {

        val thread = Thread {
            db.placeDao().getPlacesById(6753295460728832).forEach()
            {
                Log.i(
                    "Database",
                    "Trying to Fetch only the name: Zaton Soline Lon : " +
                            "\nName: ${it.placeName}"
                )
            }
        }
        thread.start()
    }

    private fun updateWithQuery(db: PlaceDB) {
        val thread = Thread {


            db.placeDao().updatePlaceWithQuery(6753295460728832, 88)
//
            db.placeDao().getPlacesById(88).forEach()
            {
                Log.i(
                    "Database",
                    "Trying to Fetch only the name: Zaton Soline Lon : " +
                            "\nName: ${it.placeName}" + " \nId: ${it.placeId}"
                )
            }
        }
        thread.start()
    }

    private fun setBanner(db: PlaceDB, id: Long, newBanner: String) {
        val thread = Thread {
            db.placeDao().updatePlaceBanner(id, newBanner)
        }
        thread.start()
    }

    private fun setComment(db: PlaceDB, id: Long, newComment: String) {
        val thread = Thread {
            db.placeDao().updatePlaceComment(id, newComment)
        }
        thread.start()
    }

    fun fetchJsonAPITwo(db: PlaceDB) {

        Log.d("Database", "inside fetch number two ")

        val thread = Thread {
            for (position in db.placeDao().getAllPlacesId()) {


                var id: Long = 0
                id = position

                val url = "https://www.noforeignland.com/home/api/v1/place?id=$id"
                val request = Request.Builder().url(url).build()
                val client = OkHttpClient()

                client.newCall(request).enqueue(object : Callback {

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string()
                        val gson = GsonBuilder().create()

                        val fromPlaceId = gson.fromJson(body, FromPlaceId::class.java)

                        val banner = fromPlaceId.place.banner
                        val comment = fromPlaceId.place.comments

                        setBanner(db, id, banner)
                        setComment(db, id, comment)
                    }

                override fun onFailure(call: Call, e: IOException) {
                    println("Something went wrong.....fetchJsonAPITwo")
                }
            })
        }// end for loop
            Log.d("Database", "-------------- Done API 2-------------- ")
        }// end thread
        thread.start()
    }// end fetchJsonAPITwo
}

