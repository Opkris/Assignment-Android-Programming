package no.kristiania.assignment_noforeignland

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detail.*
import okhttp3.*
import java.io.IOException

class DetailActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        recyclerView_detail.layoutManager = LinearLayoutManager(this)

        val  navBarTitle = intent.getStringExtra(CustomViewHolder.FEATURE_TITLE_KEY)
        supportActionBar?.title = navBarTitle

//        println(detailUrl)

        fetchJSON()


    }// end onCreate

     fun fetchJSON() {
        val placeId = intent.getLongExtra(CustomViewHolder.FEATURE_ID_KEY,-1)

        val url = "https://www.noforeignland.com/home/api/v1/place?id=" + placeId

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)



                val gson = GsonBuilder().create()


                val fromPlaceId= gson.fromJson(body, FromPlaceId::class.java)

                runOnUiThread {

                    recyclerView_detail.adapter = DetailAdapter(fromPlaceId)
                }
//                println(body)

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Something went wrong.....DetailActivity")
            }
        })
    }

//    private class DetailAdapter(val courseLessons: Array<CourseLesson>): RecyclerView.Adapter<DetailLessonViewHolder>(){
//
//        override fun getItemCount(): Int {
//            return courseLessons.size
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailLessonViewHolder {
//
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val customView = layoutInflater.inflate(R.layout.activity_detail, parent, false)
//
//            return DetailLessonViewHolder(customView)
//        }
//
//        override fun onBindViewHolder(holder: DetailLessonViewHolder, position: Int) {
//
//            val courseLesson = courseLessons.get(position)
//
////          holder.view.textView_place_name.text = feature.properties.name
//            holder.view.textView_detail_information.text = courseLesson.place[0].toString()
//
////            val imageView = holder.customView.imageView_detail_image
////            Picasso.get().load(courseLesson.imageUrl).into(imageView)
//
//            holder.courseLesson = courseLesson
//        }
//
//
//    }
//     class DetailLessonViewHolder(val view: View, var courseLesson: CourseLesson? = null): RecyclerView.ViewHolder(view){
//
//    }
}