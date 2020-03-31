package no.kristiania.assignment_noforeignland

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import no.kristiania.assignment_noforeignland.adapters.MainAdapter
import no.kristiania.assignment_noforeignland.models.HomeFeed
import no.kristiania.assignment_noforeignland.sqLite.DBHelper
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
        test()

    }// end onCreate

    private fun test() {
        var recyclerView = recyclerView_main
        Log.d("TEST", "Dah")
        for (element in recyclerView){
            Log.d("TEST", "Hello World: $element" )
        }

    }

    private fun addingPlace(webId: String, placeName: String) {
        db = DBHelper(this)
        db.addPlace(webId, placeName)


        Log.d("Database", "\nlist from DB: " + db.allPlaces)

    }// end AddingPlace

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        val searchItem = menu?.findItem(R.id.menu_search)
//        if (searchItem != null) {
//            val searchView = searchItem.actionView as SearchView
//            searchView.setOnQueryTextListener(object : SearchView.OnCloseListener,
//                SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//
//                    if (newText!!.isNotEmpty()) {
//                        //clear the list
//
//                        val search = newText.toLowerCase()
//                        //forEach {
//                        // if(
//
//                        // adapter.notifyDataSetChanged()
//
//                    } else {
//                        //clear the list
//                        // add all the "items" to the list
//                        // adapter.notifyDataSetChanged()
//                    }
//                    return true
//                }
//
//                override fun onClose(): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//            })
//        }
//        return super.onCreateOptionsMenu(menu)
//
//    }

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

