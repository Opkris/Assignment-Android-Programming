package no.kristiania.assignment_noforeignland

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.list_details.*
import kotlinx.android.synthetic.main.list_details.view.*
import kotlinx.android.synthetic.main.list_details.view.textView_list_row_detail_comments
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

        setContentView(R.layout.activity_detail)
        recyclerView_detail.layoutManager = LinearLayoutManager(this)

        val navBarTitle = intent.getStringExtra(MainAdapter.CustomViewHolder.FEATURE_NAME_KEY)
        supportActionBar?.title = navBarTitle

        fetchJSON()

    }// end onCreate

    fun fetchJSON() {
        val id = intent.getLongExtra(MainAdapter.CustomViewHolder.FEATURE_ID_KEY, -1)

            val url = "https://www.noforeignland.com/home/api/v1/place?id=$id"
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    val fromPlaceId = gson.fromJson(body, FromPlaceId::class.java)

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

    }