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
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db= Room.databaseBuilder(applicationContext, PlaceDB::class.java,"ROOM_PLACE.db").build()

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        fetchJson(db)
//        fetchAllFromDB(db)
//        fetchOneFromDB(db)
        println("---- Next enter GetAllPlacesNames ----")
        getPlacesById(db)


    }// end onCreate

    fun fetchJson(db: PlaceDB) {
        println("Attempting to Fetch JSON")

        val url = "https://www.noforeignland.com/home/api/v1/places/"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        //enqueue make sure that every thing is going on in the background // another thread.
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println("\n\n\n from Json Body: \n $body \n\n\n")
                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                //*******************************************
                //*******************************************

                // if we have fetched once we will not fetch again // at this point.
                if(db.placeDao().getAllPlaces().isEmpty()) {
                    //Looping through homeFeed.features list
                    Log.d("Database", "Storing data to local")
                    for (x in homeFeed.features.indices) {
                        val feature = homeFeed.features.get(x)
                        val thread = Thread {
                            var placeEntity = PlaceEntity()
                            placeEntity.placeId = feature.properties.id
                            placeEntity.placeName = feature.properties.name
                            placeEntity.placeLon = feature.geometry.coordinates[0]
                            placeEntity.placeLat = feature.geometry.coordinates[1]
                            db.placeDao().savePlaces(placeEntity)

                           /* db.placeDao().getAllPlaces().forEach()
                                {
                                    Log.i("Database room", "\nId: ${it.placeId}" + " Name: ${it.placeName}" + " Lon: ${it.placeLon}" + " Lat: ${it.placeLat}")
                                }
*/
                            //*******************************************
                            //*******************************************
                        }
                        thread.start()

                    }// end forloop
                }// end if statement
                else{
                    Log.d("Database","Fetching from Local data")
                }
                Log.d("Database","-------------- Done -------------- ")


                runOnUiThread {
                    recyclerView_main.adapter =
                        MainAdapter(
                            homeFeed
                        )

                }


            }


            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")

            }

        })// end client.newCall

    }// end fetchJson

    fun fetchAllFromDB(db: PlaceDB){

        val thread = Thread {
            db.placeDao().getAllPlaces().forEach()
            {
                Log.i(
                    "Database",
                    "\nId: ${it.placeId}" + " Name: ${it.placeName}" + " Lon: ${it.placeLon}" + " Lat: ${it.placeLat}"
                )
            }
            print("\n\n????????????????????")
        }
        thread.start()
    }

//    fun fetchOneFromDB (db: PlaceDB){
//        Log.i("Database", "Trying to fetch place : Spetses anchorage, and got :" + db.placeDao().findPlaceWithId(6199007999164416))
//    }

    fun getPlacesById(db: PlaceDB) {

        println("-------------- inside get all Places Name --------------")

        val thread = Thread{
            db.placeDao().getPlacesById(6753295460728832).forEach()
            {
                Log.i(
                    "Database",
                    "Trying to Fetch Zaton Soline Lon : " +
                    "\nId: ${it.placeId}" + " Name: ${it.placeName}" + " Lon: ${it.placeLon}" + " Lat: ${it.placeLat}"
                )
            }
            print("\n\n????????????????????")
        }
        thread.start()
    }






}

