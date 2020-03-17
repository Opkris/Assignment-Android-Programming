package no.kristiania.assignment_noforeignland

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_row.view.*

class MainAdapter(val homeFeed : HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {

        val placeNames = listOf("First title", "Second", "3rd", "MOOOOORE TITLE")

        // numberOfItems
        override fun getItemCount(): Int {
            return homeFeed.videos.count()
//            return homeFeed.features.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            // how do we even create a view
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.list_row, parent, false)
            return CustomViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

            val video = homeFeed.videos.get(position)
            holder.view.textView_place_name.text = video.name


            //la stå
            val thumbnailImageView = holder.view.imageView_image

            Picasso.get().load(video.imageUrl).into(thumbnailImageView)

//            val feature = homeFeed.features.get(position)
//            holder.view.textView_place_name.text = feature.properties.name
//            val imageURL = feature.properties.icon
//            Picasso.get().load(imageURL).into(thumbnailImageView)


            holder.video = video
        }

}

    class CustomViewHolder(val view: View, var video: Video? = null): RecyclerView.ViewHolder(view) {

        companion object{
            val VIDEO_TITLE_KEY = "VIDEO_TITLE"
            val VIDEO_ID_KEY = "VIDEO_ID"
        }

        init {
            view.setOnClickListener {
                println("test")

                val intent = Intent(view.context, DetailActivity::class.java)
                intent.putExtra(VIDEO_TITLE_KEY,video?.name)
                intent.putExtra(VIDEO_ID_KEY, video?.id)

                view.context.startActivity(intent)
            }
        }
    }