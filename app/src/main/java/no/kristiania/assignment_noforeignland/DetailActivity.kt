package no.kristiania.assignment_noforeignland

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detail.*
import no.kristiania.assignment_noforeignland.adapters.CustomViewHolder
import no.kristiania.assignment_noforeignland.adapters.DetailAdapter
import no.kristiania.assignment_noforeignland.db.DBHelper
import no.kristiania.assignment_noforeignland.db.model.Place
import no.kristiania.assignment_noforeignland.models.secondModel.FromPlaceId
import okhttp3.*
import java.io.IOException

class DetailActivity : AppCompatActivity(){

    val TAG = "DetailActivity"
    internal lateinit var db: DBHelper
    private var placeIdWeb =""
    private var placeNameV = ""
    private var placeV = Place()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        recyclerView_detail.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        val  navBarTitle = intent.getStringExtra(CustomViewHolder.FEATURE_NAME_KEY)
        supportActionBar?.title = navBarTitle

        fetchJSON()

//        val db= Room.databaseBuilder(applicationContext, PlaceDB::class.java,"ROOM_PLACE.db").build()
//        //Insert Case
//        val thread = Thread {
//            var placeEntity = PlaceEntity()
//            placeEntity.placeWebId = placeIdWeb
//            placeEntity.placeName = placeNameV
////            HomeFeed().features[1]
//
//            db.placeDao().savePlaces(placeEntity)
//
//            //fetch Records
//            db.placeDao().getAllPlaces().forEach()
//            {
//                Log.i("Database room", "\nId: ${it.placeId}" + " Name: ${it.placeName}" + " WebId: ${it.placeWebId}")
//            }
//            Log.i("Database room", "\n\n***************************************************************************")
//        }// end thread
//        thread.start()



    }// end onCreate

    fun fetchJSON() {
        val placeId = intent.getLongExtra(CustomViewHolder.FEATURE_ID_KEY,-1)
        val placeName = intent.getStringExtra(CustomViewHolder.FEATURE_NAME_KEY).toString()

        val url = "https://www.noforeignland.com/home/api/v1/place?id=$placeId"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println("\n\n from Json Body: \n $body")

                val gson = GsonBuilder().create()

                val fromPlaceId= gson.fromJson(body, FromPlaceId::class.java)

                runOnUiThread {

                    recyclerView_detail.adapter =
                        DetailAdapter(
                            fromPlaceId
                        )
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Something went wrong.....DetailActivity")
            }
        })
//        placeV =
        placeIdWeb = placeId.toString()
        placeNameV = placeName



    }// end FetchJSON
}