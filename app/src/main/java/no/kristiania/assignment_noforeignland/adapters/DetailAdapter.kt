package no.kristiania.assignment_noforeignland.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_row_detail.view.*
import no.kristiania.assignment_noforeignland.MapsActivity
import no.kristiania.assignment_noforeignland.R
import no.kristiania.assignment_noforeignland.models.secondModel.FromPlaceId
import no.kristiania.assignment_noforeignland.models.secondModel.Place

class DetailAdapter(val fromPlaceId : FromPlaceId) : RecyclerView.Adapter<DetailCustomViewHolder>() {

    val TAG = "DetailActivity"
    // numberOfItems
    override fun getItemCount(): Int {
        return 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCustomViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.list_row_detail, parent, false)
        return DetailCustomViewHolder(
            cellForRow
        )
    }

    override fun onBindViewHolder(holder: DetailCustomViewHolder, position: Int) {
        val place = fromPlaceId.place

        // checking if there are eny images/ banners, if not set it to default.
        val url = place.banner
        val default = "https://i.pinimg.com/originals/53/d2/ac/53d2acad97d3e05ab1ba172124bf727a.jpg"
        val imageDetailSite = holder.view.imageView_list_row_detail_image

        if (url.isEmpty()) {
            Picasso.get().load(default).into(imageDetailSite)
        } else{
            Picasso.get().load(url).into(imageDetailSite)
        }

        holder.view.textView_list_row_detail_lat.text = "lat: " + place.lat.toString()
        holder.view.textView_list_row_detail_lon.text = "lon: " + place.lon.toString()
        holder.view.textView_list_row_detail_name.text = place.name

        //Check if there are eny comments, if not, set a default text
        val defaultString = " No comment has been received"
        val comment = place.comments
            .replace("<p>", "")
            .replace("</p>", "")
            .replace("<br/>", "")
            .replace("<br>", "")
            .replace("<b>", "")
            .replace("</b>", "")

        if(comment.isEmpty()){
            holder.view.textView_list_row_detail_comments.text = defaultString
        }else {
            holder.view.textView_list_row_detail_comments.text = comment
        }
        holder.place = place
    }
}
class DetailCustomViewHolder(val view: View, var place: Place? = null): RecyclerView.ViewHolder(view){

    companion object{
        val PLACE_NAME_KEY = "PLACE_NAME"
        val PLACE_LON_KEY = "PLACE_LON"
        val PLACE_LAT_KEY = "PLACE_LAT"
    }
    init {
        view.setOnClickListener {

            val intent = Intent(view.context, MapsActivity::class.java)

            intent.putExtra(PLACE_NAME_KEY, place?.name)
            intent.putExtra(PLACE_LON_KEY,place?.lon)
            intent.putExtra(PLACE_LAT_KEY, place?.lat)

            view.context.startActivity(intent)
        }
    }
}