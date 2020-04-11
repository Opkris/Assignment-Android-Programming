package no.kristiania.assignment_noforeignland

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detail.*
import no.kristiania.assignment_noforeignland.adapters.DetailAdapter
import no.kristiania.assignment_noforeignland.adapters.MainAdapter
import no.kristiania.assignment_noforeignland.db.PlaceDB
import no.kristiania.assignment_noforeignland.models.secondModel.FromPlaceId
import okhttp3.*
import java.io.IOException

class DetailActivity : AppCompatActivity() {

    val TAG = "DetailActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, PlaceDB::class.java, "ROOM_PLACE.db").build()

        setContentView(R.layout.activity_detail)
        recyclerView_detail.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        val navBarTitle = intent.getStringExtra(MainAdapter.CustomViewHolder.FEATURE_NAME_KEY)
        supportActionBar?.title = navBarTitle

        fetchJSON(db)

    }// end onCreate

    fun fetchJSON(db: PlaceDB) {
        val id = intent.getLongExtra(MainAdapter.CustomViewHolder.FEATURE_ID_KEY, -1)


                val url = "https://www.noforeignland.com/home/api/v1/place?id=$id"

                val request = Request.Builder().url(url).build()
                val client = OkHttpClient()

                client.newCall(request).enqueue(object : Callback {

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string()
//                println("\n\n from Json Body: \n $body")

                        val gson = GsonBuilder().create()

                        val fromPlaceId = gson.fromJson(body, FromPlaceId::class.java)

                        setBanner(db, fromPlaceId.place.id, fromPlaceId.place.banner)
                        setComment(db, fromPlaceId.place.id, fromPlaceId.place.comments)
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
    }// end FetchJSON
    private fun setBanner(db: PlaceDB, id: Long, newBanner: String) {
        val thread = Thread {
            db.placeDao().updatePlaceBanner(id, newBanner)
            Log.d("Database", "id: $id and banner : $newBanner")
        }
        thread.start()
    }

    private fun setComment(db: PlaceDB, id: Long, newComment: String) {
        val thread = Thread {
            db.placeDao().updatePlaceComment(id, newComment)
            Log.d("Database", "id: $id and comment : $newComment")


            db.placeDao().getPlacesById(id).forEach {
                Log.d("Database", "id: ${it.placeId} and comment : ${it.placeComment}")
            }
        }
        thread.start()
    }
}