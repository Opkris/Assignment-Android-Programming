package no.kristiania.assignment_noforeignland

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detail.*
import no.kristiania.assignment_noforeignland.sqLite.DBHelper
import no.kristiania.assignment_noforeignland.sqLite.Place
import okhttp3.*
import java.io.IOException
import java.util.*

class DetailActivity : AppCompatActivity(){

    val TAG = "DetailActivity"
    internal lateinit var db: DBHelper
    private var placeIdV: Long = 0
    private var placeNameV = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        recyclerView_detail.layoutManager = LinearLayoutManager(this)

        val  navBarTitle = intent.getStringExtra(CustomViewHolder.FEATURE_TITLE_KEY)
        supportActionBar?.title = navBarTitle

        fetchJSON()

        addingPlace(placeIdV, placeNameV)

    }// end onCreate

    private fun addingPlace(placeId: Long, placeName: String) {
        db = DBHelper(this)
        db.addPlace(placeName)
        Log.d(TAG, "list from DB: " + db.allPlaces)

    }


    fun fetchJSON() {
        val placeId = intent.getLongExtra(CustomViewHolder.FEATURE_ID_KEY,-1)
        val placeName = intent.getStringExtra(CustomViewHolder.FEATURE_TITLE_KEY)

        val url = "https://www.noforeignland.com/home/api/v1/place?id=" + placeId

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)



                val gson = GsonBuilder().create()


                val fromPlaceId= gson.fromJson(body, FromPlaceId::class.java)

                runOnUiThread {

                    recyclerView_detail.adapter = DetailAdapter(fromPlaceId)
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Something went wrong.....DetailActivity")
            }
        })
         placeIdV = placeId
        placeNameV = placeName



    }// end FetchJSON
}