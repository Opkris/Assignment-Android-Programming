package no.kristiania.assignment_noforeignland

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detail.*
import no.kristiania.assignment_noforeignland.adapters.CustomViewHolder
import no.kristiania.assignment_noforeignland.adapters.DetailAdapter
import no.kristiania.assignment_noforeignland.models.FromPlaceId
import no.kristiania.assignment_noforeignland.sqLite.DBHelper
import no.kristiania.assignment_noforeignland.sqLite.model.Place
import okhttp3.*
import java.io.IOException

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailActivity : AppCompatActivity(){

    val TAG = "DetailActivity"
    internal lateinit var db: DBHelper
    private var placeIdWeb =""
    private var placeNameV = ""
    private var placeV = Place()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        recyclerView_detail.layoutManager = LinearLayoutManager(this)

        val  navBarTitle = intent.getStringExtra(CustomViewHolder.FEATURE_NAME_KEY)
        supportActionBar?.title = navBarTitle

        fetchJSON()

        addingPlace(placeIdWeb, placeNameV)

    }// end onCreate

    private fun addingPlace(webId: String, placeName: String) {
        db = DBHelper(this)
        db.addPlace(webId, placeName)

        Log.d("Database", "\nDetailActivity : list from DB: " + db.allPlaces)

    }// end AddingPlace


    fun fetchJSON() {
        val placeId = intent.getLongExtra(CustomViewHolder.FEATURE_ID_KEY,-1)
        val placeName = intent.getStringExtra(CustomViewHolder.FEATURE_NAME_KEY).toString()

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