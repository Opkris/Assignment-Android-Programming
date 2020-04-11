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
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import no.kristiania.assignment_noforeignland.adapters.MainAdapter
import no.kristiania.assignment_noforeignland.db.PlaceDB
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity
import no.kristiania.assignment_noforeignland.models.Feature
import no.kristiania.assignment_noforeignland.models.HomeFeed
import no.kristiania.assignment_noforeignland.models.secondModel.FromPlaceId
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "Main"
    var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(applicationContext, PlaceDB::class.java, "ROOM_PLACE.db").build()

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        fetchJson(db)

        /** Testing **/
//        getPlaceById(db)
//        getPlaceByIdReturnOnlyName(db)
//        updateWithQuery(db)
//        fetchAllFromDB(db)

    }// end onCreate

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
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                // if we have fetched once we will not fetch again // at this point.
                if (db.placeDao().getAllPlaces().isEmpty()) {
                    Log.d("Database", "Storing data to local")

                    //Looping through homeFeed.features list
                        homeFeed.features.forEach {

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
                }// end if statement
                else {
                    Log.d("Database", "Fetching from Local data")
                }
                Log.d("Database", "-------------- Done Fetching API -------------- ")

                runOnUiThread {
                    adapter = MainAdapter(homeFeed, homeFeed.features as MutableList<Feature>)
                    recyclerView_main.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }
//                fetchJsonAPITwo(db)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })// end client.newCall
    }// end fetchJson

    fun fetchAllFromDB(db: PlaceDB) {

        val thread = Thread {
            db.placeDao().getAllPlaces().forEach(){
                Log.i(
                    "Database",
                    "\nId: ${it.placeId}" + " Name: ${it.placeName}" + " Lon: ${it.placeLon}" + " Lat: ${it.placeLat}"
                )
            }
            print("\n\n----------- Done Fetching all -----------")
        }
        thread.start()
    }

    private fun getPlaceById(db: PlaceDB) {

        val thread = Thread {
            db.placeDao().getPlacesById(6753295460728832).forEach()
            {
                Log.i(
                    "Database",
                    "Trying to Fetch Zaton Soline Lon : " +
                            "\nName: ${it.placeName}" + " \nId: ${it.placeId}" + " \nLon: ${it.placeLon}" + " \nLat: ${it.placeLat}"
                )
            }
        }
        thread.start()
    }

    private fun getPlaceByIdReturnOnlyName(db: PlaceDB) {

        val thread = Thread {
            db.placeDao().getPlacesById(6753295460728832).forEach()
            {
                Log.i(
                    "Database",
                    "Trying to Fetch only the name: Zaton Soline Lon : " +
                            "\nName: ${it.placeName}"
                )
            }
        }
        thread.start()
    }

    private fun updateWithQuery(db: PlaceDB) {
        val thread = Thread {


            db.placeDao().updatePlaceWithQuery(6753295460728832, 88)
//
            db.placeDao().getPlacesById(88).forEach()
            {
                Log.i(
                    "Database",
                    "Trying to Fetch only the name: Zaton Soline Lon : " +
                            "\nName: ${it.placeName}" + " \nId: ${it.placeId}"
                )
            }
        }
        thread.start()
    }

    private fun setBanner(db: PlaceDB, id: Long, newBanner: String) {
        val thread = Thread {
            db.placeDao().updatePlaceBanner(id, newBanner)
        }
        thread.start()
    }

    private fun setComment(db: PlaceDB, id: Long, newComment: String) {
        val thread = Thread {
            db.placeDao().updatePlaceComment(id, newComment)
        }
        thread.start()
    }

    private fun fetchJsonAPITwo(db: PlaceDB) {

        Log.d("Database", "inside fetch number two ")

        val thread = Thread {
            db.placeDao().getAllPlaces().forEach {

                val url = "https://www.noforeignland.com/home/api/v1/place?id=${it.placeId}"
                val request = Request.Builder().url(url).build()
                val client = OkHttpClient()

                client.newCall(request).enqueue(object : Callback {

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string()

                        val gson = GsonBuilder().create()
                        val fromPlaceId = gson.fromJson(body, FromPlaceId::class.java)

                            setBanner(db, it.placeId, fromPlaceId.place.banner)
                            setComment(db, it.placeId, fromPlaceId.place.comments)
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        println("Something went wrong.....DetailActivity")
                    }
                })
            }
        }
        thread.start()

    }// end fetchJsonAPITwo


}

//            val myList = db.placeDao().getAllPlacesIds()
//            val listSize = myList.size
//
//            val firstList = arrayListOf<Long>()
////                val secondList = arrayListOf<Long>()
//
//            for(x in 0..listSize/2){
//                firstList.add(myList[x])
//            }
//            Log.d("Database", firstList.toString())
//                for(x in listSize/2.. listSize){
//                    secondList.add(myList[x])
//                }

