package no.kristiania.assignment_noforeignland

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import no.kristiania.assignment_noforeignland.adapters.MainAdapter
import no.kristiania.assignment_noforeignland.db.PlaceDB
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity
import no.kristiania.assignment_noforeignland.models.Location
import no.kristiania.assignment_noforeignland.splash.SplashActivity
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "Main"
    var adapter: MainAdapter? = null
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        supportActionBar?.title = "NOFOREIGNLAND"

        val db = Room.databaseBuilder(applicationContext, PlaceDB::class.java, "ROOM_PLACE.db").allowMainThreadQueries().build()
        val listOfLocations = db.placeDao().getAllPlaces()

        renderLocations(listOfLocations)


        var swipeCounter = 0
        // Swipe Widget to update list of locations.
        swipeLayout = findViewById(R.id.swipeContainer)
        swipeLayout.setOnRefreshListener {
            renderLocations(listOfLocations)
            swipeLayout.isRefreshing = false
            swipeCounter += 1

            if (swipeCounter >= 3){
                Log.d(TAG,"New Fetch request to the API")
                fetchJson(db)
                swipeCounter = 0
            }
        }
    }// end onCreate

    private fun renderLocations(listOfLocations: List<PlaceEntity>) {
        Log.d(TAG, "getting all Locations")
        runOnUiThread {
            adapter = MainAdapter( listOfLocations as MutableList<PlaceEntity>)
            recyclerView_main.adapter = adapter
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_Search)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }
        })
        return true
    }

    fun fetchJson(db: PlaceDB) {
        println("Attempting to Fetch JSON")

        val url = "https://www.noforeignland.com/home/api/v1/places/"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        //enqueue make sure that every thing is going on in the background // another thread.
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                // if you need to do debug the response.body :
//                println("from Json Body: \n $body ")

                val gson = GsonBuilder().create()
                val locations = gson.fromJson(body, Location::class.java)

                    Log.d("Database", "Storing data to local")

                    //Looping through homeFeed.features list
                    locations.features.forEach {

                        val thread = Thread {
                            //Setting values to Database
                            val placeEntity = PlaceEntity()
                            placeEntity.placeId = it.properties.id
                            placeEntity.placeName = it.properties.name
                            placeEntity.placeLon = it.geometry.coordinates[0]
                            placeEntity.placeLat = it.geometry.coordinates[1]

                            db.placeDao().savePlaces(placeEntity)
                        }
                        thread.start()
                    }// end forloop
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })// end client.newCall
    }// end fetchJson
}



