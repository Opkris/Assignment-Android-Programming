package no.kristiania.assignment_noforeignland

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import no.kristiania.assignment_noforeignland.adapters.MainAdapter
import no.kristiania.assignment_noforeignland.models.HomeFeed
import no.kristiania.assignment_noforeignland.db.DBHelper
import no.kristiania.assignment_noforeignland.db.PlaceDB
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "Main"
    internal lateinit var db: DBHelper
    private var placeId = ""
    private var placeName = ""
    private var placeComment = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        fetchJson()

        db = DBHelper(this)


//        addingPlace(placeId, placeName)

    }// end onCreate


    private fun addingPlace(webId: String, placeName: String) {
        db = DBHelper(this)
        db.addPlace(webId, placeName)


        Log.d("Database", "\nlist from DB: " + db.allPlaces)

    }// end AddingPlace

    fun fetchJson() {
        println("Attempting to Fetch JSON")

        val url = "https://www.noforeignland.com/home/api/v1/places/"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        //enqueue make sure that every thing is going on in the background // another thread.
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                Log.d("Database","\n************************************\n************************************\n" +
                        "************************************\n************************************\n" +
                        "/" + body)
                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                val db= Room.databaseBuilder(applicationContext, PlaceDB::class.java,"ROOM_PLACE.db").build()



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

