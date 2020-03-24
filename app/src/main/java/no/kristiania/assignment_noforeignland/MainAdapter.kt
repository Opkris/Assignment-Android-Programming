package no.kristiania.assignment_noforeignland

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_row.view.*

class MainAdapter(val homeFeed : HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {

        // numberOfItems
        override fun getItemCount(): Int {
            return homeFeed.features.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            // how do we even create a view
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.list_row, parent, false)
            return CustomViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

            val feature = homeFeed.features.get(position)

            holder.view.textView_place_name.text = feature.properties.name
            holder.feature = feature
}
}

    class CustomViewHolder(val view: View, var feature: Feature? = null): RecyclerView.ViewHolder(view) {

        companion object{
            val FEATURE_TITLE_KEY = "FEATURE_TITLE"
            val FEATURE_ID_KEY = "FEATURE_ID"
        }

        init {
            view.setOnClickListener {
                println("test")

                val intent = Intent(view.context, DetailActivity::class.java)
                intent.putExtra(FEATURE_TITLE_KEY,feature?.properties?.name)
                intent.putExtra(FEATURE_ID_KEY, feature?.properties?.id)

                view.context.startActivity(intent)
            }
        }
    }