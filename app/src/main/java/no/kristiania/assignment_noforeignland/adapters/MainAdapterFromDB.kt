package no.kristiania.assignment_noforeignland.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_row.view.*
import no.kristiania.assignment_noforeignland.DetailActivity
import no.kristiania.assignment_noforeignland.MapsActivity
import no.kristiania.assignment_noforeignland.R
import no.kristiania.assignment_noforeignland.db.model.PlaceEntity
import no.kristiania.assignment_noforeignland.models.Feature
import no.kristiania.assignment_noforeignland.models.secondModel.Place

class MainAdapterFromDB(
    private var ListPlaces: MutableList<PlaceEntity> = mutableListOf()
)    : RecyclerView.Adapter<MainAdapterFromDB.CustomViewHolder?>(), Filterable {

    private var TAG = "MainAdapter"
    private var ListToShow: MutableList<PlaceEntity> = mutableListOf()

    init {
        ListToShow = ListPlaces
    }

    // numberOfItems
    override fun getItemCount(): Int {
        return ListToShow.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.list_row, parent, false)

        Log.d(TAG, "$ListPlaces")

        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val feature = ListToShow[position]

        holder.view.textView_place_name.text = feature.placeName


        val placeName = feature.placeName
        val placeLon = feature.placeLon
        val placeLat = feature.placeLat

        holder.view.imageView_navigation_link.setOnClickListener {
            println("Hello From navigation Link")
            goToMap(holder.view, placeName, placeLon, placeLat)
        }

        holder.feature = feature
    }
    private fun goToMap(view: View, name: String, lon: Double, lat: Double){

        val PLACE_NAME_KEY = "PLACE_NAME"
        val PLACE_LON_KEY = "PLACE_LON"
        val PLACE_LAT_KEY = "PLACE_LAT"

        val intent = Intent(view.context, MapsActivity::class.java)
        intent.putExtra(PLACE_NAME_KEY, name)
        intent.putExtra(PLACE_LON_KEY, lon)
        intent.putExtra(PLACE_LAT_KEY, lat)
        view.context.startActivity(intent)
    }

    class CustomViewHolder(val view: View, var feature: PlaceEntity? = null) :
        RecyclerView.ViewHolder(view) {
        companion object {
            val FEATURE_NAME_KEY = "FEATURE_TITLE"
            val FEATURE_ID_KEY = "FEATURE_ID"

        }

        init {

            view.setOnClickListener {
                println("clicking on a view : MainAdapter")

                val intent = Intent(view.context, DetailActivity::class.java)

                intent.putExtra(FEATURE_NAME_KEY, feature?.placeName)
                intent.putExtra(FEATURE_ID_KEY, feature?.placeId)

                view.context.startActivity(intent)
            }
        }
    }// end CustomViewHolder

    override fun getFilter(): Filter {
        return placeFilter
    }

    private val placeFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            var aFilteredList: MutableList<PlaceEntity>
            if (constraint == null || constraint.isEmpty()) {
                aFilteredList = ListPlaces

            } else {
                aFilteredList = ListPlaces.filter {
                    it.placeName.contains(
                        constraint.toString(),
                        ignoreCase = true
                    )
                } as MutableList<PlaceEntity>

                Log.d(TAG, "$aFilteredList")

            }

            println(aFilteredList)
            val result = FilterResults()
            result.values = aFilteredList
            return result
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            results?.values.let {
                try {
                    ListToShow = it as MutableList<PlaceEntity>
                } catch (e: TypeCastException) {
                    println(e)
                }
            }
            notifyDataSetChanged()
            println(ListToShow)
        }
    }
}






