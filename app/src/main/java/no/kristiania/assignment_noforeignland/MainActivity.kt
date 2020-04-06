package no.kristiania.assignment_noforeignland

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.gms.common.util.CollectionUtils.isEmpty
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import no.kristiania.assignment_noforeignland.adapters.MainAdapter
import no.kristiania.assignment_noforeignland.models.HomeFeed
import no.kristiania.assignment_noforeignland.db.DBHelper
import no.kristiania.assignment_noforeignland.db.PlaceDB
import no.kristiania.assignment_noforeignland.db.model.Place
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "Main"
    internal lateinit var db: DBHelper
    private val counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        fetchJson()

//        db = DBHelper(this)


//        addingPlace(placeId, placeName)

    }// end onCreate


//    private fun addingPlace(webId: String, placeName: String) {
//        db = DBHelper(this)
//        db.addPlace(webId, placeName)
//        Log.d("Database", "\nlist from DB: " + db.allPlaces)
//    }// end AddingPlace

    fun fetchJson() {
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


                val db= Room.databaseBuilder(applicationContext, PlaceDB::class.java,"ROOM_PLACE.db").build()

                // if we have fetched once we will not fetch again // at this point.
                if(db.placeDao().getAllPlaces().isEmpty()) {
                    //Looping through homeFeed.features list
                    for (x in homeFeed.features.indices) {
                        val feature = homeFeed.features.get(x)
                        val thread = Thread {
                            var placeEntity = PlaceEntity()
                            placeEntity.placeWebId = feature.properties.id.toString()
                            placeEntity.placeName = feature.properties.name
                            db.placeDao().savePlaces(placeEntity)

                            //*******************************************
                            //*******************************************
                        }
                        thread.start()

                    }// end forloop
                }// end if statement
                else{
                    println("Fetching from Local data")
                }

//                 // list all places from DB
//                db.placeDao().getAllPlaces().forEach()
//                {
//                    Log.i(
//                        "Database",
//                        "\nId: ${it.placeId}" + " Name: ${it.placeName}" + " WebId: ${it.placeWebId}"
//                    )
//                }
//                print("\n\n????????????????????")


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
    }

