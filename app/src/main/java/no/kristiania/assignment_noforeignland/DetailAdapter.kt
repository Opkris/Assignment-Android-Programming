package no.kristiania.assignment_noforeignland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_row_detail.view.*

class DetailAdapter(val fromPlaceId : FromPlaceId) : RecyclerView.Adapter<DetailLessonViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
//            return homeFeed.videos.count()
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailLessonViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.list_row_detail, parent, false)
        return DetailLessonViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: DetailLessonViewHolder, position: Int) {

        val place = fromPlaceId
        holder.view.textView_list_row_detail_name.text = place.place.name
        holder.view.textView_list_row_detail_comments.text = place.place.comments
    }

}
class DetailLessonViewHolder(val view: View): RecyclerView.ViewHolder(view){

}